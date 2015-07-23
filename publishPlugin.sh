#!/usr/bin/env bash
./gradlew android-aspectj-plugin:clean android-aspectj-plugin:build
./gradlew android-aspectj-plugin:install
./gradlew android-aspectj-plugin:bintrayUpload

