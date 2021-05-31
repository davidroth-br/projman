<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Task ${task.name} Details</title>
</head>
<body>
Task Name: ${task.name}
<br><br>
Description: ${task.description}
<br><br>
Deadline: <fmt:formatDate value="${task.deadline}" type="date"/>
<br><br>
Priority: ${priorityList[task.priority]}
<br><br>
State: ${stateList[task.state]}
<br><br>
Completion Date:
<c:choose>
    <c:when test="${task.completionDate == null}">
        Not Complete
    </c:when>
    <c:otherwise>
        <fmt:formatDate value="${task.completionDate}" type="date"/>
    </c:otherwise>
</c:choose>
<br><br>
Project: ${task.project.name}
<br><br>
Allocated to:<br>
<c:forEach var="user" items="${task.users}">
    &emsp;${user.firstName} ${user.lastName}<br>
</c:forEach>
<br>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tasks/list?message='">Back</button>
</body>
</html>
