#! /usr/bin/env sh

sbt -Dtest_host=${test_host} "playground-calc-smoketest/test"