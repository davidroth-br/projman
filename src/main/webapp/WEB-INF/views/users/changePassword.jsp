<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <h3 class="h3 text-center fw-bold">Change Password</h3>
    <f:form method="POST" action="${pageContext.request.contextPath}/users/admin/validateNewPass" modelAttribute="user">
        <div class="container">
            <div class="row mb-3">
                <div class="col">
                    <label for="currentPassword"
                           class="label ${empty currentPasswordMessage ? "" : "text-danger label"}">Current
                        Password:</label>
                    <span class="text-danger label">${currentPasswordMessage}</span><br>
                    <input id="currentPassword" name="currentPassword" type="password" size="35">
                </div>
                <div class="col">
                    <label for="newPassword" class="label ${empty newPasswordMessage ? "" : "text-danger label"}">New
                        Password:</label>
                    <span class="text-danger label">${newPasswordMessage}</span><br>
                    <input id="newPassword" name="newPassword" type="password" size="35"/>
                    <br>
                    <label for="passCheck" class="label ${empty repeatMessage ? "" : "text-danger label"}">Confirm New
                        Password: </label>
                    <span class="text-danger label">${repeatMessage}</span><br>
                    <input id="passCheck" name="passCheck" type="password" size="35"/>
                </div>
            </div>
        </div>
        <f:hidden path="id" value="${user.id}"/>
        <f:hidden path="userName" value="${user.userName}"/>
        <f:hidden path="encryptedPassword" value="${user.encryptedPassword}"/>
        <f:hidden path="enabled" value="${user.enabled}"/>
        <f:hidden path="firstName" value="${user.firstName}"/>
        <f:hidden path="lastName" value="${user.lastName}"/>
        <f:hidden path="email" value="${user.email}"/>
        <f:hidden path="phone" value="${user.phone}"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button class="btn btn-sm btn-info text-center me-2" type="submit">Save</button>
                <a href="<c:url value="/users/admin/list"/>"
                   class="btn btn-sm btn-secondary text-center ms-2">Cancel</a>
            </div>
        </div>
    </f:form>
</div>
</body>
</html>
