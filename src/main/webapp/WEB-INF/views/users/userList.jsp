<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Users</title>
</head>
<body>
<%@include file="../_menu.jsp" %>

<c:if test="${message != null}">
    <h3 style="${messageColor}">${message}</h3>
</c:if>
<a href="<c:url value="/users/admin/new"/>"><h2>Add New User</h2></a>
<h2>User List</h2>
<c:if test="${!empty userList}">
    <table>
        <tr>
            <th>Collaborator</th>
            <th>User Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Role</th>
            <th>Enabled</th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>
                    <a href="<c:url value="/users/admin/details/${user.id}"/>">${user.firstName} ${user.lastName}</a>
                </td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>
                    <c:if test="${user.role.roleId == 1}">Admin</c:if>
                    <c:if test="${user.role.roleId == 2}">User</c:if>
                </td>
                <td>${user.enabled}</td>
                <td><a href="<c:url value="/users/admin/newPass/${user.id}"/>">Change Password</a></td>
                <td><a href="<c:url value="/users/admin/edit/${user.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/users/admin/remove/${user.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
