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

                def buildTypeName = variant.name.capitalize()
                def hasRetrolambda = project.plugins.hasPlugin('me.tatarka.retrolambda')

                def aopTask = project.task("compile${buildTypeName}AspectJ") {
                    doLast {
                        ant.taskdef(
                                resource: "org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties",
                                classpath: project.configurations.ajc.asPath
                        )
                        ant.iajc(
                                source: project.android.compileOptions.sourceCompatibility,
                                target: project.android.compileOptions.targetCompatibility,
                                maxmem: "2048m",
                                fork: "true",
                                destDir: variant.javaCompile.destinationDir,
                                aspectPath: project.configurations.aspects.asPath,
                                bootClasspath: project.android.bootClasspath.join(File.pathSeparator),
                                classpath: variant.javaCompile.classpath.asPath,
                                inpath: "$variant.javaCompile.destinationDir",
                                inpathDirCopyFilter: "java/**/*.class"
                        )
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
