# Hadoop Hive Project
## Table of Content
- [Introduction](#Introduction)
- [Hadoop Cluster](#Hadoop-Cluster)
- [Hive Project](#Hive-Project)
- [Improvements](#Improvements)

## Introduction
This Hadoop project is focusing on evaluate Core Hadoop Components, including HDFS, 
MapReduce and YARN. Apache Hive and Zeppelin NoteBook are used to process those data and solve some real business analytics problems. 

 A one mater node, two work nodes cluster is built in Google Cloud Platform working as a Hadoop Cluster, and the "big data" that is 
 used for this project is the World Development Indicators(WDI) DataSet from Google Storage.
 
## Hadoop Cluster
The Hadoop Cluster used for this project is 3 nodes cluster, contains one master and two workers node which are hosted by Google Cloud Platform.
MasterNode also known as NameNode maintains and manages WorkerNode(DataNodes), it records the metadata of all files stored in the cluster as well.
2 DataNodes are the nodes that store actual data. All 3 nodes have 2vCPUs, 12GB of RAM, and 100GB of storage.  
 The Architecture is shown below.  
![Architecture](assets/architecture.PNG)  

- HDFS stands for Hadoop distributed file system, HDFS is a distributed file system that handles large data sets running on commodity hardware. It is used to scale a single Apache Hadoop cluster to hundreds
 (and even thousands) of nodes,  allows you to store a large number of files across different node. In this project, 2 dataNodes located 
 in worker machines store the actual block of data, where the nameNode located in master machine manages and maintain those
two dataNodes.
- YARN 
- ZEPPLIN
- Hive

## Hive Project
discuss purpose and what you have done
post Zeppelin Note
## Improvements
1. df
2. ef
3. sd