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
      <link rel = "stylesheet" href="${pageContext.request.contextPath}/resources/css/main_style.css">

</head>
<body style="background: linear-gradient(#632ece,#9929c9);">
    <header>
        <nav class="navbar navbar-expand-md navbar-dark"
             style="background: linear-gradient(#5725ba,#7e22a5)">
            <div class="container-fluid">
                <a href="<%=request.getContextPath()%>/" class="navbar-brand"> VK OSINT  </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Переключатель навигации">
                  <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                  <ul class="navbar-nav">
                    <li class="nav-item">
                      <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/photos">Photos</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/groups">Groups</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/friends">Friends</a>
                    </li>
                  </ul>
                </div>
            </div>
        </nav>
    </header>

	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
			    <form action="download-user" method="post">
                    <caption>
                        <h2>
                             User Information
                        </h2>
                    </caption>

                    <c:if test="${user != null}">
                        <div class="container">
                           <div class="col-sm text-center" >
                                 <button type="submit" class="btn btn-primary btn-lg btn-block fs-btn">Download</button>
                           </div>
                        </div>
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

                        <c:if test="${user.getStatus() != null && !user.getStatus().isBlank()}">
                            <fieldset class="form-group">
                                <label>User Status</label> <input type="text"
                                    value="<c:out value='${user.getStatus()}' />" class="form-control"
                                    name="status" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getBooks() != null && !user.getBooks().isBlank()}">
                            <fieldset class="form-group">
                                <label>User Books</label> <input type="text"
                                    value="<c:out value='${user.getBooks()}' />" class="form-control"
                                    name="books" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${user.getInterests() != null && !user.getInterests().isBlank()}">
                            <fieldset class="form-group">
                                <label>User Interests</label> <input type="text"
                                    value="<c:out value='${user.getInterests()}' />" class="form-control"
                                    name="interests" required="required" readonly>
                            </fieldset>
                        </c:if>

                        <c:if test="${locations != null}">
                        <label>User Locations</label>
                            <c:forEach  var="location" items="${locations}">
                                <p>
                                    ${location}
                                </p>
                            </c:forEach>
                        </c:if>

                        <c:if test="${notes != null}">
                        <label>User Notes</label>
                            <c:forEach  var="note" items="${notes}">
                                <p>
                                    ${note.getText()}
                                </p>
                            </c:forEach>
                        </c:if>
                    </c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>