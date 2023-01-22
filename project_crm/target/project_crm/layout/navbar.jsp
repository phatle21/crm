<%@page import="com.cybersoft.crm.module.User" %>
<%@page import="com.cybersoft.crm.variable.Variable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
    User userLogin = (User) request.getSession().getAttribute(Variable.USER_LOGIN);
%>
<style>
    #crm{
        -webkit-text-stroke: 1.5px red;
        font-size: 20px;
    }
    #crm:hover{
        color: black;
        -webkit-text-stroke: 1.5px yellow;
        font-size: 23px;
        transition: 1.5s;
    }
</style>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top m-b-0">
    <div class="navbar-header">
        <a class="navbar-toggle hidden-sm hidden-md hidden-lg "
           href="javascript:void(0)" data-toggle="collapse"
           data-target=".navbar-collapse"> <i class="fa fa-bars"></i>
        </a>
        <div class="top-left-part">
            <a class="logo"
               href="<%=contextPath%>/home">
                <b>
                <img src="<%=contextPath%>/plugins/images/pixeladmin-logo.png" alt="home" />
                </b>
                <span class="hidden-xs">
			        <b id="crm" style="width: auto;">DỰ ÁN CRM</b>
			    </span>
            </a>
        </div>

        <ul class="nav navbar-top-links navbar-right pull-right">
            <li>
                <div class="dropdown">
                    <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
                        <img src="<%=contextPath%>/plugins/images/users/varun.jpg"
                             alt="user-img" width="36" class="img-circle" />
                        <b class="hidden-xs"><%= userLogin.getFullName() %></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=contextPath%>/profile">Thông tin cá nhân</a></li>
                        <li><a href="<%=contextPath%>/profile/task">Thống kê công việc</a></li>
                        <li class="divider"></li>
                        <li><a href="<%=contextPath + Variable.URL_LOGOUT%>">Đăng
                            xuất</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>

</nav>