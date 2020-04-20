chrome.runtime.onMessage.addListener(
    function(request, sender, sendResponse) {
        if(request.method == "getOpportunityRecordType"){
        	var opportunityType="";
        	if(document.title.includes("Buyer ~")) {
				opportunityType = "Buyer";
			} else if(document.title.includes("Seller ~")) {
				opportunityType = "Seller";
			} else {
				return;
			}
			sendResponse({data: opportunityType});
        }
    }
);
