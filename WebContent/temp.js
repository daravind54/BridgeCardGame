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
	/*if(player.gameType=="Game Phase")
	{
		var data=null;
		var clubdata=null;
		var diamonddata=null;
		var heartdata=null;
		var spadedata=null;
		if(player.dummyPlayer=="North" && player.playerName=="North")
		{
			data=JSON.parse(northData.data);
			clubdata=Nclubs;
			diamonddata=Ndiamonds;
			heartdata=Nhearts;
			spadedata=Nspades;
		}
		if(player.dummyPlayer=="South" && player.playerName=="South")
		{
			data=JSON.parse(southData.data);
			clubdata=Sclubs;
			diamonddata=Sdiamonds;
			heartdata=Shearts;
			spadedata=Sspades;
		}
		if(player.dummyPlayer=="East" && player.playerName=="East")
		{
			data=JSON.parse(eastData.data);
			clubdata=Eclubs;
			diamonddata=Ediamonds;
			heartdata=Ehearts;
			spadedata=Espades;
		}
		if(player.dummyPlayer=="West" && player.playerName=="West")
		{
			data=JSON.parse(westData.data);
			clubdata=Wclubs;
			diamonddata=Wdiamonds;
			heartdata=Whearts;
			spadedata=Wspades;
		}
		
		var temp="card";
        var clubs=" ";
		var diamonds=" ";
		var hearts=" ";
		var spades=" ";
        for(var i=1;i<=13;i++)
        {
        	
        	var card=temp+i;
        	if(data.hasOwnProperty(card))
        	{
        		var cardData=data[card];
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
        
        document.getElementById(clubdata).value=clubs;
        document.getElementById(diamonddata).value=diamonds;
        document.getElementById(heartdata).value=hearts;
        document.getElementById(spadedata).value=spades;
	}*/
	if(player.gameType=="Game Won")
	{
		var display="Game Complete."+"\n"+"Game Won by "+player.gameWinner+" Team"+"\n";
		alert(display);
	}
	if(player.gameType=="Bid Complete")
	{
		var trump=null;
		if(player.trump=="C")
			trump="Clubs";
		if(player.trump=="S")
			trump="Spades";
		if(player.trump=="H")
			trump="Hearts";
		if(player.trump=="D")
			trump="Diamonds";
		var display="Bidding Complete,"+"\n"+" Bid won by "+player.bidWinner+"\n"
		+player.dummyPlayer+" is the dummy player"+"\n"
		+"The trump suit is "+trump+"\n"
		+"Tricks to win are "+player.tricksToWin+"\n";
		alert(display);
		player.gameType="Game Phase";
	}
	if(player.gameType=="Game Phase")
	{
		document.getElementById("tricksWonN").style.display = "";
		document.getElementById("tricksWonN").value=player.tricksWonN;
		document.getElementById("tricksWonNLabel").style.display = "";
		
	    document.getElementById("tricksWonS").style.display = "";
		document.getElementById("tricksWonS").value=player.tricksWonS;
		document.getElementById("tricksWonSLabel").style.display = "";
		
	    document.getElementById("tricksWonE").style.display = "";
		document.getElementById("tricksWonE").value=player.tricksWonE;
		document.getElementById("tricksWonELabel").style.display = "";
		
	    document.getElementById("tricksWonW").style.display = "";
		document.getElementById("tricksWonW").value=player.tricksWonW;
		document.getElementById("tricksWonWLabel").style.display = "";
	    
	}
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
function formSubmitCardW()
{
	var username="West";
	var temp=document.getElementById("Winput").value;
	var cardValue=temp.replace(" ", "-");
	var flag=0;
	var leadSuit=westData.suitForTrick;
	var suitlabel=null;
	if(leadSuit=="C")
		suitlabel="Wclubs";
	if(leadSuit=="D")
		suitlabel="Wdiamonds";
	if(leadSuit=="H")
		suitlabel="Whearts";
	if(leadSuit=="S")
		suitlabel="Wspades";
	
	for(var i=1;i<=13;i++)
	{
		var card="card"+i;
    	if(westData.hasOwnProperty(card))
    	{
    		var cardData=westData[card];
    		if(cardData==cardValue)
    		{
    			if(leadSuit!="N" )
    			{
    				if(leadSuit.charAt(0)==cardValue.charAt(0))
	    			{
	    				flag=1;
	    				break;
	    			}
    				else if(document.getElementById(suitlabel).value.length<=1)
    				{
    					flag=1;
    					break;
    				}
    			}
    			else
    			{
    				flag=1;
    				break;
    			}
    		}
    	}
	}
	if(flag==0)
	{
		alert("invalid input");
	}
	else
	{
		
		westData.action="game";
		westData.cardValue=cardValue;
		socket.send(JSON.stringify(westData));
	}
	
}
function formSubmitCardE()
{
	var username="East";
	var temp=document.getElementById("Einput").value;
	var cardValue=temp.replace(" ", "-");
	var flag=0;
	var leadSuit=eastData.suitForTrick;
	var suitlabel=null;
	if(leadSuit=="C")
		suitlabel="Eclubs";
	if(leadSuit=="D")
		suitlabel="Ediamonds";
	if(leadSuit=="H")
		suitlabel="Ehearts";
	if(leadSuit=="S")
		suitlabel="Espades";
	
	for(var i=1;i<=13;i++)
	{
		var card="card"+i;
    	if(eastData.hasOwnProperty(card))
    	{
    		var cardData=eastData[card];
    		if(cardData==cardValue)
    		{
    			if(leadSuit!="N" )
    			{
    				if(leadSuit.charAt(0)==cardValue.charAt(0))
	    			{
	    				flag=1;
	    				break;
	    			}
    				else if(document.getElementById(suitlabel).value.length<=1)
    				{
    					flag=1;
    					break;
    				}
    			}
    			else
    			{
    				flag=1;
    				break;
    			}
    		}
    	}
	}
	if(flag==0)
	{
		alert("invalid input");
	}
	else
	{
		
		eastData.action="game";
		eastData.cardValue=cardValue;
		socket.send(JSON.stringify(eastData));
	}
	
}
function formSubmitCardN()
{
	var username="North";
	var temp=document.getElementById("Ninput").value;
	var cardValue=temp.replace(" ", "-");
	var flag=0;
	var leadSuit=northData.suitForTrick;
	var suitlabel=null;
	if(leadSuit=="C")
		suitlabel="Nclubs";
	if(leadSuit=="D")
		suitlabel="Ndiamonds";
	if(leadSuit=="H")
		suitlabel="Nhearts";
	if(leadSuit=="S")
		suitlabel="Nspades";
	
	for(var i=1;i<=13;i++)
	{
		var card="card"+i;
    	if(northData.hasOwnProperty(card))
    	{
    		var cardData=northData[card];
    		if(cardData==cardValue)
    		{
    			if(leadSuit!="N" )
    			{
    				if(leadSuit.charAt(0)==cardValue.charAt(0))
	    			{
	    				flag=1;
	    				break;
	    			}
    				else if(document.getElementById(suitlabel).value.length<=1)
    				{
    					flag=1;
    					break;
    				}
    			}
    			else
    			{
    				flag=1;
    				break;
    			}
    		}
    	}
	}
	if(flag==0)
	{
		alert("invalid input");
	}
	else
	{
		
		northData.action="game";
		northData.cardValue=cardValue;
		socket.send(JSON.stringify(northData));
	}
	
}
function formSubmitCardS()
{
	var username="South";
	var temp=document.getElementById("Sinput").value;
	var cardValue=temp.replace(" ", "-");
	var flag=0;
	var leadSuit=southData.suitForTrick;
	var suitlabel=null;
	if(leadSuit=="C")
		suitlabel="Sclubs";
	if(leadSuit=="D")
		suitlabel="Sdiamonds";
	if(leadSuit=="H")
		suitlabel="Shearts";
	if(leadSuit=="S")
		suitlabel="Sspades";
	
	for(var i=1;i<=13;i++)
	{
		var card="card"+i;
    	if(southData.hasOwnProperty(card))
    	{
    		var cardData=southData[card];
    		if(cardData==cardValue)
    		{
    			if(leadSuit!="N" )
    			{
    				if(leadSuit.charAt(0)==cardValue.charAt(0))
	    			{
	    				flag=1;
	    				break;
	    			}
    				else if(document.getElementById(suitlabel).value.length<=1)
    				{
    					flag=1;
    					break;
    				}
    			}
    			else
    			{
    				flag=1;
    				break;
    			}
    		}
    	}
	}
	if(flag==0)
	{
		alert("invalid input");
	}
	else
	{
		
		southData.action="game";
		southData.cardValue=cardValue;
		socket.send(JSON.stringify(southData));
	}
	
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
    
    document.getElementById("tricksWonN").style.display = "none";
    document.getElementById("tricksWonS").style.display = "none";
    document.getElementById("tricksWonE").style.display = "none";
    document.getElementById("tricksWonW").style.display = "none";
    document.getElementById("tricksWonNLabel").style.display = "none";
    document.getElementById("tricksWonSLabel").style.display = "none";
    document.getElementById("tricksWonELabel").style.display = "none";
    document.getElementById("tricksWonWLabel").style.display = "none";
}