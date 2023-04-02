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
<style>
    .btn-primary {
           background: linear-gradient(#5725ba,#7e22a5);
           border: none;
           font-size:20pt;
           font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
       }


       .btn-primary:hover, .btn-primary:active{
           background: linear-gradient(#371778,#4d1465) ;
           border: none !important;
       }
       .fs{
              font-size:18pt;
              color: black;
              font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
          }
        .fs_header{
                font-size:30pt;
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

	<div class="container min-vh-100">
	    <div class="row justify-content-center">
	        <div class="d-flex container-fluid min-vh-100 align-items-center" >
	        <div class="col-sm text-center">
                <div class="card">
                    <div class="card-body">
                        <form action="MainServlet" method="post">
                        <c:if test="${is_id}">
                            <input type="hidden" name="action" value="user" />
                        </c:if>
                        <c:if test="${is_fullname}">
                              <input type="hidden" name="action" value="user_list" />
                        </c:if>

                        <caption>
                            <h2 class="fs fs_header">
                                <c:if test="${is_id}">
                                    Search User By ID
                                </c:if>
                                <c:if test="${is_fullname}">
                                    Search User By ID
                                </c:if>
                            </h2>
                        </caption>

                        <c:if test="${is_id}">
                            <fieldset class="form-group">
                                <label class = "fs">User ID</label> <input type="text"
                                    class="form-control" name="id" required="required">
                            </fieldset>
                        </c:if>

                        <c:if test="${is_fullname}">
                            <fieldset class="form-group">
                                <label class = "fs">User Full Name</label> <input type="text"
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