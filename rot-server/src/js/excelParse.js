//node --max-old-space-size=3000 excelParse.js
var fs = require('fs');
var path = require("path");
var FtpClient = require('ftp');
var columnName='R';
var mobileIndex=(columnName.charCodeAt(0)-'A'.charCodeAt(0));
var parseExcel=function(fileName,outTextName){
    var out=fs.createWriteStream(outTextName, {'flags': 'a'});
    var xlsx = require('node-xlsx');
    var obj = xlsx.parse(fileName);
    var worksheets=obj.worksheets;
    for(var i in worksheets){
        var sheet=worksheets[i];
        var maxCol=sheet.maxCol;
        if(mobileIndex<=maxCol){
            var maxRow=sheet.maxRow;
            var data=sheet.data;
            for(var rowIndex=0;rowIndex<maxRow;rowIndex++){
                var row=data[rowIndex];
                //console.log(row);
                    var mobileCell=row[mobileIndex];
                    if(mobileCell){
                        var mobile=mobileCell.value;
                        if(mobile ){
                            mobile=mobile+"";
                            if(mobile.trim().length==11){
                                //console.log(mobile.trim());
                                out.write(mobile.trim());
                                out.write("\n");
                            }
                        }
                    }
            }
        }
    }
    out.end("\n");
}

var ftpUpload=function(file){
    console.log("start uploading txt file:"+file);
    var c = new FtpClient();
    c.on('ready', function() {
        c.put(outTextName, '/workspace/plugin/sql/raw/'+path.basename(outTextName), function(err) {
          if (err) throw err;
          console.log("end uploading txt file:"+file);
          c.end();
        });
    });
    // connect to localhost:21 as anonymous
    c.connect({host:"58.64.200.114",port:'21',user:'hike',password:'!!$w0dem1ma'});
}


var handleXlsx=function(xlsxPath){
    var outTextName=xlsxPath+".txt";
    parseExcel(xlsxPath,outTextName);
    
    
    //fs.unlinkSync(outTextName);
}

var main=function(dataDir){
    //console.log('Start reading raw data folder:'+rawFolder);
    var getFiles=function(){
        var files=fs.readdirSync(dataDir).map(function (file) {
            return path.join(dataDir, file);
        }).filter(function(file){
                 return fs.statSync(file).isFile();
        });
        return files;
    }
    var fileList=getFiles();

    getFiles().filter(function (file) {return path.extname(file)=='.xlsx'; }).forEach(function (file) {
         var txtFile=file+".txt";
         console.log("start parsing xlsx file:"+file);
         parseExcel(file,txtFile);
         console.log("end parsing xlsx file:"+file);
         fs.unlinkSync(file);
         console.log("delete xlsx file:"+file);
    });    

    getFiles().filter(function (file) { return path.extname(file)=='.txt'; }).forEach(function (file) {
         ftpUpload(file);
    });            
    //console.log('End reading raw data folder:'+rawFolder);
};

//fs.unlinkSync("f:/tmp/raw/10.1-10.31投诉表.xlsx");
var dataDir='f:/tmp/raw/';
main(dataDir);
