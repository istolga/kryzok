<%@include file="/WEB-INF/view/jsp/utility/taglibs.jsp"%>
<%@include file="/WEB-INF/view/jsp/utility/pagesettings.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/WEB-INF/view/jsp/common/head.jsp"%>
		<title>Spring4 MVC -HelloWorld</title>
	</head>
	<body>
		<header id="header"><tiles:insertAttribute name="header"/></header>
		<article id="body">
			<h1>Hello : ${name}</h1>
			<tiles:insertAttribute name="body"/>
		</article>
		<footer id="footer"><tiles:insertAttribute name="footer"/></footer>
		
		<script language="javascript" type="text/javascript" src="/resource/page/common.js?ver=1.1"></script>
	</body>
</html>