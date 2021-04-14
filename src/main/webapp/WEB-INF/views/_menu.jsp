<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">

    <c:choose>
        <c:when test="${pageContext.request.userPrincipal != null}">
            <a href="<c:url value="/welcome"/>">Home</a> |
            <a href="<c:url value="/userInfo"/>">User Info</a> |
            <a href="<c:url value="/admin"/>">Admin</a> |
            <a href="<c:url value="/logout"/>">Logout</a> |
            <a href="<c:url value="/users/new"/>">Add User</a> |
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/login"/>">Login</a>
        </c:otherwise>
    </c:choose>
</div>
