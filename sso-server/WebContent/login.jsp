<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
request.removeAttribute("errorMessage");

String url = String.valueOf(request.getParameter("url"));
if (url == null) {
	url = "";
}
url = url.replaceAll("\\\"", "&quot;");
System.out.println(url);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body>
<form method="get" action="./login">
	<table>
		<%if (errorMessage != null) {%>
		<tr>
			<td colspan="2"><%=errorMessage%></td>
		</tr>
		<%}%>
		<tr style="display:none;">
			<td colspan="2"><input type="text" name="url" value="<%=url%>"/></td>
		</tr>
		<tr>
			<td>Username: </td>
			<td><input type="text" name="username"/></td>
		</tr>
		<tr>
			<td>Password: </td>
			<td><input type="text" name="password"/></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Submit"/></td>
		</tr>
	</table>
</form>
</body>
</html>