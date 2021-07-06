<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous">
    </script>
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <c:if test="${message != null}">
        <h3 class="fs-5 text-center ${messageColor}">${message}</h3>
    </c:if>
    <h3 class="h3 text-center fw-bold">User List</h3>
    <h5 class="h5 text-center"><a href="<c:url value="/users/admin/new"/>">Add User</a></h5>
    <c:if test="${!empty userList}">
        <table class="table table-sm table-borderless">
            <tr class="text-nowrap">
                <th>Collaborator</th>
                <th>User Name</th>
                <th class="text-center">Role</th>
                <th class="text-center">Enabled</th>
            </tr>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>
                        <a href="#" data-bs-toggle="modal" data-bs-target="#userModal"
                           data-bs-userFullName="${user.fullName}"
                           data-bs-userEmail="${user.email}"
                           data-bs-userPhone="${user.phone}">${user.fullName}</a>
                    </td>
                    <td>${user.userName}</td>
                    <td class="text-center">
                        <c:if test="${user.role.roleId == 1}">Admin</c:if>
                        <c:if test="${user.role.roleId == 2}">User</c:if>
                    </td>
                    <td class="text-center">${user.enabled}</td>
                    <td><a href="<c:url value="/users/admin/newPass/${user.id}"/>">Change Password</a></td>
                    <td><a href="<c:url value="/users/admin/edit/${user.id}"/>">Edit</a></td>

                    <td>
                        <c:set var="deleteId" value="${user.id}"/>
                        <c:set var="deleteMessage" value="user"/>
                        <c:set var="deleteName" value="${user.fullName}"/>
                        <c:set var="deleteUri" value="/users/admin/remove/"/>
                        <a href="#" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal_${deleteId}">Delete</a>
                    </td>
                </tr>
                <%@include file="../modals/confirmDelete.html" %>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../modals/userDetails.html" %>
<script src="${pageContext.request.contextPath}/js/userDetails.js"></script>
</body>
</html>
