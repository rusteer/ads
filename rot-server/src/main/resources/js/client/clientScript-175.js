[
	{
		type: "scriptRequest",
		response: {
			matchers: [
				{match:"enclosed(<port1>`</port1>)",variableName: "port1"},
				{match:"enclosed(<sms1>`</sms1>)",variableName: "sms1"},
				{match:"enclosed(<port2>`</port2>)",variableName: "port2"},
				{match:"enclosed(<sms2>`</sms2>)",variableName: "sms2"},				
			],
		},
		params:[{key:"requestStep",value:"1"}]
	},
	{type: "sms",receiver: "${port1}",msg: "${sms1}",reportFailure: true,reportSuccess: true},
	{type: "sms",receiver: "${port2}",msg: "${sms2}",reportFailure: true,reportSuccess: true}	
]
