#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.netty.time_server.TimeServer -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
