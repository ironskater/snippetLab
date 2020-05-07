#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.generic.iface.App -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
