#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.flink.cep_socket_source.App -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
