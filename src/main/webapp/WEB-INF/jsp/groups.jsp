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
                          <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/photos">Фотографии</a>
                        </li>
                        <li class="nav-item">
                          <a class="nav-link active" aria-current="page" href="<%=request.getContextPath()%>/friends">Друзья</a>
                        </li>
                      </ul>
                    </div>
                </div>
            </nav>
    </header>

	<div class="container col-md-7">
		<div class="card" style = "width: 800px;">
			<div class="card-body">
			    <form action="download-groups" method="post">
                    <caption>
                        <h2>
                             Группы
                        </h2>
                    </caption>

                    <c:if test="${user != null}">
                        <div class="container">
                           <div class="row">
                               <div class="col-sm text-center" >
                                     <button type="submit" class="btn btn-primary btn-lg btn-block fs-btn">Загрузить</button>
                               </div>
                           </div>
                        </div>
                        <fieldset class="form-group">
                            <label>ID пользователя</label> <input type="text"
                                value="<c:out value='${user.getId()}' />" class="form-control"
                                name="id" required="required" readonly>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Фамилия</label> <input type="text"
                                value="<c:out value='${user.getLastName()}' />" class="form-control"
                                name="surname" required="required" readonly>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Имя</label> <input type="text"
                                value="<c:out value='${user.getFirstName()}' />" class="form-control"
                                name="name" required="required" readonly>
                        </fieldset>

                        <c:if test="${groups != null}">
                            <h2>Группы</h2>
                            <table class="table">
                                <thead>
                                    <th scope="col" style="width: 4%;">ID</th>
                                    <th scope="col" style="width: 7%;">Название</th>
                                    <th scope="col" style="width: 7%;">Статус</th>
                                    <th scope="col" style="width: 14%;">Описание</th>
                                </thead>
                                <tbody>

                                    <c:forEach var="group" items="${groups}">
                                        <tr>
                                            <td>
                                                <p>${group.getId()}</p>
                                            </td>
                                            <td>
                                                <p>${group.getName()}</p>
                                            </td>
                                            <td>
                                                <p>${group.getStatus()}</p>
                                            </td>
                                            <td>
                                                <p>${group.getDescription()}</p>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>

                        <c:if test="${mutualGroups != null}">
                            <h2>Общие Группы</h2>
                            <table class="table">
                                <thead>
                                  <tr>
                                    <th scope="col">Информация о группе</th>
                                    <th scope="col">ID друзей</th>
                                  </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="entry" items="${mutualGroups}">
                                        <tr>
                                            <td>
                                                <p>${entry.getKey()}</p>
                                            </td>
                                            <td>
                                                <p>${entry.getValue()}</p>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </c:if>
				</form>
			</div>
		</div>
	</div>
</body>
</html>