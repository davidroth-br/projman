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
Start Date: <fmt:formatDate value="${task.deadline}" type="date"/>
<br><br>
Priority: ${priorityList[task.priority]}
<br><br>
State: ${stateList[task.state]}
<br><br>
Completion Date: <fmt:formatDate value="${task.completionDate}" type="date"/>
<br><br>
Project: ${task.project.name}
<br><br>
Allocated to:
<c:forEach var="user" items="${task.users}">
    ${user.firstName} ${user.lastName}<br>
</c:forEach>
<br>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tasks/list?message='">Back</button>
</body>
</html>
