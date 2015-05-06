package me.leolin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

/**
 * @author leolin
 */
class GradleAndroidAspectJPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.configurations {
            aspectjTaskClasspath
            aspects
            aspectjClassPath
        }

        project.repositories {
            mavenCentral()
        }

        def aspectjVersion = '1.8.5';
        project.dependencies {
            //AspectJ
            aspectjTaskClasspath "org.aspectj:aspectjtools:$aspectjVersion"
            aspectjClassPath "org.aspectj:aspectjrt:$aspectjVersion"
        }

        project.afterEvaluate {
            project.android.applicationVariants.all { variant ->

                def buildTypeName = variant.name.capitalize()
                def hasRetrolambda = project.plugins.hasPlugin('me.tatarka.retrolambda')

                def copyDir = new File("${project.buildDir.absolutePath}/copyClasses")
                if (copyDir.exists()) {
                    copyDir.deleteDir()
                }
                copyDir.mkdirs()

                def copyClassTask = project.task("copy${buildTypeName}Classes", type: Copy) {
                    from variant.javaCompile.destinationDir
                    into copyDir
                    doLast {
                        variant.javaCompile.destinationDir.deleteDir()
                        variant.javaCompile.destinationDir.mkdirs()
                    }
                }


                def aopTask = project.task("compile${buildTypeName}AspectJ") {
                    doLast {
                        ant.taskdef(
                                resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                                classpath: project.configurations.aspectjTaskClasspath.asPath
                        )
                        ant.iajc(
                                source: project.android.compileOptions.sourceCompatibility,
                                target: project.android.compileOptions.targetCompatibility,
                                fork: "true",
                                destDir: variant.javaCompile.destinationDir,
                                classpath: project.configurations.aspectjClassPath.asPath,
                                bootClasspath: project.android.bootClasspath.join(File.pathSeparator),
                                inpathDirCopyFilter: "java/**/*.class"
                        ) {
                            inpath {
                                pathElement(location: copyDir)
                                variant.javaCompile.classpath.each {
                                    if (!it.name.startsWith("aspectjrt")) {
                                        pathElement(location: it)
                                    }
                                }
                            }
                        }
                    }
                }
                aopTask.dependsOn(copyClassTask);


                def classPathNames = []
                variant.javaCompile.classpath.each {
                    if (!it.name.startsWith("aspectjrt")) {
                        classPathNames.add(it.absolutePath)
                    }
                }

                def finalPreDexJars = []
                project.tasks["preDex${buildTypeName}"].inputFiles.each {
                    if (!classPathNames.contains(it.absolutePath)) {
                        finalPreDexJars.add(it)
                    }
                }
                project.tasks["preDex${buildTypeName}"].inputFiles = finalPreDexJars


                if (hasRetrolambda) {
                    project.tasks["compileRetrolambda$buildTypeName"].finalizedBy(aopTask)
                } else {
                    project.tasks["compile${buildTypeName}Java"].finalizedBy(aopTask)
                }

            }
        }

    }

}
