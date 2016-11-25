
var socket = new WebSocket("wss://localhost/BridgeCardGame/actions");
socket.onmessage = onMessage;
function onMessage(event) {
	
	alert("event");
	//window.location.href = "game.jsp";
}

function formSubmit() {
	hideForm();
	socket.send("Hello");
}
function hideForm() {
    document.getElementById("form1").style.display = "none";
}