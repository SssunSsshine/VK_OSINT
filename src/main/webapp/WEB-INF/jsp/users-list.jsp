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

                    <c:if test="${users != null}">
                        <table class="table">
                            <thead>
                              <tr>
                                <th scope="col">Photo</th>
                                <th scope="col">Information</th>
                              </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>
                                            <img src=${String.valueOf(user.getPhoto200())} class = "pic">
                                        </td>
                                        <td>
                                            <p>ID: ${user.id}</p>
                                            <p>Имя: ${user.firstName}</p>
                                            <p>Фамилия: ${user.lastName}</p>
                                            <p>День рождения: ${user.bdate}</p>
                                            <p>Страна: ${user.country}</p>
                                            <p>Город: ${user.city}</p>
                                            <p>Пол: ${user.sex}</p>
                                            <p>Интересы: ${user.interests}</p>
                                            <p>Статус: ${user.status}</p>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                          </table>

                       <button type="submit" class="btn btn-primary btn-lg btn-block fs-btn">Download</button>
                    </c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>