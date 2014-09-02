<%@page import="de.acando.facebooklogin.OAuth2Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OAuth2 Interactive </title>
</head>
<body>

<h1> OAuth with facebook, Interactive </h1>



<jsp:useBean id="oauth2Constants"  class="de.acando.facebooklogin.OAuth2Constants" scope="application"/>


   <h1> Interactive  Flow </h1>

	<form action="${oauth2Constants.getOAuth2URL()}" method="get">
  
   	<p> ClientID (Hardcoded) <br/>
    	client_id  : <input name="client_id" size="50"  value="${oauth2Constants.getClientId()}" />
    <p>
    
    
    <p> RedirectURI (Hardcoded, same as before) <br/>
    	redirect_uri : <input name="redirect_uri"   value="http://localhost:8080/facebooklogin/step1.jsp" />
    <p>
    
    	State  obtained from Authorization Response: <br/>
	<p>
    	state :  <%=request.getParameter("state")%>
   </p>
    
    <button type="submit"> Submit to facebook for Authorization Token </button>
 </form>
 




</p>






</body>
</html>