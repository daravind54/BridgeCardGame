window.onload = init;
var socket = new WebSocket("wss://localhost/BridgeCardGame/actions");
socket.onmessage = onMessage;
var southData,northData,eastData,westData;
var sbid=null;
var nbid=null;
var ebid=null;
var wbid=null;
function onMessage(event) {
	
	//alert("On message");
	//window.location.href = "game.jsp";
	document.getElementById("waiting").style.display = "none";
	document.getElementById("cards").style.display = "";
	var player = JSON.parse(event.data);
	//alert(sbid);
	document.getElementById("gameType").value = player.gameType;
	document.getElementById("turn").value = player.turn;
	
	
    if (player.playerName == "South") {
    	
        //alert("South Player");
        southData=player;
        document.getElementById("playerName").value = player.playerName;
        if(player.hasOwnProperty("SouthBidOrCard"))
        {
        	document.getElementById("playedValueS").style.display = "";
            document.getElementById("playedValueS").value =player.SouthBidOrCard;
        }
        if(player.hasOwnProperty("WestBidOrCard"))
        {
        	document.getElementById("playedValueW").style.display = "";
            document.getElementById("playedValueW").value =player.WestBidOrCard;
        }
        if(player.hasOwnProperty("EastBidOrCard"))
        {
        	document.getElementById("playedValueE").style.display = "";
            document.getElementById("playedValueE").value =player.EastBidOrCard;
        }
        if(player.hasOwnProperty("NorthBidOrCard"))
        {
        	document.getElementById("playedValueN").style.display = "";
            document.getElementById("playedValueN").value =player.NorthBidOrCard;
        }
        
        var temp="card";
        var clubs=" ";
		var diamonds=" ";
		var hearts=" ";
		var spades=" ";
        for(var i=1;i<=13;i++)
        {
        	
        	var card=temp+i;
        	if(player.hasOwnProperty(card))
        	{
        		var cardData=player[card];
        		//alert(cardData);
        		var cardDataArray=cardData.split("-");
        		//alert(cardDataArray[0]);
        		if(cardDataArray[0]=="C")
        		{
        			clubs+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="D")
        		{
        			diamonds+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="H")
        		{
        			hearts+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="S")
        		{
        			spades+=cardDataArray[1]+" ";
        		}
        	}
        	
        	
        }
        
        document.getElementById("Sclubs").value=clubs;
        document.getElementById("Sdiamonds").value=diamonds;
        document.getElementById("Shearts").value=hearts;
        document.getElementById("Sspades").value=spades;
        if(player.turn=="South's")
        	document.getElementById("Sinput").style.display = "";
        else
        	document.getElementById("Sinput").style.display = "none";
        if(player.gameType=="Bidding Phase" && player.turn=="South's")
    	{
        	
    		document.getElementById("SSubmitCard").style.display = "none";
    		document.getElementById("SSubmitBid").style.display = "";
    		
    		
    	}
        else
        {
        	
    		document.getElementById("SSubmitBid").style.display = "none";
        }
        
    	if(player.gameType=="Game Phase" && player.turn=="South's")
    	{
    		
    		document.getElementById("SSubmitBid").style.display = "none";
    		document.getElementById("SSubmitCard").style.display = "";
    	}
    	else
    	{
    		
    		document.getElementById("SSubmitCard").style.display = "none";
    	}
    }
	if (player.playerName == "West") {
	    	
	        //alert("West Player");
			westData=player;
	        document.getElementById("playerName").value = player.playerName;
	        if(player.hasOwnProperty("SouthBidOrCard"))
	        {
	        	document.getElementById("playedValueS").style.display = "";
	            document.getElementById("playedValueS").value =player.SouthBidOrCard;
	        }
	        if(player.hasOwnProperty("WestBidOrCard"))
	        {
	        	document.getElementById("playedValueW").style.display = "";
	            document.getElementById("playedValueW").value =player.WestBidOrCard;
	        }
	        if(player.hasOwnProperty("EastBidOrCard"))
	        {
	        	document.getElementById("playedValueE").style.display = "";
	            document.getElementById("playedValueE").value =player.EastBidOrCard;
	        }
	        if(player.hasOwnProperty("NorthBidOrCard"))
	        {
	        	document.getElementById("playedValueN").style.display = "";
	            document.getElementById("playedValueN").value =player.NorthBidOrCard;
	        }
	        var temp="card";
	        var clubs=" ";
			var diamonds=" ";
			var hearts=" ";
			var spades=" ";
	        for(var i=1;i<=13;i++)
	        {
	        	
	        	var card=temp+i;
	        	if(player.hasOwnProperty(card))
	        	{
	        		var cardData=player[card];
	        		//alert(cardData);
	        		var cardDataArray=cardData.split("-");
	        		//alert(cardDataArray[0]);
	        		if(cardDataArray[0]=="C")
	        		{
	        			clubs+=cardDataArray[1]+" ";
	        		}
	        		if(cardDataArray[0]=="D")
	        		{
	        			diamonds+=cardDataArray[1]+" ";
	        		}
	        		if(cardDataArray[0]=="H")
	        		{
	        			hearts+=cardDataArray[1]+" ";
	        		}
	        		if(cardDataArray[0]=="S")
	        		{
	        			spades+=cardDataArray[1]+" ";
	        		}
	        	}
	        	
	        	
	        }
	        
	        document.getElementById("Wclubs").value=clubs;
	        document.getElementById("Wdiamonds").value=diamonds;
	        document.getElementById("Whearts").value=hearts;
	        document.getElementById("Wspades").value=spades;
	        if(player.turn=="West's")
	        	document.getElementById("Winput").style.display = "";
	        else
	        	document.getElementById("Winput").style.display = "none";
	        if(player.gameType=="Bidding Phase"&& player.turn=="West's")
	    	{
	        	
	    		document.getElementById("WSubmitCard").style.display = "none";
	    		document.getElementById("WSubmitBid").style.display = "";
	    		
	    	}
	        else
	        {
	        	
	    		document.getElementById("WSubmitBid").style.display = "none";
	        }
	        
	    	if(player.gameType=="Game Phase" && player.turn=="West's")
	    	{
	    		
	    		document.getElementById("WSubmitBid").style.display = "none";
	    		document.getElementById("WSubmitCard").style.display = "";
	    	}
	    	else
	    	{
	    		
	    		document.getElementById("WSubmitCard").style.display = "none";
	    	}
	}
	if (player.playerName == "North") {
		
	   //alert("North Player");
		northData=player;
	    document.getElementById("playerName").value = player.playerName;
	    if(player.hasOwnProperty("SouthBidOrCard"))
        {
        	document.getElementById("playedValueS").style.display = "";
            document.getElementById("playedValueS").value =player.SouthBidOrCard;
        }
        if(player.hasOwnProperty("WestBidOrCard"))
        {
        	document.getElementById("playedValueW").style.display = "";
            document.getElementById("playedValueW").value =player.WestBidOrCard;
        }
        if(player.hasOwnProperty("EastBidOrCard"))
        {
        	document.getElementById("playedValueE").style.display = "";
            document.getElementById("playedValueE").value =player.EastBidOrCard;
        }
        if(player.hasOwnProperty("NorthBidOrCard"))
        {
        	document.getElementById("playedValueN").style.display = "";
            document.getElementById("playedValueN").value =player.NorthBidOrCard;
        }
	    var temp="card";
        var clubs=" ";
		var diamonds=" ";
		var hearts=" ";
		var spades=" ";
        for(var i=1;i<=13;i++)
        {
        	
        	var card=temp+i;
        	if(player.hasOwnProperty(card))
        	{
        		var cardData=player[card];
        		//alert(cardData);
        		var cardDataArray=cardData.split("-");
        		//alert(cardDataArray[0]);
        		if(cardDataArray[0]=="C")
        		{
        			clubs+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="D")
        		{
        			diamonds+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="H")
        		{
        			hearts+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="S")
        		{
        			spades+=cardDataArray[1]+" ";
        		}
        	}
        	
        	
        }
        
        document.getElementById("Nclubs").value=clubs;
        document.getElementById("Ndiamonds").value=diamonds;
        document.getElementById("Nhearts").value=hearts;
        document.getElementById("Nspades").value=spades;
        if(player.turn=="North's")
        	document.getElementById("Ninput").style.display = "";
        else
        	document.getElementById("Ninput").style.display = "none";
        if(player.gameType=="Bidding Phase" && player.turn=="North's")
    	{
        	
    		document.getElementById("NSubmitCard").style.display = "none";
    		document.getElementById("NSubmitBid").style.display = "";
    		
    	}
        else
        {
        	
    		document.getElementById("NSubmitBid").style.display = "none";
        }
        	
        
    	if(player.gameType=="Game Phase" && player.turn=="North's")
    	{
    		
    		document.getElementById("NSubmitBid").style.display = "none";
    		document.getElementById("NSubmitCard").style.display = "";
    	}
    	else
    	{
    		
    		document.getElementById("NSubmitCard").style.display = "none";
    	}
	}
	if (player.playerName == "East") {
		
	    //alert("East Player");
		eastData=player;
	    document.getElementById("playerName").value = player.playerName;
	    if(player.hasOwnProperty("SouthBidOrCard"))
        {
        	document.getElementById("playedValueS").style.display = "";
            document.getElementById("playedValueS").value =player.SouthBidOrCard;
        }
        if(player.hasOwnProperty("WestBidOrCard"))
        {
        	document.getElementById("playedValueW").style.display = "";
            document.getElementById("playedValueW").value =player.WestBidOrCard;
        }
        if(player.hasOwnProperty("EastBidOrCard"))
        {
        	document.getElementById("playedValueE").style.display = "";
            document.getElementById("playedValueE").value =player.EastBidOrCard;
        }
        if(player.hasOwnProperty("NorthBidOrCard"))
        {
        	document.getElementById("playedValueN").style.display = "";
            document.getElementById("playedValueN").value =player.NorthBidOrCard;
        }
	    var temp="card";
        var clubs=" ";
		var diamonds=" ";
		var hearts=" ";
		var spades=" ";
        for(var i=1;i<=13;i++)
        {
        	
        	var card=temp+i;
        	if(player.hasOwnProperty(card))
        	{
        		var cardData=player[card];
        		//alert(cardData);
        		var cardDataArray=cardData.split("-");
        		//alert(cardDataArray[0]);
        		if(cardDataArray[0]=="C")
        		{
        			clubs+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="D")
        		{
        			diamonds+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="H")
        		{
        			hearts+=cardDataArray[1]+" ";
        		}
        		if(cardDataArray[0]=="S")
        		{
        			spades+=cardDataArray[1]+" ";
        		}
        	}
        	
        	
        }
        
        document.getElementById("Eclubs").value=clubs;
        document.getElementById("Ediamonds").value=diamonds;
        document.getElementById("Ehearts").value=hearts;
        document.getElementById("Espades").value=spades;
        if(player.turn=="East's")
        	document.getElementById("Einput").style.display = "";
        else
        	document.getElementById("Einput").style.display = "none";
        if(player.gameType=="Bidding Phase" && player.turn=="East's")
    	{
        	
    		document.getElementById("ESubmitCard").style.display = "none";
    		document.getElementById("ESubmitBid").style.display = "";
    		
    	}
        else
        {
        	
    		document.getElementById("ESubmitBid").style.display = "none";
        }
        
    	if(player.gameType=="Game Phase" && player.turn=="East's")
    	{
    		
    		document.getElementById("ESubmitBid").style.display = "none";
    		document.getElementById("ESubmitCard").style.display = "";
    	}
    	else
    	{
    		
    		document.getElementById("ESubmitCard").style.display = "none";
    	}
	}
	
}

