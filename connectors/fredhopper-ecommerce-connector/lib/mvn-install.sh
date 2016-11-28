#!/bin/bash
# Install Fredhopper libraries in the local Maven repository

echo "Installing Fredhopper libraries into the local Maven repository..."

mvn -q install:install-file -DgroupId=com.fredhopper -DartifactId=ws-client -Dversion=7.5.2.9 -Dpackaging=jar -Dfile=ws-client-7.5.2.9.jar
mvn -q install:install-file -DgroupId=com.fredhopper -DartifactId=query-lang -Dversion=7.5.2.9 -Dpackaging=jar -Dfile=query-lang-7.5.2.9.jar

echo "Finished"
