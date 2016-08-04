#!/bin/bash
 
######################################
#crontab -e
#*/1 * * * * /workspace/plugin/sql/import.sh
######################################
 
 RAW_FOLDER=/workspace/plugin/sql/raw;
 EXECUTE_FOLDER=/workspace/plugin/sql/execute;
 cd $RAW_FOLDER;
 
 function doImport() {
     f=$1;
     sqlFile="$f.sql";
     echo "Convert raw data to sql file:$f->$sqlFile";
     nodejs ../import.js $f $sqlFile;
     echo "Start import $sqlFile to mysql";
     echo "mysql -uplugin -p123456 -Dplugin < $sqlFile";
     mysql -uplugin -p123456 -Dplugin < $sqlFile;
     #rm $sqlFile;
     #rm $f;
     echo "Finish handling $f"
 }
 
 function doCheck(){
     FLAG_FILE="/tmp/black_import_flag";
 
     if [ -f "$FLAG_FILE" ]; then
         return;
     fi
 
     touch $FLAG_FILE;
 
     for f in *.zip 
     do
         if [ -f "$f" ]; then
             if ! [[ `lsof -c vsftpd | grep "$f"` ]] ; then
                 FILE_NAME=$(date +"%Y%m%d%H%M%S");
                 FILE_PATH=$EXECUTE_FOLDER/$FILE_NAME
                 echo "move file from $RAW_FOLDER/$f to $FILE_PATH.zip";
                 mv $RAW_FOLDER/$f  $FILE_PATH.zip;
                 unzip -o $FILE_PATH.zip -d $FILE_PATH;
                 find $FILE_PATH -name "*.*" -print | xargs cat >$FILE_PATH.txt
                 rm -R $FILE_PATH;
                 doImport $FILE_PATH.txt;
                 sleep 1.1
             fi    
         fi
     done
 
 
     for f in *.rar 
     do
         if [ -f "$f" ]; then
             if ! [[ `lsof -c sftp-serv | grep "$f"` ]] ; then
                 FILE_NAME=$(date +"%Y%m%d%H%M%S");
                 FILE_PATH=$EXECUTE_FOLDER/$FILE_NAME
                 echo "move file from $RAW_FOLDER/$f to $FILE_PATH.rar";
                 mv $RAW_FOLDER/$f  $FILE_PATH.rar;
                 unrar e -o+ $FILE_PATH.rar $FILE_PATH/
                 find $FILE_PATH -name "*.*" -print | xargs cat >$FILE_PATH.txt
                 rm -R $FILE_PATH;      
                 doImport $FILE_PATH.txt;
                 sleep 1.1
             fi    
         fi
     done
 
     for f in *.txt
     do
         if [ -f "$f" ]; then
             if ! [[ `lsof -c sftp-serv | grep "$f"` ]] ; then
                 FILE_NAME=$(date +"%Y%m%d%H%M%S");
                 FILE_PATH=$EXECUTE_FOLDER/$FILE_NAME.txt
                 echo "move file from $RAW_FOLDER/$f to $FILE_PATH";
                 mv $RAW_FOLDER/$f  $FILE_PATH;
                 doImport $FILE_PATH;
                 sleep 1.1
             fi
         fi
     done
     rm $FLAG_FILE;
 }
 
 doCheck;     