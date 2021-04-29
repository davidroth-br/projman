<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%@include file="_menu.jsp" %>
<f:form method="POST" action="${pageContext.request.contextPath}/users/validateNewPass" modelAttribute="user">
    Current Password: <input name="currentPassword" type="password">
    <c:if test="${isNotPassword}">
        Incorrect password. Please try again.
    </c:if>
    <br><br>
    New Password: <input name="newPassword" type="password"/>
    <c:if test="${isNotMatch}">
        Passwords did not match. Please try again.
    </c:if>
    <br><br>
    Repeat New Password: <input name="passCheck" type="password"/>
    <c:if test="${isNotMatch}">
        Passwords did not match. Please try again.
    </c:if>
    <f:hidden path="id" value="${user.id}"/>
    <f:hidden path="userName" value="${user.userName}"/>
    <f:hidden path="encryptedPassword" value="${user.encryptedPassword}"/>
    <f:hidden path="firstName" value="${user.firstName}"/>
    <f:hidden path="lastName" value="${user.lastName}"/>
    <f:hidden path="email" value="${user.email}"/>
    <f:hidden path="phone" value="${user.phone}"/>
    <f:hidden path="enabled" value="${user.enabled}"/>
    <br><br>
    <input type="submit"/>
</f:form>
</body>
</html>
