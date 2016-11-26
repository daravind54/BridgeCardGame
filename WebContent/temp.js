window.onload = init;
var socket = new WebSocket("wss://localhost/BridgeCardGame/actions");
socket.onmessage = onMessage;
function onMessage(event) {
	
	//alert("On message");
	//window.location.href = "game.jsp";
	document.getElementById("cards").style.display = "";
	var player = JSON.parse(event.data);
    if (player.playerName === "South") {
    	
        alert("South Player");
    }
	if (player.playerName === "West") {
	    	
	        alert("South Player");
	}
	if (player.playerName === "North") {
		
	    alert("South Player");
	}
	if (player.playerName === "East") {
		
	    alert("South Player");
	}
	
}

function formSubmit() {
	hideForm();
	socket.send("Hello");
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
}