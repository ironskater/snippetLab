#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.netty.discard_server.DiscardServer -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
