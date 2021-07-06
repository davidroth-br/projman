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
    <c:if test="${message != null}">
        <h3 class="fs-5 text-center ${messageColor}">${message}</h3>
    </c:if>
    <c:set var="currentUser" value="${sessionScope.currentUser}"/>
    <h3 class="h3 text-center fw-bold">${currentUser.fullName}'s Profile</h3>
    <div class="container">
        <div class="row mb-3">
            <div class="col">
                <span class="fw-bold">User Name: </span>${currentUser.userName}
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <span class="fw-bold">First Name: </span>${currentUser.firstName}
            </div>
            <div class="col">
                <span class="fw-bold">Last Name: </span>${currentUser.lastName}
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <span class="fw-bold">E-mail: </span>${currentUser.email}
            </div>
            <div class="col">
                <span class="fw-bold">Phone: </span>${currentUser.phone}
            </div>
        </div>
        <div class="row mb-3">
            <div class="col text-center">
                <a href="<c:url value="/users/profile/edit"/>"
                   class="btn btn-sm btn-info text-center ms-2">Edit</a>
                <a href="<c:url value="/users/profile/changePassword"/>"
                   class="btn btn-sm btn-info text-center ms-2">Change Password</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
