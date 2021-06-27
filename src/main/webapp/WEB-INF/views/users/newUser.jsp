<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add User</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<%@include file="../_menu.jsp" %>

<f:form method="POST" action="${pageContext.request.contextPath}/users/admin/validateNew" modelAttribute="user">

    User Name: <f:input path="userName"/> <f:errors cssClass="error" path="userName"/>
    <br><br>
    Password: <f:input path="encryptedPassword" type="password"/> <f:errors cssClass="error" path="encryptedPassword"/>
    <br><br>
    Repeat Password: <input name="passCheck" type="password"/> <nobr class="error">${repeatMessage}</nobr>
    <br><br>
    First Name: <f:input path="firstName"/> <f:errors cssClass="error" path="firstName"/>
    <br><br>
    Last Name: <f:input path="lastName"/> <f:errors cssClass="error" path="lastName"/>
    <br><br>
    Email: <f:input path="email" type="email"/> <f:errors cssClass="error" path="email"/>
    <br><br>
    Phone: <f:input path="phone" type="tel" placeholder="(999) 999-9999"/> <f:errors cssClass="error" path="phone"/>
    <br><br>
    Role:
    User <f:radiobutton path="role.roleId" value="2" checked="checked"/>
    <br>
    Admin: <f:radiobutton path="role.roleId" value="1"/>
    <br><br>
    Enabled <f:checkbox path="enabled"/>
    <br><br>
    <input type="submit"/>
    <br><br>
</f:form>
</body>
</html>
