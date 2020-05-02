#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.urlclassloader.general.App -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
