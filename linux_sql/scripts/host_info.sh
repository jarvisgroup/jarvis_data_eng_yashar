#!/bin/bash

#assign arugement to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_passward=$5

#validate the arugement
if [ "$#" -ne 5 ]; then
	echo "Invalid Arguments" 
	exit 1
fi

#parse host hardware specification
lscpu_out=`lscpu`
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Model name:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(echo "lscpu_out" | egrep "L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | grep "^MemTotal" | awk '{print $2}')
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#insert data using psql cli
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -W $psql_password <<EOF
INSERT INTO host_info (
hostname,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,total_mem,
timestamp)
VALUES ('$hostname','$cpu_number','$cpu_architecture','$cpu_model','$cpu_mhz',
'$L2_cache','total_mem','$timestamp');
EOF

echo "Successfully inserted data into host_info table"
exit 0


