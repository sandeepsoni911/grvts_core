#!/bin/sh

PID=`ps -eaf | grep gravitas.jar | grep -v grep | awk '{print $2}'`
if [[ "" !=  "$PID" ]] 
then 
{
  echo "Stopping GRAVITAS!.....";
  echo "killing $PID"
  kill -9 $PID
}
else 
{
  echo "GRAVITAS not running!";
};fi

