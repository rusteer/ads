#!/bin/bash

 logFile=~/cronjobs/clear_mysql.log;
 expire_1_days=$(date -d "-1 day" +%Y-%m-%d)
 expire_3_days=$(date -d "-3 day" +%Y-%m-%d)
 expire_32_days=$(date -d "-32 day" +%Y-%m-%d)
 expire_40_days=$(date -d "-40 day" +%Y-%m-%d)
 expire_20_days=$(date -d "-20 day" +%Y-%m-%d)
 echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"   >>${logFile}

 ##delete old data
 ## 40 days
 echo "delete FROM t_stat_access WHERE access_date< '${expire_20_days}';"                          > tmp.sql;
 echo "delete FROM t_order_record WHERE order_date< '${expire_32_days}';"                          >> tmp.sql;
 echo "delete FROM t_client WHERE last_access_time< '${expire_32_days}';"                          >> tmp.sql;


 ## 3 days
 echo "delete from t_biz where update_time<'${expire_3_days}' and id>10 and id not in (SELECT distinct fee_id FROM t_order_record);"   >>tmp.sql

 ## 1 day
 echo "delete from t_block_record where create_time < '${expire_1_days}';"             >> tmp.sql;
 echo "delete from t_feedback_record where create_time < '${expire_1_days}';"          >> tmp.sql;
 echo "delete from t_step_record where create_time < '${expire_1_days}';"              >> tmp.sql;

 ## 32 days
 echo "delete from t_stat_fees_client where last_fee_time < '${expire_32_days}';"      >> tmp.sql;
 echo "delete from t_stat_fee_client where last_fee_time < '${expire_32_days}';"       >> tmp.sql;
 echo "delete from t_stat_fee_clients where last_fee_time < '${expire_32_days}';"      >> tmp.sql;
 echo "delete from t_stat_fee_province where last_fee_time < '${expire_32_days}';"     >> tmp.sql;

 ## rebuild table
 #echo "alter table  t_biz ENGINE = Innodb;"                                            >> tmp.sql;
 #echo "alter table  t_stat_access ENGINE = Innodb;"                                    >> tmp.sql;
 #echo "alter table  t_order_record ENGINE = Innodb;"                                   >> tmp.sql;
 #echo "alter table  t_client ENGINE = Innodb;"                                         >> tmp.sql;
 #echo "alter table  t_block_record ENGINE = Innodb;"                                   >> tmp.sql;
 #echo "alter table  t_feedback_record ENGINE = Innodb;"                                >> tmp.sql;
 #echo "alter table  t_step_record ENGINE = Innodb;"                                    >> tmp.sql;
 #echo "alter table  t_stat_fees_client ENGINE = Innodb;"                               >> tmp.sql;
 #echo "alter table  t_stat_fee_client ENGINE = Innodb;"                                >> tmp.sql;
 #echo "alter table  t_stat_fee_clients ENGINE = Innodb;"                               >> tmp.sql;
 #echo "alter table  t_stat_fee_province ENGINE = Innodb;"                              >> tmp.sql;
 ##start execute sql
 echo "$(date +"%Y-%m-%d %H:%M:%S") execute sql begin"                                 >>${logFile}
 cat tmp.sql                                                                           >>${logFile}
 mysql -uplugin -p123456 -Dplugin <tmp.sql                                             >>${logFile}

 #rm tmp.sql
 echo "$(date +"%Y-%m-%d %H:%M:%S") execute sql end"                                   >>${logFile}
 #echo "$(date +"%Y-%m-%d %H:%M:%S") restart jetty server begin"                        >>${logFile}
 #/bin/bash ~/18001/restart.sh
 #sleep 1m
 #/bin/bash ~/18002/restart.sh
 #/bin/bash ~/18003/restart.sh
 #echo "$(date +"%Y-%m-%d %H:%M:%S") restart jetty server end"                          >>${logFile}
 #echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"      >>${logFile}