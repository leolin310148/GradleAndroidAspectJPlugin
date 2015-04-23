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

                def aopTask = project.task("compile${buildTypeName}AspectJ") {
                    doLast{
                        ant.taskdef(
                                resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                                classpath: project.configurations.ajc.asPath
                        )
                        if (hasRetrolambda) {
                            ant.iajc(
                                    source: project.android.compileOptions.sourceCompatibility,
                                    target: project.android.compileOptions.targetCompatibility,
                                    maxmem: "2048m",
                                    fork: "true",
                                    destDir: variant.javaCompile.destinationDir,
                                    aspectPath: project.configurations.aspects.asPath,
                                    classpath: iajcClasspath,
                                    inpath: "$variant.javaCompile.destinationDir",
                                    inpathDirCopyFilter: "java/**/*.class"
                            )
                        }else {
                            def sourceRoots = [];
                            variant.javaCompile.source.sourceCollections.each {
                                it.asFileTrees.each {
                                    sourceRoots << it.dir
                                }
                            }
                            ant.iajc(
                                    source: project.android.compileOptions.sourceCompatibility,
                                    target: project.android.compileOptions.targetCompatibility,
                                    maxmem: "2048m",
                                    fork: "true",
                                    destDir: variant.javaCompile.destinationDir,
                                    aspectPath: project.configurations.aspects.asPath,
                                    inpath: project.configurations.ajInpath.asPath,
                                    sourceRoots: sourceRoots.join(File.pathSeparator),
                                    sourceRootCopyFilter: "**/*.java",
                                    classpath: iajcClasspath
                            )
                        }

                    }
                }
                if (hasRetrolambda) {
                    project.tasks["compileRetrolambda$buildTypeName"].finalizedBy(aopTask)
                } else {
                    project.tasks["compile${buildTypeName}Java"].finalizedBy(aopTask)
                }
            }
        }

    }

}
