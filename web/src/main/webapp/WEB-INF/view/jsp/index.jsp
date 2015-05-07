<%@include file="/WEB-INF/view/jsp/utility/taglibs.jsp"%>
<%@include file="/WEB-INF/view/jsp/utility/pagesettings.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/WEB-INF/view/jsp/common/head.jsp"%>
	<title>Spring4 MVC -HelloWorld</title>
</head>
<body>
	<div id="header"><tiles:insertAttribute name="header"/></div>
	<div id="body">
		<h1>Hello : ${name}</h1>
		<tiles:insertAttribute name="body"/>
	</div>
	<div id="footer"><tiles:insertAttribute name="footer"/></div>
	
	<script language="javascript" type="text/javascript" src="/js/common.js?ver=1.1"></script>
</body>
</html>