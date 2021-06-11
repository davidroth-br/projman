<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Change Password</title>
</head>
<body>
<%@include file="../_menu.jsp" %>
<f:form method="POST" action="${pageContext.request.contextPath}/users/admin/validateNewPass" modelAttribute="user">
    Current Password: <input name="currentPassword" type="password"> ${currentPasswordMessage}
    <br><br>
    New Password: <input name="newPassword" type="password"/> ${newPasswordMessage}
    <br><br>
    Repeat New Password: <input name="passCheck" type="password"/> ${repeatMessage}
    <f:hidden path="id" value="${user.id}"/>
    <f:hidden path="userName" value="${user.userName}"/>
    <f:hidden path="encryptedPassword" value="${user.encryptedPassword}"/>
    <f:hidden path="enabled" value="${user.enabled}"/>
    <f:hidden path="firstName" value="${user.firstName}"/>
    <f:hidden path="lastName" value="${user.lastName}"/>
    <f:hidden path="email" value="${user.email}"/>
    <f:hidden path="phone" value="${user.phone}"/>
    <br><br>
    <input type="submit"/>
</f:form>
</body>
</html>
