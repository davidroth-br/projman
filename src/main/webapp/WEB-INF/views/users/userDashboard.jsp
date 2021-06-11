<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<%@include file="../_menu.jsp" %>
<h2>${user.fullName}'s Dashboard</h2>

<c:if test="${user.projectsLead.size() != 0}">
    You are the leader of these projects:<br>
    <c:forEach var="project" items="${user.projectsLead}">
        &emsp;${project.name}<br>
    </c:forEach>
    <br>
</c:if>
Assigned Tasks: ${tasks}
<br><br>
&emsp;Completed: ${completed}
<br>
&emsp;&emsp;On time: ${onTime}
<br>
&emsp;&emsp;Late: ${late}
<br><br>
&emsp;Pending: ${pending}
<br>
&emsp;&emsp;overdue: ${overdue}
</body>
</html>
