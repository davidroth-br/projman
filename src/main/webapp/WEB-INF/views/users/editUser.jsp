<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <h3 class="h3 text-center fw-bold">Edit User</h3>
    <f:form method="POST" action="${pageContext.request.contextPath}/users/${from}/validateEdit" modelAttribute="user">
    <div class="container">
        <div class="row mb-3">
            <div class="col">
                <f:label path="userName" cssClass="label" cssErrorClass="text-danger label">User Name:</f:label>
                <f:errors cssClass="text-danger label" path="userName"/><br>
                <f:input path="userName" size="35"/>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="firstName" cssClass="label" cssErrorClass="text-danger label">First Name:</f:label>
                <f:errors cssClass="text-danger label" path="firstName"/><br>
                <f:input path="firstName" size="35"/>
            </div>
            <div class="col">
                <f:label path="lastName" cssClass="label" cssErrorClass="text-danger label">Last Name:</f:label>
                <f:errors cssClass="text-danger label" path="lastName"/><br>
                <f:input path="lastName" size="35"/>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="email" cssClass="label" cssErrorClass="text-danger label">Email:</f:label>
                <f:errors cssClass="text-danger label" path="email"/><br>
                <f:input path="email" type="email" size="35"/>
            </div>
            <div class="col">
                <f:label path="phone" cssClass="label" cssErrorClass="text-danger label">Phone:</f:label>
                <f:errors cssClass="text-danger label" path="phone"/><br>
                <f:input id="phone" path="phone" type="text"/>
            </div>
        </div>
        <c:choose>
            <c:when test="${user.id != sessionScope.currentUser.id}">
                <div class="row mb-3">
                    <div class="col">
                        <f:label path="role.roleId" cssClass="label">Role:</f:label><br>
                        <span>User: </span><f:radiobutton path="role.roleId" value="2" checked="checked"
                                                          cssClass="align-middle"/>
                        <br>
                        <span>Admin: </span><f:radiobutton path="role.roleId" value="1" cssClass="align-middle"/>
                    </div>
                    <div class="col">
                        <f:label path="enabled">Enabled:</f:label>
                        <f:checkbox path="enabled" cssClass="align-middle"/>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <f:hidden path="role.roleId" value="${user.role.roleId}"/>
                <f:hidden path="enabled" value="${user.enabled}"/>
            </c:otherwise>
        </c:choose>
        <f:hidden path="id" value="${user.id}"/>
        <f:hidden path="encryptedPassword" value="${user.encryptedPassword}"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button class="btn btn-sm btn-info text-center me-2" type="submit">Save</button>
                <a href="<c:url value="/users/${from}/${from == 'admin' ? 'list' : 'show'}"/>"
                   class="btn btn-sm btn-secondary text-center ms-2">Cancel</a>
            </div>
        </div>
        </f:form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/formatPhoneNumber.js"></script>
</body>
</html>
