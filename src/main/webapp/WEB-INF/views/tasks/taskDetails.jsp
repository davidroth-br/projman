<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${task.name} Details</title>
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
Completion Date: ${completionDate}
<br><br>
Project: ${task.project.name} (Leader: ${task.project.leader.firstName} ${task.project.leader.lastName})
<br><br>
Allocated to:<br>
<c:forEach var="user" items="${task.users}">
    &emsp;${user.firstName} ${user.lastName}<br>
</c:forEach>
<br>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tasks/${from}/list?message='">Back</button>
</body>
</html>
