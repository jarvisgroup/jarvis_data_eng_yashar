#!/bin/bash
#set up arguments
switch=$1 
password=$2 
container_name=jrvs-psql


export PGPASSWORD="$password"
#check docker status, if not running, start docker
docker_deamon=$(sudo systemctl status docker || sudo systemctl start docker)

if [ "$#" -eq 2 ] && [ $switch  = "start" ]
then
		#check if pgdata volume is created
		if [ -z $(sudo docker volume ls -q -f name=pgdata) ]; then
			sudo docker volume create pgdata
		fi
		#check if psql container is created
		if [ -z "$(sudo docker container ps -q -f name=$container_name)" ]; then 
			#run psql container, if not created
			sudo docker run --rm --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
			echo "psql container has created and is running"
		#check if psql container is running
		elif [ -z "$(sudo docker container ls -q -a -f name=$container_name)" ]; then
			sudo docker container start jrvs-psql
			echo "psql container starts running"
		else
			echo  "psql is running"
			exit 1
		fi
elif [ "$#" -eq 1 ] && [ $switch  = "stop" ]
then
		#stop sql container
		sudo docker container stop jrvs-psql
		echo "Docker psql container stopped"
else
		echo "ERROR! PLEASE USE: $0 (start dbpassword|stop) FORMAT"
		exit 1
fi
#log out session
exit 0
		
