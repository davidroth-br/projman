<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Add Project</title>
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
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/projects/list?message='">Back</button>
</body>
</html>
