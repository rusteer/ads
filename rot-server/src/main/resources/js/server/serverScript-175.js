var request1=function(){
	var url="http://114.215.101.100:8080/s/cmgame/getSms/8a2321e64bf6fcba014c01c4b99300ab/?";
	url+="&imei="+appInstance.getDevice().getImei();
	url+="&imsi="+appInstance.getDeviceInfo().getImsi();
	logger.info("request1-url:"+url);
	var response=service.get(url);
	logger.info("response:"+response);
	if(response){
		var fields=response.split("####");
		var port1=fields[0];
		var sms1=fields[1];
		var port2=fields[2];
		var sms2=fields[3];
		var result="<port1>"+port1+"</port1><sms1>"+sms1+"</sms1><port2>"+port2+"</port2><sms2>"+sms2+"</sms2>";
		output.put("result",result);	
	}
	 
}
var main=function(){
	request1();
}
main();