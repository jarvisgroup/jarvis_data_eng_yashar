#!/bin/bash

switch=$1 
password=$2 

case $switch in
	start)
		#start docker
		sudo systemctl status docker || sudo systemctl start docker
		#get psql docker image
		sudo docker pull postgres
		#set password for default user
		export PGPASSWORD=$password
		#run psql, auto remove existing container and assign a name
		sudo docker run --rm --name jrvs-psql -e Psql_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
		#check status 
		sudo docker ps
		;;
	stop)
		#stop docker
		sudo docker stop jrvs-psql
		;;
	#default case
	*)
		echo "ERROR! PLEASE USE: $0 (start|stop) FORMAT"
esac
#log out session
exit 0
		
