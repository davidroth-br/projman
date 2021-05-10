<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Add User</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<c:if test="${message != null}">
    <h2>${message}</h2>
</c:if>

<a href="<c:url value="/users/admin/new"/>"><h2>Add New User</h2></a>
<br>
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
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${fn:substring(user.role.roleName, 5, fn:length(user.role.roleName))}
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