 #!/bin/bash

 logFile=~/cronjobs/clear_mongo.log;
 target_date=$(date -d "-20 day" +%Y-%m-%d)
 echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"   >>${logFile}

 ##delete old data
 echo "db.client.remove({'createTime':{'\$lt':'$target_date'}});"                      > tmp.js;

 ##start execute
 echo "$(date -d "+1 seconds" +"%Y-%m-%d %H:%M:%S") execute begin"                     >>${logFile}
 cat tmp.js                                                                            >>${logFile}

 mongo localhost:27017/plugin  -quiet tmp.js

 rm tmp.js
 echo "$(date -d "+1 seconds" +"%Y-%m-%d %H:%M:%S") execute end"                       >>${logFile}
 echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"      >>${logFile}

 ~
 ~