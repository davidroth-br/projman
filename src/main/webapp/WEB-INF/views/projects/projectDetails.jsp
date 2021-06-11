<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${project.name} Details</title>
</head>
<body>
Project Name: ${project.name}
<br><br>
Description: ${project.description}
<br><br>
Start Date: <fmt:formatDate value="${project.startDate}" type="date"/>
<br><br>
End Date: <fmt:formatDate value="${project.endDate}" type="date"/>
<br><br>
Leader: ${project.leader.firstName} ${project.leader.lastName}
<br><br>
Members:
<br>
<c:forEach items="${project.users}" var="user">
    &emsp;${user.firstName} ${user.lastName}<br>
</c:forEach>
<br><br>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/projects/admin/list?message='">Back
</button>
</body>
</html>
