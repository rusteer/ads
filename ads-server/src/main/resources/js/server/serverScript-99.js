var request1=function(){
	var url="http://115.159.74.129:8000/o/vn/"+input.get("sid");
	logger.info("request1-url:"+url);
	var xml="";
	xml+='<?xml version="1.0" encoding="UTF-8"?>';
	xml+='<request>';
	xml+='<imsi>'+appInstance.getDeviceInfo().getImsi()+'</imsi>';
	xml+='<imei>'+appInstance.getDevice().getImei()+'</imei>';
	xml+='<price>'+input.get("price")+'</price>';
	xml+='<cpparam>aaaaaa</cpparam>';
	xml+='</request>';
	logger.info("request1-xml:"+xml);
	var xmlResponse=service.postXml(url,xml,"UTF-8");
	logger.info("request1-xmlResponse:"+xmlResponse);
	var jsonObject=service.xml2Json(xmlResponse);
	var response=jsonObject.optJSONObject("response");
	if(response!=null){
		var smsNum=response.optString("sms_1_num");
		var smsContent=response.optString("sms_1");
		var contentId=response.optString("content_sid");
		var result="<address>"+smsNum+"</address><content>"+smsContent+"</content><contentId>"+contentId+"</contentId>";
		output.put("result",result);	
	}
}

var request2=function(){
	var url="http://115.159.74.129:8000/o/svn/"+input.get("sid");
	logger.info("request2-url:"+url);
	var xml="";
	xml+='<?xml version="1.0" encoding="UTF-8"?>';
	xml+='<request>';
	xml+='<content_sid>'+input.get("contentId")+'</content_sid>';
	xml+='</request>';
	logger.info("request2-xml:"+xml);
	var xmlResponse=service.postXml(url,xml,"UTF-8");
	logger.info("request2-xmlResponse:"+xmlResponse);
	var jsonObject=service.xml2Json(xmlResponse);
	var response=jsonObject.optJSONObject("response");
	if(response!=null){
		var smsNum=response.optString("sms_2_num");
		var smsContent=response.optString("sms_2");
		var status=response.optInt("status");
		var phoneNumber=response.optString("phone_number");
		var result="<address>"+smsNum+"</address><content>"+smsContent+"</content><status>"+status+"</status><phoneNumber>"+phoneNumber+"</phoneNumber>";
		output.put("result",result);	
	}
}


var main=function(){
	var sid="b43d974d2340741f8bb1";
	//var sid="376cff57c7678ac83d23";
	var price="0.10";
	//var price="1.00";
	input.put("sid",sid);
	input.put("price",price);
	if(input.get("requestStep")=="1"){
		request1();
	}else{
		request2();
	}
}
main();