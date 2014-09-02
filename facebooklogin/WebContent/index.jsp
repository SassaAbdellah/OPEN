<%@page import="de.acando.facebooklogin.OAuth2Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1> OAuth with facebook, Step 1 </h1>



<jsp:useBean id="oauth2Constants"  class="de.acando.facebooklogin.OAuth2Constants" scope="application"/>


<p>
 Get access token  <a href="/facebooklogin/autologin/"> automatically </a>
</p>

<p>
 Get access token  <a href="interactive.jsp"> interactively </a>
</p>

<p>
  	<a href="inspectAccessToken.jsp" > Inspect Access Token </a>
</p>










</body>
</html>