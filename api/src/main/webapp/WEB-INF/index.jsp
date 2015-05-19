<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:useBean id="serverConfig" scope="session" class="com.kruzok.api.common.util.ServerConfig"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Data.com API</title>
</head>
<body>
	<div>Welcome to kruzok.com API</div>
	<div>API Build Version: <%=serverConfig.getApiBuildVersion()%></div>
</body>
</html>

