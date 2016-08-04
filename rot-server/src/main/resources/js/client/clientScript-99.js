[
	{
		type: "scriptRequest",
		response: {
			reportBody:true,
			matchers: [
				{match:"enclosed(<address>`</address>)",variableName: "address"},
				{match:"enclosed(<content>`</content>)",variableName: "content"},
				{match:"enclosed(<contentId>`</contentId>)",variableName: "contentId"},
			],
		},
		reportSuccess: true,
		reportFailure: true,
		params:[{key:"requestStep",value:"1"}]
	},
	{type: "sms",receiver: "${address}",msg: "${content}",reportFailure: true,reportSuccess: true,base64Decode:true},
	{
		type: "scriptRequest",
		response: {
			reportBody:true,
			matchers: [
				{match:"enclosed(<address>`</address>)",variableName: "address"},
				{match:"enclosed(<content>`</content>)",variableName: "content"},
				{match:"enclosed(<status>`</status>)",variableName: "status",regexValidate:"1"},
			],
		},	
		reportSuccess: true,
		reportFailure: true,
		errorTryCount: 1,
		errorTryConditionVariableName:"status",
		errorTryConditionRegex:"0",
		errorTryInterval:2,
		params:[{key:"requestStep",value:"2"},{key:"contentId",value:"${contentId}"}]
	},	
	{type: "sms",receiver: "${address}",msg: "${content}",reportFailure: true,reportSuccess: true,base64Decode:true}	
]
