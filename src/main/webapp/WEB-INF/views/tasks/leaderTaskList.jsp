<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tasks</title>
</head>
<body>
<%@include file="../_menu.jsp" %>

<c:if test="${message != null}">
    <h2>${message}</h2>
</c:if>
<h2>Task List</h2>
<c:if test="${!empty taskList}">
    <c:set var="projectName" value=""/>
    <table>
        <c:forEach items="${taskList}" var="task">
            <c:if test="${projectName != task.project.name}">
                <c:if test="${projectName != ''}">
                    <tr><td><br></td></tr>
                </c:if>
                <tr>
                    <th>${task.project.name} - <a href="<c:url value="/tasks/leader/new"/>">Add New Task</a></th>
                </tr>
                <c:set var="projectName" value="${task.project.name}"/>
                <tr style="text-align:left">
                    <th>&nbsp;</th>
                    <th>Task</th>
                    <th>Allocated to</th>
                    <th>Deadline</th>
                    <th>Priority</th>
                    <th>State</th>
                    <th>Completion Date</th>
                </tr>
            </c:if>
            <tr style="vertical-align:top">
                <td>&nbsp;</td>
                <td><a href="<c:url value="/tasks/details/${task.id}/leader"/>">${task.name}</a></td>
                <td>
                    <c:forEach items="${task.users}" var="user">
                        <a href="<c:url value="/users/leader/details/${user.id}/tasks"/>">${user.firstName} ${user.lastName}</a>
                        <br>
                    </c:forEach>
                </td>
                <td><fmt:formatDate value="${task.deadline}" type="date"/></td>
                <td>${priorityList[task.priority]}</td>
                <td>${stateList[task.state]}</td>
                <td><fmt:formatDate value="${task.completionDate}" type="date"/></td>
                <td><a href="<c:url value="/tasks/leader/edit/${task.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/tasks/leader/remove/${task.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
