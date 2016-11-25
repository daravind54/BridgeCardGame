
var socket = new WebSocket("ws://localhost:443/BridgeCardGame/login/actions");
socket.onmessage = onMessage;
function onMessage(event) {
	
	alert("On message");
	window.location.href = "game.jsp";
}

function formSubmit() {
	hideForm();
	socket.send("Hello");
}
function hideForm() {
    document.getElementById("form1").style.display = "none";
}