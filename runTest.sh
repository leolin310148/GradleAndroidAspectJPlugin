#!/usr/bin/env bash
./gradlew SampleApp_01_Normal:clean                 SampleApp_01_Normal:connectedAndroidTest
./gradlew SampleApp_02_AspectInAndroidLib_App:clean SampleApp_02_AspectInAndroidLib_App:connectedAndroidTest
./gradlew SampleApp_03_Retrolambda:clean            SampleApp_03_Retrolambda:connectedAndroidTest
./gradlew SampleApp_04_MultiDex:clean               SampleApp_04_MultiDex:connectedAndroidTest
./gradlew SampleApp_05_AndroidAnnotations:clean     SampleApp_05_AndroidAnnotations:connectedAndroidTest

