#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.flink.map_operation.App -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
