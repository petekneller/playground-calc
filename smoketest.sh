#! /usr/bin/env sh

#set -x

if [ $# -ne 1 ]
then
    echo "Usage: smoketest.sh deploy_machine:port"
    exit 1
fi

sbt -Dtest_host=$1 "playground-calc-smoketest/test"