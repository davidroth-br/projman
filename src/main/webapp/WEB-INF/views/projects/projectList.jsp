<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Projects</title>
</head>
<body>
<%@include file="../_menu.jsp" %>

<c:if test="${message != null}">
    <h3 style="${messageColor}">${message}</h3>
</c:if>
<h2><a href="<c:url value="/projects/admin/new"/>">Add New Project</a></h2>
<h2>Project List</h2>
<c:if test="${!empty projectList}">
    <table>
        <tr>
            <th>Project</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Leader</th>
        </tr>
        <c:forEach items="${projectList}" var="project">
            <tr>
                <td><a href="<c:url value="/projects/admin/details/${project.id}"/>">${project.name}</a></td>
                <td><fmt:formatDate value="${project.startDate}" type="date"/></td>
                <td><fmt:formatDate value="${project.endDate}" type="date"/></td>
                <td>${project.leader.firstName} ${project.leader.lastName}</td>
                <td><a href="<c:url value="/projects/admin/edit/${project.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/projects/leader/manageMembers/${project.id}"/>">Manage Members</a></td>
                <td><a href="<c:url value="/projects/admin/remove/${project.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
