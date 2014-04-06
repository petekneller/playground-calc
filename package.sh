#! /usr/bin/env sh

#set -x

version_string="version in ThisBuild := \"${BUILD_NUMBER:-dev}\""
echo $version_string | tee build-number.sbt

sbt clean test oneZip

if [ ! -d target ]; then mkdir target; fi
mv calc/target/scala-2.10/playground-calc*.zip target
echo "##teamcity[publishArtifacts 'target/playground-calc*.zip']"
