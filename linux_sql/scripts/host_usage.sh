#!/bin/bash

#assign CLI arguments to variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
hostname=$(hostname -f)
#validate argument
if [ "$#" -ne 5 ]; then
	echo "Invalid arguement, please type in psql_host psql_port db_name psql_user psql_password"
	exit 1
fi

#parse server CPU and memory usage data
vmstat_unit=$(vmstat -S M | tail -1)
disk_stat=$(vmstat -d)
memory_free=$(echo "$vmstat_unit" | awk '{print $4}')
cpu_idel=$(echo "$vmstat_unit" | awk '{print $15}')
cpu_kernel=$(echo "$vmstat_unit" | awk '{print $14}')
disk_io=$(echo "$disk_stat" | awk '{print $9, $10}') 
disk_available=$(df -BM | grep -E 'sda1' | awk -F '[ M]*' '{print $4}' | xargs)
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#insert data
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user  << EOF
INSERT INTO host_usage ("timestamp", host_id, memory_free, cpu_idel, cpu_kernel, disk_io, disk_available)  
VALUES ('$timestamp',
	(SELECT id FROM host_info WHERE host_name='$hostname'),
	'$memory_free','$cpu_idel',
	'$cpu_kernel','$disk_io','$disk_available'
);
EOF

echo "Successfully inserted data into host_usage table"
exit 0 


