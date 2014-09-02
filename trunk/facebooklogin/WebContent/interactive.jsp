<%@page import="de.acando.facebooklogin.OAuth2Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OAuth2 Interactive </title>
</head>
<body>


<jsp:useBean id="oauth2Constants"  class="de.acando.facebooklogin.OAuth2Constants" scope="application"/>


   <h1>Interactive Flow</h1>

	<form action="${oauth2Constants.getOAuth2URL()}" method="get">
  
   	<h2>client_id  :</h2>
   	<p> ClientID (Hardcoded)<br/>
    	<input name="client_id" size="50"  value="${oauth2Constants.getClientId()}" />
    </p>
    
    <h2>redirect_uri :</h2>
    <p> RedirectURI (Hardcoded, same as before) <br/>
    	<input name="redirect_uri"  size="50"  value="${oauth2Constants.getBaseURL()}step1.jsp" />
    </p>
    
    <h2>state :</h2>
    State  obtained from Authorization Response: <br/>
	<p>
     <%=request.getParameter("state")%>
   </p>
    
    <button type="submit"> Submit to facebook for Authorization Token </button>
 </form>
 




</p>






</body>
</html>