package me.leolin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author leolin
 */
class GradleAndroidAspectJPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.configurations {
            ajc
            aspects
            ajInpath
        }

        project.repositories {
            mavenCentral()
        }

        project.dependencies {
            ext {
                aspectjVersion = '1.8.5';
            }
            //AspectJ
            ajc "org.aspectj:aspectjtools:$aspectjVersion"
            compile "org.aspectj:aspectjrt:$aspectjVersion"
        }

        project.afterEvaluate {
            project.android.applicationVariants.all { variant ->


                def androidSdk = project.android.adbExe.parent + "/../platforms/" + project.android.compileSdkVersion + "/android.jar"
                def iajcClasspath = androidSdk + ":" + project.configurations.compile.asPath
                def tree = project.fileTree(dir: "${project.buildDir}/intermediates/exploded-aar", include: ['**/classes.jar'])
                tree.each { jarFile ->
                    iajcClasspath += ":" + jarFile
                }

                def buildTypeName = variant.name.capitalize()
                def hasRetrolambda = project.plugins.hasPlugin('me.tatarka.retrolambda')
                if (hasRetrolambda) {

                    def aopRetrolambdaTask = project.task("aopRetrolambda${buildTypeName}") {

                        doLast {
                            println "Doing Ajc compile for classpath:"
                            iajcClasspath.split(':').each { c ->
                                println(c)
                            }

                            ant.taskdef(
                                    resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                                    classpath: project.configurations.ajc.asPath
                            )
                            ant.iajc(
                                    source: project.sourceCompatibility,
                                    target: project.targetCompatibility,
                                    maxmem: "2048m",
                                    fork: "true",
                                    destDir: variant.javaCompile.destinationDir,
                                    aspectPath: project.configurations.aspects.asPath,
                                    classpath: iajcClasspath,
                                    inpath: "$variant.javaCompile.destinationDir",
                                    inpathDirCopyFilter: "java/**/*.class"
                            )

                            println "Ajc compile Finish"
                        }
                    }

                    project.tasks["compileRetrolambda$buildTypeName"].finalizedBy(aopRetrolambdaTask)
                } else {
                    def sourceRoots = [];
                    variant.javaCompile.source.sourceCollections.each {
                        it.asFileTrees.each {
                            sourceRoots << it.dir
                        }
                    }
                    def aopCompileJavaTask = project.task("aopCompile${buildTypeName}Java") {
                        doLast {
                            println "Doing Ajc compile for classpath:"
                            iajcClasspath.split(':').each { c ->
                                println(c)
                            }

                            ant.taskdef(
                                    resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                                    classpath: project.configurations.ajc.asPath
                            )
                            ant.iajc(
                                    source: project.sourceCompatibility,
                                    target: project.targetCompatibility,
                                    maxmem: "2048m",
                                    fork: "true",
                                    destDir: variant.javaCompile.destinationDir,
                                    aspectPath: project.configurations.aspects.asPath,
                                    inpath: project.configurations.ajInpath.asPath,
                                    sourceRoots: sourceRoots.join(File.pathSeparator),
                                    sourceRootCopyFilter: "**/*.java",
                                    classpath: iajcClasspath
                            )

                            println "Ajc compile Finish"
                        }
                    }

                    project.tasks["compile${buildTypeName}Java"].finalizedBy(aopCompileJavaTask)
                }
            }
        }

    }

}
