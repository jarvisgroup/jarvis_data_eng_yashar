/* group hosts by CPU number and sort by their mem siz in descending order
SELECT cpu_number,host_id,total_mem 
FROM 
	PUBLIC.host_info
ORDER BY
	totak_mem DESC;



SELECT host_id,host_name,total_mem,
	AVG( )      AS used_memory_percentage
FROM 
	PUBLIC.host_info
ORDER BY 
	used_memory_percentage DESC;
