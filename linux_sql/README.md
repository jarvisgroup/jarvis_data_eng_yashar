#Linux Cluster Monitoring Agent
This project is under developement. Since this project follows the GitFlow, the final work will be merged to the master branch after Team Code Review.

In this Directory, there are 6 files in total.
1.README.md : the high level architecture for the linux/sql project and the explaination for the other files
2. (.scripts/host_info.sh) : a bash script that collects host hardware info and insert it into database.
3. (.scripts/host_usage.sh) : a bash script that collects current hsot usage like CPU and memory and then insert into the database, this script will run once every minute. 
4. (.scripts/psql.docker.sh) : a bash script that start/stop psql instance using docker container. 
5. (.sql/ddl.sql) : a sql script that will automate the database initialization.
6. (.sql/queries.sql) : a sql script that collectes information from the cluster database to better manafe 
the cluster and plan the future resources.
