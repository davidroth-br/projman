<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${sessionScope.currentUser.fullName}'s Projects</title>
</head>
<body>
<%@include file="../_menu.jsp" %>

<c:if test="${message != null}">
    <h2>${message}</h2>
</c:if>
<h2>Projects You Lead</h2>
<c:set var="projectName" value=""/>
<table>
    <c:forEach items="${taskList}" var="task">
        <c:if test="${projectName != task.project.name}">
            <c:if test="${projectName != ''}">
                <tr>
                    <td><br></td>
                </tr>
            </c:if>
            <tr>
                <th>${task.project.name}</th>
                <th colspan="3">
                    - <a href="<c:url value="/tasks/leader/new/${task.project.id}"/>">Add New Task</a>
                    | <a href="<c:url value="/projects/leader/manageMembers/${task.project.id}"/>">Manage Project
                    Members</a>
                </th>
            </tr>
            <c:set var="projectName" value="${task.project.name}"/>
            <tr style="text-align:left">
                <th></th>
                <th>Task</th>
                <th>Allocated to</th>
                <th>Deadline</th>
                <th>Priority</th>
                <th>State</th>
                <th></th>
                <th>Completion Date</th>
            </tr>
        </c:if>
        <c:if test="${empty taskList}">
            <h3>No tasks yet.</h3>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}${action}" name="changeState">
            <tr style="vertical-align:top">
                <td></td>
                <td><a href="<c:url value="/tasks/details/${task.id}/leader"/>">${task.name}</a></td>
                <td>
                    <c:forEach items="${task.users}" var="user">
                        <a href="<c:url value="/users/leader/details/${user.id}/tasks"/>">${user.firstName} ${user.lastName}</a>
                        <br>
                    </c:forEach>
                </td>
                <td><fmt:formatDate value="${task.deadline}" type="date"/></td>
                <td>${priorityList[task.priority]}</td>
                <td>
                    <select name="state">
                        <c:forEach items="${stateList}" var="stateOption">
                            <c:set var="selected" value=""/>
                            <c:if test="${stateOption.key == task.state}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <option value="${stateOption.key}" ${selected}>${stateOption.value}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input name="submit" type="submit" value="update"/></td>
                <td><fmt:formatDate value="${task.completionDate}" type="date"/></td>
                <td><a href="<c:url value="/tasks/leader/edit/${task.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/tasks/leader/remove/${task.id}"/>">Delete</a></td>
                <input type="hidden" name="id" value="${task.id}"/>
            </tr>
        </form>
    </c:forEach>
</table>
</body>
</html>
