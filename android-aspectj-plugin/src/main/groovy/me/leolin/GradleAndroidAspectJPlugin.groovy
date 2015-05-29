package me.leolin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Copy

/**
 * @author leolin
 */
class GradleAndroidAspectJPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.configurations {
            aspectjTaskClasspath
            aspectsInPath
        }

        project.repositories {
            mavenCentral()
        }

        def aspectjVersion = '1.8.5';
        project.dependencies {
            //AspectJ
            aspectjTaskClasspath "org.aspectj:aspectjtools:$aspectjVersion"
            compile "org.aspectj:aspectjrt:$aspectjVersion"
        }

        project.afterEvaluate {
            def hasRetrolambda = project.plugins.hasPlugin('me.tatarka.retrolambda')

            if (project.plugins.hasPlugin(LibraryPlugin)) {
                def android = project.extensions.getByType(LibraryExtension)
                android.libraryVariants.all { variant ->
                    if (hasRetrolambda) {
                        compileAopWithRetrolambda(project, variant)
                    } else {
                        compileAop(project, variant)
                    }
                }
            } else {
                def android = project.extensions.getByType(AppExtension)
                android.applicationVariants.all { variant ->
                    if (hasRetrolambda) {
                        compileAopWithRetrolambda(project, variant)
                    } else {
                        compileAop(project, variant)
                    }
                }
            }
        }

    }

    def compileAopWithRetrolambda(Project project, BaseVariant variant) {
        def buildTypeName = variant.name.capitalize()
        project.tasks["compileRetrolambda$buildTypeName"].finalizedBy(createAopTask(project, variant))
    }

    def compileAop(Project project, BaseVariant variant) {
        variant.javaCompile.finalizedBy(createAopTask(project, variant))
    }

    def Task createAopTask(Project project, BaseVariant variant) {
        def buildTypeName = variant.name.capitalize()

        def aspectsInPaths = [];
        def aspectsInPathsAbsolute = [];
        def aopTask = project.task("compile${buildTypeName}AspectJ") {
            doFirst {
                project.configurations.aspectsInPath.each {
                    aspectsInPaths.add(it);
                    aspectsInPathsAbsolute.add(it.absolutePath);
                }
            }

            doLast {
                def copyDir = new File("${project.buildDir.absolutePath}/copyClasses")

                ant.taskdef(
                        resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                        classpath: project.configurations.aspectjTaskClasspath.asPath
                )
                ant.iajc(
                        source: project.android.compileOptions.sourceCompatibility,
                        target: project.android.compileOptions.targetCompatibility,
                        fork: "true",
                        destDir: variant.javaCompile.destinationDir,
                        bootClasspath: project.android.bootClasspath.join(File.pathSeparator),
                        inpathDirCopyFilter: "java/**/*.class"
                ) {
                    classpath {
                        variant.javaCompile.classpath.each {
                            if (!aspectsInPathsAbsolute.contains(it)) {
                                pathElement(location: it)
                            }
                        }
                    }
                    inpath {
                        pathElement(location: copyDir)
                        aspectsInPaths.each {
                            if (!it.name.startsWith("aspectjrt")) {
                                pathElement(location: it)
                            }
                        }
                    }
                }
            }
        }
        aopTask.dependsOn(copyClassesTask(project, variant))

        if (!project.getTasksByName("preDex${buildTypeName}",true).isEmpty()) {
            def filterPreDexTask = project.task("filter${buildTypeName}PreDex") {
                doLast {
                    def finalPreDexJars = []
                    project.tasks["preDex${buildTypeName}"].inputFiles.each {
                        if (it.name.startsWith("aspectjrt") ||
                                !aspectsInPathsAbsolute.contains(it.absolutePath)) {
                            finalPreDexJars.add(it)
                        }
                    }
                    project.tasks["preDex${buildTypeName}"].inputFiles = finalPreDexJars
                }
            }
            project.tasks["preDex${buildTypeName}"].dependsOn(filterPreDexTask)
        }



        return aopTask
    }

    private Task copyClassesTask(Project project, BaseVariant variant) {
        def buildTypeName = variant.name.capitalize()

        def copyDir = new File("${project.buildDir.absolutePath}/copyClasses")
        if (copyDir.exists()) {
            copyDir.deleteDir()
        }
        copyDir.mkdirs()

        return project.task("copy${buildTypeName}Classes", type: Copy) {
            from variant.javaCompile.destinationDir
            into copyDir
            doLast {
                variant.javaCompile.destinationDir.deleteDir()
                variant.javaCompile.destinationDir.mkdirs()
            }
        }
    }

}
