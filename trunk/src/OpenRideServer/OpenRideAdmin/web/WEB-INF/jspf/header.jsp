<%--
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>OpenRide-Admin</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/default.css" title="default" type="text/css" rel="stylesheet" />
<link href="img/icon.png" rel="shortcut icon" />
</head>
<body>

<div id="main">
    <div id="heading">
        <h1 id="title">OpenRide</h1>
    </div>
    <div id="menu">
        <ul>
            <li<c:if test="${param.section=='home'}"> class="active" </c:if>><a href="./">Home</a></li>
            <li<c:if test="${param.section=='regpasses'}"> class="active" </c:if>><a href="registration_passes.jsf">Neue Passcodes</a></li>
            <li<c:if test="${param.section=='regpasses_used'}"> class="active" </c:if>><a href="registration_passes_used.jsf">Vergebene Passcodes</a></li>
            <li<c:if test="${param.section=='monitoring'}"> class="active" </c:if>><a href="monitoring.jsf">Monitoring</a></li>
        </ul>
    </div>
    <div id="content">
        <div>
            