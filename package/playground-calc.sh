#! /usr/bin/env sh

case $1 in
start)
  if [ ! -d logs ]; then mkdir logs; fi
  date >> logs/out
  java -cp "lib/*" com.github.petekneller.playground.calc.Calc >> logs/out 2>&1  &
  echo $! > logs/PID
;;

stop)
  kill `cat logs/PID` || true
  date >> logs/out
  echo "Shutting down" >> logs/out
;;

*)
  echo "Usage: $0 (start|stop)"
;;

esac
