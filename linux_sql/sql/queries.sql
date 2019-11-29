-- Q1 group hosts by CPU number and sort by their mem siz in descending order
SELECT cpu_number,host_id,total_mem 
FROM 
	PUBLIC.host_info
ORDER BY
	cpu_number,
	totak_mem DESC;


-- Q2 
SELECT host_usage.host_id AS host_id,
	host_info.host_name AS host_name,
	host_info.total_mem AS total_memory,
	AVG(100*(host_info.total_mem-host.usage.memory_free)/host_info.total_mem) AS used_memory_percentage
FROM 
	PUBLIC.host_usage
	INNER JOIN host_info ON host_usage.host_id = host_info.id
GROUP BY
	host_id,
	host_name,
	total_memory, 
	DATE_TRUNC('hour',host_usage.timestamp) + 
	DATE_PART('minute',host_usage.timestamp)::int/5 * interval'5 min' AS time
ORDER BY 
	host_usage.host_id;
