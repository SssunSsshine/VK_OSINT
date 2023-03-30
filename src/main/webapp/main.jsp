<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>007</title>
<link rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main_style.css" >
</head>
<body style="background: linear-gradient(#632ece,#9929c9);">
    <header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background: linear-gradient(#5725ba,#7e22a5)">
			<div>
				<a href="<%=request.getContextPath()%>/" class="navbar-brand"> VK OSINT  </a>
			</div>
		</nav>
	</header>

	<div class="d-flex container-fluid min-vh-100 align-items-center">
		<div class="row justify-content-end">
			<div class="col-10" >
				<div class="d-flex container-fluid min-vh-100 align-items-center" >
					<div class="container">
						<div class="row fs">
							<div class="col-sm text-center">
								Choose how do you want to search information about people
							</div>
						</div>
						<div class="row">
							<div class="col-sm text-center" >
								<a href="<%=request.getContextPath()%>/search_by_fullname" class="btn btn-primary btn-lg btn-block" role="button">By User Full Name</a>
							</div>
							<div class="col-sm text-center">
								<a href="<%=request.getContextPath()%>/search_by_id" class="btn btn-primary btn-lg btn-block" role="button">By User ID</a>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div class="row justify-content-left">
			<img src="<%=request.getContextPath()%>/pictures/juicy-scientist-with-magnifying-glass.gif" class="pic">
		</div>
	</div>
</body>
</html>