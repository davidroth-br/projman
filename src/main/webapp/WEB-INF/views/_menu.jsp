<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mb-1 btn-group-sm">
    <c:if test="${pageContext.request.userPrincipal != null}">
        <a href="<c:url value="/dashboard"/>" class="btn btn-primary">Dashboard</a>
        <a href="<c:url value="/tasks/user/list"/>" class="btn btn-primary">Your Tasks</a>
        <c:if test="${sessionScope.currentUser.isLeader()}">
            <a href="<c:url value="/projects/leader/list"/>" class="btn btn-primary">Your Projects</a>
        </c:if>
        <c:if test="${sessionScope.currentUser.isAdmin()}">
            <a href="<c:url value="/users/admin/list"/>" class="btn btn-primary">All Users</a>
            <a href="<c:url value="/projects/admin/list"/>" class="btn btn-primary">All Projects</a>
        </c:if>
        <a href="<c:url value="/logout"/>" class="btn btn-primary">Logout</a>
    </c:if>
</div>