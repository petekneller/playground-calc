#! /usr/bin/env sh

version_string="version := \"${BUILD_NUMBER:-dev}\""
echo $version_string | tee build-number.sbt

sbt clean test package
