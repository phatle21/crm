<%@page import="com.cybersoft.crm.variable.Variable"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>CRM - Đăng nhập hệ thống</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>

<div class="container text-center text-dark mt-5">
    <div class="row">
        <div class="col-lg-4 d-block mx-auto mt-5">
            <div class="row">
                <div class="col-xl-12 col-md-12 col-md-12">
                    <div class="card">
                        <form action="<%=contextPath + Variable.URL_LOGIN%>" method="post">
                            <div class="card-body wow-bg" id="formBg">
                                <h3 class="colorboard">Đăng nhập hệ thống</h3><br>

                                <span style="color: red;"><c:out value="${msg_error}" /></span>

                                <div class="input-group mb-3">
                                    <input type="text" name="email" class="form-control textbox-dg"
                                           placeholder="Email" style="color: black;">
                                </div>
                                <div class="input-group mb-4">
                                    <input type="password" name="password" class="form-control textbox-dg"
                                           placeholder="Mật khẩu" style="color: black;">
                                </div>

                                <div class="row">
                                    <div class="col-12">
                                        <button type="submit"
                                                class="btn btn-primary btn-block logn-btn">Đăng nhập</button>
                                    </div>
                                    <div class="col-12"></div>
                                </div>

                                <div class="mt-6 btn-list"></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>
