<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tasks</title>
</head>
<body>
<%@include file="../_menu.jsp" %>
<h2>Your Tasks</h2>
<c:choose>
    <c:when test="${!empty taskList}">
        <c:set var="projectName" value=""/>
        <table>
            <c:forEach items="${taskList}" var="task">
                <c:if test="${projectName != task.project.name}">
                    <tr style="text-align:left">
                        <th>
                            <h3>
                            <c:if test="${projectName != ''}"><br></c:if>
                                ${task.project.name}
                            </h3>
                        </th>
                        <th colspan="5" style="vertical-align:bottom">
                            <h3>
                            (Project Leader: ${task.project.leader.fullName})
                            <a href="<c:url value="/projects/user/members/${task.project.id}"/>">Show members</a>
                            </h3>
                        </th>
                    </tr>
                    <c:set var="projectName" value="${task.project.name}"/>
                    <tr style="text-align:left">
                        <th></th>
                        <th>Task</th>
                        <th>Deadline</th>
                        <th>Priority</th>
                        <th>State</th>
                        <th></th>
                        <th>Completion Date</th>
                    </tr>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}${action}" name="changeState">
                    <tr style="vertical-align:top">
                        <td></td>
                        <td><a href="<c:url value="/tasks/details/${task.id}/user"/>">${task.name}</a></td>
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
                        <input type="hidden" name="id" value="${task.id}"/>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h3>You don't have any tasks assigned to you</h3>
    </c:otherwise>
</c:choose>
</body>
</html>
