#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.netty.time_client.TimeClient -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
