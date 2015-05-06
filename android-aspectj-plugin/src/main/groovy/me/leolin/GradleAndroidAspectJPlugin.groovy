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
                aopTask.dependsOn(copyClassTask);

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



                if (hasRetrolambda) {
                    project.tasks["compileRetrolambda$buildTypeName"].finalizedBy(aopTask)
                } else {
                    project.tasks["compile${buildTypeName}Java"].finalizedBy(aopTask)
                }

            }
        }

    }

}
