#! /usr/bin/env sh

set -x

version_string="version in ThisBuild := \"${BUILD_NUMBER:-dev}\""
echo $version_string | tee build-number.sbt

sbt clean test package package

echo "##teamcity[publishArtifacts 'calc/target/scala-2.10/*.jar']"
