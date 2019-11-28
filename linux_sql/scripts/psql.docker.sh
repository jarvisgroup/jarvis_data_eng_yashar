#!/bin/bash
#set up arguments
switch=$1 
password=$2 

if [ "$#" -eq 2 ] && [ $switch  = "start" ]
then
		#run psql, auto remove existing container and assign a name
		sudo docker run --rm --name jrvs-psql -e Psql_PASSWORD=$password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
		echo "Docker psql container started"
elif [ "$#" -eq 1 ] && [ $switch  = "stop" ]
then
		#stop sql container
		sudo docker container stop 'jrcs_psql'
		echo "Docker psql container stopped"
else
		echo "ERROR! PLEASE USE: $0 (start|stop) FORMAT"
		exit 1
fi
#log out session
exit 0
		
