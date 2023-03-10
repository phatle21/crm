<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<%@page import="com.cybersoft.crm.variable.Variable"%>
<jsp:include page="/layout/header.jsp"></jsp:include>
<div class="preloader">
    <div class="cssload-speeding-wheel"></div>
</div>
<div id="wrapper">
    <jsp:include page="/layout/navbar.jsp"></jsp:include>
    <jsp:include page="/layout/menu.jsp"></jsp:include>
    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">Thêm mới thành viên</h4>
                </div>
            </div>
            <!-- /.row -->
            <!-- .row -->
            <div class="row">
                <div class="col-md-2 col-12"></div>
                <div class="col-md-8 col-xs-12">
                    <div class="white-box">
                        <form class="form-horizontal form-material" action="<%= contextPath %>/user/add" method="post">
                            <div class="form-group">
                                <label class="col-md-12">Họ và tên</label>
                                <div class="col-md-12">
                                    <input required type="text" name="fullname" placeholder="Johnathan Doe"
                                           class="form-control form-control-line">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="example-email" class="col-md-12">Email</label>
                                <div class="col-md-12">
                                    <input required type="email" placeholder="johnathan@admin.com"
                                           class="form-control form-control-line" name="email"
                                           id="example-email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12">Mật khẩu</label>
                                <div class="col-md-12">
                                    <input minlength="6" required type="password" name="password" placeholder="*****"
                                           class="form-control form-control-line">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12">Số điện thoại</label>
                                <div class="col-md-12">
                                    <input required minlength="10" maxlength="12" type="text" name="phone" placeholder="038 767 9089"
                                           class="form-control form-control-line">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12">Địa chỉ</label>
                                <div class="col-md-12">
                                    <input required type="text" name="address" placeholder="Ho Chi Minh City"
                                           class="form-control form-control-line">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-12">Chọn quyền</label>
                                <div class="col-sm-12">
                                    <select required class="form-control form-control-line" name="role">
                                        <option value="">Chọn quyền</option>
                                        <c:forEach var="role" items="${list_role}">
                                            <option value="${role.getId()}">${role.getRoleName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <button type="submit" class="btn btn-success">Thêm mới</button>
                                    <a href="<%= contextPath %>/user" class="btn btn-primary">Quay lại</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-2 col-12"></div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
        <jsp:include page="/layout/footer.jsp"></jsp:include>
    </div>
</div>

<!-- /#wrapper -->
<jsp:include page="/layout/script.jsp"></jsp:include>
</body>
</html>