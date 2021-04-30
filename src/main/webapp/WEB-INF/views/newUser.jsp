<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add User</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<f:form method="POST" action="${pageContext.request.contextPath}/users/admin/validateNew" modelAttribute="user">

    User Name: <f:input path="userName"/> <f:errors path="userName"/>
    <br><br>
    Password: <f:input path="encryptedPassword" type="password"/> <f:errors path="encryptedPassword"/>
    <br><br>
    Repeat Password: <input name="passCheck" type="password"/> <c:if test="${isNotMatch}">Passwords did not match</c:if>
    <br><br>
    First Name: <f:input path="firstName"/> <f:errors path="firstName"/>
    <br><br>
    Last Name: <f:input path="lastName"/> <f:errors path="lastName"/>
    <br><br>
    Email: <f:input path="email" type="email"/> <f:errors path="email"/>
    <br><br>
    Phone: <f:input path="phone" type="tel" placeholder="(999) 999-9999"/> <f:errors path="phone"/>
    <br><br>
    Role:
    User <input name="role" type="radio" value="2" checked>
    <br>
    Admin <input name="role" type="radio" value="1">
    <br><br>
    Enabled <f:checkbox path="enabled" checked="checked"/>
    <br><br>
    <input type="submit"/>
    <br><br>
</f:form>
</body>
</html>
