#! /usr/bin/env sh

version_string="version in ThisBuild := \"${BUILD_NUMBER:-dev}\""
echo $version_string | tee build-number.sbt

sbt clean test package package
