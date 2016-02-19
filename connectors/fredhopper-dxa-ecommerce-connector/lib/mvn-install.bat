@echo off
rem Install Fredhopper libraries in the local Maven repository

echo Installing Fredhopper libraries into the local Maven repository...

call mvn -q install:install-file -DgroupId=com.fredhopper -DartifactId=ws-client -Dversion=7.5.2.7 -Dpackaging=jar -Dfile=ws-client-7.5.2.7.jar
call mvn -q install:install-file -DgroupId=com.fredhopper -DartifactId=query-lang -Dversion=7.5.2.7 -Dpackaging=jar -Dfile=query-lang-7.5.2.7.jar

echo Finished
pause