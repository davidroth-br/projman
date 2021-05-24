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
    <h2>${message}</h2>
</c:if>

<a href="<c:url value="/tasks/new"/>"><h2>Add New Task</h2></a>
<br>
<h2>Project List</h2>
<c:if test="${!empty taskList}">
    <table>
        <tr>
            <th>Project Name</th>
            <th>User Name</th>
            <th>Task</th>
            <th>Deadline</th>
            <th>Priority</th>
            <th>State</th>
            <th>Completion Date</th>
        </tr>
        <c:forEach items="${taskList}" var="task">
            <tr>
                <td>${task.projectId}</td>
                <td>${task.usersId}</td>
                <td><a href="<c:url value="/task/details/${task.id}"/>">${task.name}</a></td>
                <td><fmt:formatDate value="${task.deadline}" type="date"/></td>
                <td>${task.priority}</td>
                <td>${task.state}</td>
                <td><fmt:formatDate value="${task.completionDate}" type="date"/></td>
                <td><a href="<c:url value="/projects/edit/${task.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/projects/remove/${task.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
