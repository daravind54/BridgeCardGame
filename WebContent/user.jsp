<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="temp.js"></script>
</head>
<body>
<div id="welcome">
	<h1>Login successful</h1>
</div>
<div id="username">
	<h3>Welcome, <%= session.getAttribute("username") %></h3>
</div>
<form id="form1">


<input type="button"  value="Join Game" onclick=formSubmit()>
<a href="">View Stats</a><br>
</form>
<br>
<div id="cards">
	<table>
		<tr>
			<td></td>
			<td>
			
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
			
			</td>
			<td></td>
			<td>
			
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			
			</td>
			<td></td>
		</tr>
	</table>
</div>
</body>
</html>