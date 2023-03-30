<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.util.*, java.text.*" %>

<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>ERROR</title>
  </head>
  <body>
        <centre>
           <h1>ERROR</h1>
           <h2><%=exception.getMessage() %></h2>
       </centre>
  </body>
</html>