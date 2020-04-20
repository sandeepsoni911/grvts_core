#!/bin/sh

PID=`ps -eaf | grep gravitas.jar | grep -v grep | awk '{print $2}'`
if [[ "" !=  "$PID" ]]
then
{
  echo "GRAVITAS! already running with PID: $PID";
}
else
{
  GRAVITAS_HOME=`cd "./.." >/dev/null; pwd`

  echo "#################################### Starting GRAVITAS! ####################################";
  echo "Using GRAVITAS_HOME:   $GRAVITAS_HOME";
  echo "Lookup gravitas logs at: " $GRAVITAS_HOME/logs/

  java -Dgravitas.home="$GRAVITAS_HOME" -jar ../lib/gravitas.jar > ../logs/console.log 2>&1 &

  PID=`ps -eaf | grep gravitas.jar | grep -v grep | awk '{print $2}'`
  echo "GRAVITAS! started with PID: $PID";

};fi

