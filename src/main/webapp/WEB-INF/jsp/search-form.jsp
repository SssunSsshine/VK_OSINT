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
<link rel = "stylesheet" href="${pageContext.request.contextPath}/resources/css/main_style.css"></head>
<body style="background: linear-gradient(#632ece,#9929c9);">
     <header>
        <nav class="navbar navbar-expand-md navbar-dark"
            style="background: linear-gradient(#5725ba,#7e22a5)">
            <div>
                <a href="<%=request.getContextPath()%>/" class="navbar-brand"> VK OSINT  </a>
            </div>
        </nav>
    </header>

	<div class="container min-vh-100">
	    <div class="row justify-content-center">
	        <div class="d-flex container-fluid min-vh-100 align-items-center" >
	        <div class="col-sm text-center">
                <div class="card">
                    <div class="card-body">

                        <c:if test="${is_id}">
                            <form action="user-info" method="post">
                        </c:if>
                        <c:if test="${is_fullname}">
                            <form action="users-list" method="post">
                        </c:if>

                        <caption>
                            <h2 class="fs-search fs-header">
                                <c:if test="${is_id}">
                                    Search User By ID
                                </c:if>
                                <c:if test="${is_fullname}">
                                    Search User By Full Name
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${is_id}">
                            <fieldset class="form-group">
                                <label class = "fs-search">User ID</label> <input type="text"
                                    class="form-control" name="id" required="required">
                            </fieldset>
                        </c:if>

                        <c:if test="${is_fullname}">
                            <fieldset class="form-group">
                                <label class = "fs-search">User Full Name</label> <input type="text"
                                    class="form-control" name="fullname" required="required">
                            </fieldset>
                        </c:if>

                        <c:if test="${error != null}">
                            <font color="red"> <c:out value='${error}' /></font>
                        </c:if>

                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                        </form>
                    </div>
                    </div>
                </div>
            </div>
         </div>
	</div>
</body>
</html>