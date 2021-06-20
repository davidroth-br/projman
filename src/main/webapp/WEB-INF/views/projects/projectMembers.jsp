<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${project.name} Members</title>
</head>
<body>
<h2>${project.name} Members</h2>
<p>
    <c:forEach items="${project.users}" var="user">
        ${user.fullName}<br>
    </c:forEach>
</p>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tasks/user/list?message='">
    Back
</button>
</body>
</html>
