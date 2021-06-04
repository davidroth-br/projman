<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal != null}">
            <c:choose>
                <c:when test="${sessionScope.values().toString().contains('ROLE_ADMIN')}">
                    <a href="<c:url value="/welcome"/>">Home</a> |
                    <a href="<c:url value="/users/admin/list?message="/>">Users</a> |
                    <a href="<c:url value="/projects/admin/list?message="/>">Projects</a> |
                    <a href="<c:url value="/tasks/admin/list?message="/>">Tasks</a> |
                    <a href="<c:url value="/logout"/>">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/welcome"/>">Home</a> |
                    <a href="<c:url value="/userInfo"/>">User Info</a> |
                    <a href="<c:url value="/logout"/>">Logout</a>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <a href="<c:url value="/login"/>">Login</a>
        </c:otherwise>
    </c:choose>
</div>