function formSubmit() {
	hideForm();
	document.getElementById("waiting").style.display = "";
	var DeviceAction = {
	        action: "initialization"
	        };
	socket.send(JSON.stringify(DeviceAction));
}
function formSubmitBidS() {
	//hideForm();
	//document.getElementById("waiting").style.display = "";
	var username="South";
	var bidvalue=document.getElementById("Sinput").value;
	document.getElementById("playedValueS").style.display = "";
	//document.getElementById("playedValueS").value =bidvalue;
	var DeviceAction = {
	        action: "bidding",
	        playerName: username,
	        bidValue: bidvalue,
	        };
	southData.action="bidding";
	
	southData.bidValue=bidvalue;
	socket.send(JSON.stringify(southData));
}
function formSubmitBidN() {
	//hideForm();
	//document.getElementById("waiting").style.display = "";
	var username="North";
	var bidvalue=document.getElementById("Ninput").value;
	document.getElementById("playedValueN").style.display = "";
	document.getElementById("playedValueN").value =bidvalue;
	var DeviceAction = {
	        action: "bidding",
	        playerName: username,
	        bidValue: bidvalue,
	        };
	northData.action="bidding";
	
	northData.bidValue=bidvalue;
	socket.send(JSON.stringify(northData));
}
function formSubmitBidE() {
	//hideForm();
	//document.getElementById("waiting").style.display = "";
	var username="East";
	var bidvalue=document.getElementById("Einput").value;
	document.getElementById("playedValueE").style.display = "";
	document.getElementById("playedValueE").value =bidvalue;
	var DeviceAction = {
	        action: "bidding",
	        playerName: username,
	        bidValue: bidvalue,
	        };
	eastData.action="bidding";
	
	eastData.bidValue=bidvalue;
	socket.send(JSON.stringify(eastData));
}
function formSubmitBidW() {
	//hideForm();
	//document.getElementById("waiting").style.display = "";
	var username="West";
	var bidvalue=document.getElementById("Winput").value;
	document.getElementById("playedValueW").style.display = "";
	document.getElementById("playedValueW").value =bidvalue;
	var DeviceAction = {
	        action: "bidding",
	        playerName: username,
	        bidValue: bidvalue,
	        };
	westData.action="bidding";
	
	westData.bidValue=bidvalue;
	socket.send(JSON.stringify(westData));
}
function hideForm() {
    document.getElementById("form1").style.display = "none";
    document.getElementById("welcome").style.display = "none";
}
function init() {
    hideDiv();
}
function hideDiv() {
    document.getElementById("cards").style.display = "none";
    document.getElementById("waiting").style.display = "none";
    document.getElementById("SSubmitBid").style.display = "none";
    document.getElementById("SSubmitCard").style.display = "none";
    document.getElementById("WSubmitBid").style.display = "none";
    document.getElementById("WSubmitCard").style.display = "none";
    document.getElementById("NSubmitBid").style.display = "none";
    document.getElementById("NSubmitCard").style.display = "none";
    document.getElementById("ESubmitBid").style.display = "none";
    document.getElementById("ESubmitCard").style.display = "none";
    
    document.getElementById("Ninput").style.display = "none";
    document.getElementById("Einput").style.display = "none";
    document.getElementById("Winput").style.display = "none";
    document.getElementById("Sinput").style.display = "none";
    document.getElementById("playedValueN").style.display = "none";
    document.getElementById("playedValueS").style.display = "none";
    document.getElementById("playedValueE").style.display = "none";
    document.getElementById("playedValueW").style.display = "none";
}