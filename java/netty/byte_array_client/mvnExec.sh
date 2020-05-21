#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.netty.byte_array_client.ByteArrayClient -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
