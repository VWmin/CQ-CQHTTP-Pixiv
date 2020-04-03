PID=$(ps -aux | grep "app.jar" | grep -v "grep" | awk '{ print $2 }')

if [ $? -eq 0 ]; then
    echo "process id:$PID"
else
    echo "process $PID not exit"
    exit
fi

kill ${PID}

if [ $? -eq 0 ];then
    echo "kill app.jar success"
else
    echo "kill app.jar fail"
fi
