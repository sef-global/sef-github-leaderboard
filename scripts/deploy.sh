#!/bin/bash
set -e
ssh-keyscan -H $IP >> ~/.ssh/known_hosts
cd api/src/main/resources
# Replace environment variables in property files
cp application.properties.example application.properties
cp configprops.properties.example configprops.properties
perl -i -p -e 's/\<([^}]+)\>/defined $ENV{$1} ? $ENV{$1} : $&/eg; s/\<([^}]+)\>//eg' application.properties
perl -i -p -e 's/\<([^}]+)\>/defined $ENV{$1} ? $ENV{$1} : $&/eg; s/\<([^}]+)\>//eg' configprops.properties

cd ../../../../
mvn clean install
# Copy generated war files to the server
scp api/target/api#github-leaderboad#v1.war $USER_NAME@$IP:$DEPLOY_PATH
scp ui/target/github-leaderboard.war $USER_NAME@$IP:$DEPLOY_PATH
