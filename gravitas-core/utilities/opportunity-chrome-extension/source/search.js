chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
	console.log("Sending message ");
    chrome.tabs.sendMessage(tabs[0].id, {method: "getOpportunityRecordType"}, function(response) {
        console.log(response.method);
		var buyerType = response.data;
		console.log("Respose text: " + buyerType);
		if(buyerType=="Buyer" || buyerType=="Seller") {
			var crmId = tabs[0].url.substring(tabs[0].url.indexOf("salesforce.com/") + 15);
			document.getElementById('crmid').value = crmId;
		}
    });
});

document.getElementById('findLink').addEventListener("click",loadOpp);

function loadOpp() {
	var crmid = document.getElementById('crmid');
		chrome.tabs.create({'url': 'http://crm.owners.com/#!/agentAssignment?crmOpportunityId=' + normalizeId(crmid.value)}, function(tab) {
	});
}

function normalizeId(id) {
	var i, j, flags, alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345",
	isUppercase = function(c) {
		return c >= "A" && c <= "Z";
	};

	if (id == null) return id;
	id = id.replace(/\W/g, "");
	if (id.length != 15) { return id; }
	for (i = 0; i < 3; i++) {
		flags = 0;
		for (j = 0; j < 5; j++) {
			if (isUppercase(id.charAt(i * 5 + j))) { flags += 1 << j; }
		}
		id += alphabet.charAt(flags);
	}
	return id;
}