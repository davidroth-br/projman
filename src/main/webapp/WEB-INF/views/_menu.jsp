<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">
    <c:if test="${pageContext.request.userPrincipal != null}">
        <a href="<c:url value="/dashboard"/>">Dashboard</a> |
        <a href="<c:url value="/tasks/user/list"/>">Your Tasks</a> |
        <c:if test="${sessionScope.currentUser.isLeader()}">
            <a href="<c:url value="/projects/leader/list"/>">Your Projects</a> |
        </c:if>
        <c:if test="${sessionScope.currentUser.isAdmin()}">
            <a href="<c:url value="/users/admin/list"/>">All Users</a> |
            <a href="<c:url value="/projects/admin/list"/>">All Projects</a> |
        </c:if>
        <a href="<c:url value="/logout"/>">Logout</a>
    </c:if>
</div>