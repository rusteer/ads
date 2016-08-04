var fs = require('fs');
var readline = require('readline');
var path = require("path");
var os=require('os');
var us=require('underscore');
var prefix=(os.platform()=='win32'?"E:":"")+'/workspace/plugin/sql';

var rawFolder=prefix+"/raw";
var todoPath=prefix+"/todo";
var donePath=prefix+"/done";
var batchSize=501;

var convertTosql=function(rawFile,outFile){
        var out=fs.createWriteStream(rawFile, {'flags': 'a'});
        out.end("\n");
        var rd = readline.createInterface({
            input: fs.createReadStream(rawFile),
            output: process.stdout,
            terminal: false
        });
        out=fs.createWriteStream(outFile, {'flags': 'a'});
        var sql="";
        var index=0;
        rd.on('line', function(line) {
            line=line.trim();
            if(line.length!=11) return;
            if(sql.length==0){
                sql="insert ignore into t_black values ";
            }
            sql+=("('"+line.trim()+"')");
            if((++index)>=batchSize){
                sql+=";\n";
                out.write(sql);
                //console.log(sql);
                sql="";
                index=0;
            }else{
                sql+=",";
            }
        });
        rd.on('close',function(){
//            console.log("readline close");
            if(sql.length>0){
                sql=sql.substring(0,sql.length-1)+";\n";
                out.write(sql);
 //               console.log(sql);
            }
            out.end('');
        });
        

}
var main=function(){
    //console.log('Start reading raw data folder:'+rawFolder);
    fs.readdir(rawFolder, function (err, files) {
        files.map(function (file) {
            return path.join(rawFolder, file);
        }).filter(function (file) {
            return fs.statSync(file).isFile() && path.extname(file)=='.txt';
        }).forEach(function (file) {
            convertTosql(file);    
        });        
    });
    //console.log('End reading raw data folder:'+rawFolder);
};

var main2=function(){
    var arguments=process.argv.splice(2);
    if(arguments.length==2){
            var rawFile=arguments[0];
            var outFile=arguments[1];
            convertTosql(rawFile,outFile);
    }
}

main2();