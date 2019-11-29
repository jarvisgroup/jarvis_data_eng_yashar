--CREATE DATABASE host_agent
DROP DATABASE IF EXISTS host_agent;
CREATE DATABASE host_agent;

--  switch to host_agent db
\c host_agent;

-- create table to store hardware specifications 
CREATE TABLE IF NOT EXISTS public.host_info(
	id SERIAL NOT NULL PRIMARY KEY,
	host_name VARCHAR NOT NULL UNIQUE,
	cpu_number INT2 NOT NULL,
	cpu_architecture VARCHAR NOT NULL,
	cpu_model VARCHAR NOT NULL,
	cpu_mhz DECIMAL NOT NULL,
	L2_cache VARCHAR NOT NULL,
	"timestamp" TIMESTAMP NOT NULL,
	total_mem INTEGER
);

--  CREATE TABLE to resource usage data 
CREATE TABLE IF NOT EXISTS public.host_usage(
	"timestamp" TIMESTAMP NOT NULL,
	host_id SERIAL NOT NULL REFERENCES public.host_info(id),
	memory_free INTEGER NOT NULL ,
	cpu_idel INTEGER NOT NULL,
	cpu_kernel INTEGER NOT NULL,
	disk_id INTEGER NOT NULL,
	disk_avaiable INTEGER NOT NULL
);


