<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>User Registration Application</title>
<link rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      crossorigin="anonymous">
      <link rel = "stylesheet" href="${pageContext.request.contextPath}/resources/css/main_style.css">
      <style>
        .fs-btn{
        font-size:13pt;
        margin-bottom:5pt;
        }
      </style>
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

	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
			    <form action="UploadDownloadFileServlet" method="post">
                    <caption>
                        <h2>
                             User Information
                        </h2>
                    </caption>

                    <c:if test="${user != null}">
                        <fieldset class="form-group">
                            <label>User ID</label> <input type="text"
                                value="<c:out value='${user.getId()}' />" class="form-control"
                                name="id" required="required" readonly>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>User Surname</label> <input type="text"
                                value="<c:out value='${user.getLastName()}' />" class="form-control"
                                name="surname" required="required" readonly>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>User Name</label> <input type="text"
                                value="<c:out value='${user.getFirstName()}' />" class="form-control"
                                name="name" required="required" readonly>
                        </fieldset>

                        <c:if test="${user.getBdate() != null}">
                            <fieldset class="form-group">
                                <label>User Birthday</label> <input type="text"
                                    value="<c:out value='${user.getBdate()}' />" class="form-control"
                                    name="birthday" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getCountry() != null}">
                            <fieldset class="form-group">
                                <label>User Country</label> <input type="text"
                                    value="<c:out value='${user.getCountry().getTitle()}' />" class="form-control"
                                    name="country" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getCity() != null}">
                            <fieldset class="form-group">
                                <label>User City</label> <input type="text"
                                    value="<c:out value='${user.getCity().getTitle()}' />" class="form-control"
                                    name="city" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getSex() != null}">
                            <fieldset class="form-group">
                                <label>User Sex</label>
                                <input type="text"
                                    <c:if test="${user.getSex().toString() == '1'}">
                                        value="<c:out value='Female' />"
                                    </c:if>
                                    <c:if test="${user.getSex().toString() != '1'}">
                                        value="<c:out value='Male' />"
                                    </c:if>
                                    class="form-control"
                                    name="sex" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getStatus() != null}">
                            <fieldset class="form-group">
                                <label>User Status</label> <input type="text"
                                    value="<c:out value='${user.getStatus()}' />" class="form-control"
                                    name="status" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getBooks() != null}">
                            <fieldset class="form-group">
                                <label>User Books</label> <input type="text"
                                    value="<c:out value='${user.getBooks()}' />" class="form-control"
                                    name="books" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getInterests() != null}">
                            <fieldset class="form-group">
                                <label>User Interests</label> <input type="text"
                                    value="<c:out value='${user.getInterests()}' />" class="form-control"
                                    name="interests" required="required" readonly>
                            </fieldset>
                        </c:if>
                        <div class="container">
                           <div class="row">
                                <div class="col-sm text-center" >
                                    <a href="<%=request.getContextPath()%>/MainServlet?action=groups" class="btn btn-primary btn-lg btn-block fs-btn" role="button">Show Groups</a>
                                </div>
                                <div class="col-sm text-center" >
                                    <a href="<%=request.getContextPath()%>/MainServlet?action=photos" class="btn btn-primary btn-lg btn-block fs-btn" role="button">Show Photos</a>
                                </div>
                           </div>
                           <div class="row">
                               <div class="col-sm text-center" >
                                    <a href="<%=request.getContextPath()%>/MainServlet?action=friends" class="btn btn-primary btn-lg btn-block fs-btn" role="button">Show Mutual Friends</a>
                               </div>
                               <div class="col-sm text-center" >
                                    <a href="<%=request.getContextPath()%>/MainServlet?action=geotags" class="btn btn-primary btn-lg btn-block fs-btn" role="button">Show Geotags</a>
                               </div>
                            </div>
                           <div class="row">
                               <div class="col-sm text-center" >
                                    <a href="<%=request.getContextPath()%>/MainServlet?action=wall" class="btn btn-primary btn-lg btn-block fs-btn" role="button">Show Wall Notes</a>
                               </div>
                               <div class="col-sm text-center" >
                                     <button type="submit" class="btn btn-primary btn-lg btn-block fs-btn">Download</button>
                               </div>
                            </div>

                    </c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>