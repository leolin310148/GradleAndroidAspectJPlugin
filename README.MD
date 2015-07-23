Gradle Android AspectJ Plugin
===================================

AspectJ plugin for Android.

SUPPORTS
===================================
<table style="border:0px">
<tr style="border:0px">
    <td style="border:0px" width="200px" align="center">
        <a href="https://github.com/evant/gradle-retrolambda">Retrolambda</a>
    </td>
    <td style="border:0px" align="center"><img src="http://i.imgur.com/QF7sIdw.png?2" title="source: imgur.com" /></td>
</tr>
<tr style="border:0px">
    <td style="border:0px" align="center">
        <a href="http://androidannotations.org">AndroidAnnotation</a>
    </td>
    <td style="border:0px" align="center"><img src="http://i.imgur.com/QF7sIdw.png?2" title="source: imgur.com" /></td>
</tr>
<tr style="border:0px">
    <td style="border:0px" align="center">
        <a href="https://developer.android.com/tools/building/multidex.html">multidex</a>
    </td>
    <td style="border:0px" align="center"><img src="http://i.imgur.com/QF7sIdw.png?2" title="source: imgur.com" /></td>
</tr>
<tr style="border:0px">
    <td style="border:0px" align="center">Android library</td>
    <td style="border:0px" align="center"><img src="http://i.imgur.com/QF7sIdw.png?2" title="source: imgur.com" /></td>
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
                classpath 'me.leolin:android-aspectj-plugin:1.0.7'
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
                classpath 'me.tatarka:gradle-retrolambda:3.0.+'
                classpath 'me.leolin:android-aspectj-plugin:1.0.7'
            }
        }
        
        apply plugin: 'com.android.application'
        apply plugin: 'me.tatarka.retrolambda'
        apply plugin: 'me.leolin.gradle-android-aspectj'


        

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
