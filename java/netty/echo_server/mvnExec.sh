#!/bin/bash
mvn clean compile install; mvn exec:java -Dexec.mainClass=snippetlab.java.netty.echo_server.EchoServer -Dexec.cleanupDaemonThreads=false
#-Dexec.classpathScope="compile"
