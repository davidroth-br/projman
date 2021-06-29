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
    <h3 style="${messageColor}">${message}</h3>
</c:if>
<h2>Projects You Lead</h2>
<c:set var="projectName" value=""/>
<table>
    <c:forEach items="${projectList}" var="project">
        <c:if test="${projectName != project.name}">
            <c:if test="${projectName != ''}">
                <tr>
                    <td><br></td>
                </tr>
            </c:if>
            <tr>
                <th>
                    <h3 style="text-align:right">${project.name} -</h3>
                </th>
                <th colspan="3">
                    <h3 style="text-align:left">
                        <a href="<c:url value="/tasks/leader/new/${project.id}"/>">Add New Task</a> |
                        <a href="<c:url value="/projects/leader/manageMembers/${project.id}"/>">Manage Members</a>
                    </h3>
                </th>
            </tr>
            <c:set var="projectName" value="${project.name}"/>
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
        <c:if test="${empty project.tasks}">
            <tr>
                <th></th>
                <th><h3>No tasks yet.</h3></th>
            </tr>
        </c:if>
        <c:forEach items="${project.tasks}" var="task">
            <form method="post" action="${pageContext.request.contextPath}${action}/${task.id}" name="changeState">
                <tr style="vertical-align:top">
                    <td></td>
                    <td><a href="<c:url value="/tasks/details/${task.id}/leader"/>">${task.name}</a></td>
                    <td>
                        <c:forEach items="${task.users}" var="user">
                            <a href="<c:url value="/users/projects/details/${user.id}"/>">${user.firstName} ${user.lastName}</a>
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
    </c:forEach>
</table>
</body>
</html>
