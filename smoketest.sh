#! /usr/bin/env sh

set -x

sbt -Dtest_host=${test_host} "playground-calc-smoketest/test"