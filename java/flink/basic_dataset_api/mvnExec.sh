#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.flink.basic_dataset_api.App -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
