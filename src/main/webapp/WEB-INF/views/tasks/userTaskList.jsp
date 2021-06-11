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
<c:if test="${!empty taskList}">
    <c:set var="projectName" value=""/>
    <table>
        <c:forEach items="${taskList}" var="task">
            <c:if test="${projectName != task.project.name}">
                <tr>
                    <th>${task.project.name}</th>
                </tr>
                <c:set var="projectName" value="${task.project.name}"/>
                <tr style="text-align:left">
                    <th>&nbsp;</th>
                    <th>Task</th>
                    <th>Deadline</th>
                    <th>Priority</th>
                    <th>State</th>
                    <th>Completion Date</th>
                </tr>
            </c:if>
            <tr style="vertical-align:top">
                <form method="post" action="${pageContext.request.contextPath}${action}" name="changeState">
                    <td>&nbsp;</td>
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
                    <td><fmt:formatDate value="${task.completionDate}" type="date"/></td>
                    <input type="hidden" name="id" value="${task.id}"/>
                    <td><input name="submit" type="submit" value="update"/></td>
                </form>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
