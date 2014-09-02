<%@page import="de.acando.facebooklogin.OAuth2Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Redirected via facebook </title>
</head>
<body>


<jsp:useBean id="oauth2Constants"  class="de.acando.facebooklogin.OAuth2Constants" scope="application"/>


<form action="https://graph.facebook.com/oauth/access_token" method="get">
   


	State  obtained from Authorization Response: <br/>
	
	<p>
    state :<br/>  
    <%=request.getParameter("state") %>
   </p>
   
   <h2>code :</h2> 
   <p>
   	Code obtained from Authorization Response: <br/>
	<input name="code" size="50" value="<%= request.getParameter("code")%>" />
   </p>
  
   <h2>redirect_uri :<h2>
   <p>
   	Redirect URL (Hardcoded, where we will digest TokenResponse) <br/>
   	<input name="redirect_uri" size="50"  value="<%=oauth2Constants.getFacebookRedirectURL()%>" />
   <p>
  
   <h2>grant_type :<h2/> 
   <p>
    <input name="grant_type" size="50"  value="authorization_code" />
   <p>
   
   <h2> client_id  :</h2> 
   <p> 
    <input name="client_id" size="50" value="<%=oauth2Constants.getClientId()%>" />
    <p>
     
     <h2> client_secret :</h2>
    <p> 
     <input name="client_secret"  size="50" value="<%=oauth2Constants.getAppSecret()%>" />
    <p>
    
    <h2>redirect_uri :</h2>
    <p> RedirectURI (Hardcoded, same as before) <br/>
     <input name="redirect_uri"  size="50" value="http://localhost:8080/facebooklogin/step1.jsp" />
    <p>
    
   
    <button type="submit"> Submit to facebook for Authorization Token </button>
  
 </form>
 
 
 
 
 
</body>
</html>