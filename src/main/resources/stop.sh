#!/bin/sh

jar_name='coolq.jar'

pid=`ps ax | grep -i ${jar_name} | grep java | grep -v grep | awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No ${jar_name} running."
        exit -1;
fi

echo "The ${jar_name}(${pid}) is running..."

kill ${pid}

echo "Send shutdown request to ${jar_name}(${pid}) OK"