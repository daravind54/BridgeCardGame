
var socket = new WebSocket("ws://localhost:8080/BridgeCardGame/login/actions");
socket.onmessage = onMessage;
function onMessage(event) {
	
	
	window.location.href = "game.jsp";
}

function formSubmit() {
	hideForm();
	socket.send("Hello");
}
function hideForm() {
    document.getElementById("form1").style.display = "none";
}