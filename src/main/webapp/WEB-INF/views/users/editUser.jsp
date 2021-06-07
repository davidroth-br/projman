<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%@include file="../_menu.jsp" %>
<f:form method="POST" action="${pageContext.request.contextPath}/users/admin/validateEdit" modelAttribute="user">
    User Name: <f:input path="userName" value="${user.userName}"/> <f:errors path="userName"/>
    <br><br>
    First Name: <f:input path="firstName" value="${user.firstName}"/> <f:errors path="firstName"/>
    <br><br>
    Last Name: <f:input path="lastName" value="${user.lastName}"/> <f:errors path="lastName"/>
    <br><br>
    Email: <f:input path="email" type="email" value="${user.email}"/> <f:errors path="email"/>
    <br><br>
    Phone: <f:input path="phone" type="tel" value="${user.phone}" placeholder="(999) 999-9999"/> <f:errors
        path="phone"/>
    <br><br>
    Role:
    <c:if test="${isChangingOwnRole}">
        You cannot edit your own role
    </c:if>
    <br>
    User <f:radiobutton path="role.roleId" value="2"/>
    <br>
    Admin: <f:radiobutton path="role.roleId" value="1"/>
    <br><br>
    Enabled
    <f:checkbox path="enabled"/>
    <c:if test="${isDisablingSelf}">
        You cannot disable yourself
    </c:if>
    <br><br>
    <f:hidden path="id" value="${user.id}"/>
    <f:hidden path="encryptedPassword" value="${user.encryptedPassword}"/>
    <input type="submit"/>
</f:form>
</body>
</html>
