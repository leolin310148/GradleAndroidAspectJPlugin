Gradle Android AspectJ Plugin
===================================

AspectJ plugin for Android.

Supports [Retrolambda](https://github.com/evant/gradle-retrolambda)


SUPPORTS
===================================
<table>
<tr>
    <th>Target files</th>
    <th>Aspect files</th>
    <th>Supporting</th>
</tr>
<tr>
    <td align="center">Main Application</td>
    <td align="center">Main Application</td>
    <td align="center">Yes</td>
</tr>
<tr>
    <td align="center">Main Application</td>
    <td align="center">Jar</td>
    <td align="center">Yes</td>
</tr>
<tr>
    <td align="center">Jar</td>
    <td align="center">Main Application</td>
    <td align="center">Yes</td>
</tr>
</table>


USAGE
===================================
General usage

        buildscript {
            repositories {
                jcenter()
            }
            dependencies {
                classpath 'com.android.tools.build:gradle:1.+'
                classpath 'me.leolin:android-aspectj-plugin:1.0.4'
            }
        }
        
        apply plugin: 'com.android.application'
        apply plugin: 'me.leolin.gradle-android-aspectj'


With [Retrolambda](https://github.com/evant/gradle-retrolambda):

        buildscript {
            repositories {
                jcenter()
            }
            dependencies {
                classpath 'com.android.tools.build:gradle:1.+'
                classpath 'me.tatarka:gradle-retrolambda:2.5.0'
                classpath 'me.leolin:android-aspectj-plugin:1.0.+'
            }
        }
        
        apply plugin: 'com.android.application'
        apply plugin: 'me.tatarka.retrolambda'
        apply plugin: 'me.leolin.gradle-android-aspectj'


Aspect classes in jar:
        
        dependencies {
            compile project(":SampleJarLib")
            aspectsInPath project(":SampleJarLib")
        }
        
        
        
SCENARIO
===================================
        
<img width="282" height="375" src="http://i.imgur.com/jWg3pHj.png"/>
        

DEVELOP BY
===================================
[Leo Lin](https://github.com/leolin310148)



LICENSE
===================================
<br/>
        
        Copyright 2015 Leo Lin
        
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        
            http://www.apache.org/licenses/LICENSE-2.0
        
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
<br/>       
