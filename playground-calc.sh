#! /usr/bin/env sh

case $1 in
start)
  date >> logs/out
  java -cp lib/*:playground-calc_2.10-19.jar com.github.petekneller.playground.calc.Calc >> logs/out 2>&1  &
  echo $! > logs/PID
;;

stop)
  kill `cat logs/PID`
;;

*)
  echo "Usage: $0 (start|stop)"
;;

esac
