#!/usr/bin/env bash
# Simple script to start up the micro service using a Spring boot single JAR
# To compile a single JAR type for example the following in the project root:
#   mvn package -Pfredhopper -Pmicroservice-singlejar
#
java -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Dorg.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH=true -Dfile.encoding=UTF-8 -jar target/ecommerce-framework-odata-service-1.1.0.jar
