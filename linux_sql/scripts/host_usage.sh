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
vmstat_unit=`vmstat --unit M`
vmstat_disk=`vmstat -d`
memory_free=$("$vmstat_unit" | grep -E --invert-match "procs|r" | awk '{print $4}' | xargs)
cpu_idel=$("$vmstat_unit" | grep -E --invert-match "procs|r" | awk '{print $15}' | xargs)
cpu_kernel=$("$vmstat_unit" | grep -E --invert-match "procs|r" | awk '{print $14}' | xargs)
disk_io=$("vmstat_disk" | grep -E --invert-match "disk|*total" | awk '{print $10}' | xargs) 
disk_available=$(df - BM / | grep -E --invert-match "Filesystem" | awk -F '[ M]*' '{print $4}' | xargs)
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

#insert data
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -W $psql_password << EOF
INSERT INTO host_usage VALUES (
	(SELECT id FROM host_info WHERE hostname = '$hostname'),
	'$memory_free", '$cpu_idel','$cpu_kernel','$disk_io','$disk_available',
	'$timestamp'
);
EOF

echo "Successfully inserted data into host_usage table"
exit 0 


