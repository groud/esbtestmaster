#!/bin/bash

glassfishPath="/home/ubuntu/GlassFishESBv22/glassfish/bin"

if [ $# != 1 ]
then
set 1
fi

for i in `seq 1 $1`
do
echo agent$i
$glassfishPath/asadmin deploy --contextroot agent$i --name agent$i ESBTestCP/dist/ESBTestCP.war
done
