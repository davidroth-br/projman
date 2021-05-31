<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${user.firstName} ${user.lastName} Details</title>
</head>
<body>
User Name: ${user.userName}
<br><br>
First Name: ${user.firstName}
<br><br>
Last Name: ${user.lastName}
<br><br>
Email: ${user.email}
<br><br>
Phone Number: ${user.phone}
<br><br>
Role: ${fn:substring(user.role.roleName, 5, fn:length(user.role.roleName))}
<br><br>
Projects / Tasks:<br>
<c:set var="previousProject" value=""/>
<c:forEach var="task" items="${taskList}">
    <c:if test="${task.project.name != previousProject}">
        <c:if test="${previousProject != ''}"><br></c:if>
        &emsp;${task.project.name}
        <c:forEach var="userLead" items="${userLeads}">
            <c:if test="${task.project.name == userLead}">
                (Leader)
            </c:if>
        </c:forEach>
        <br>
        <c:set var="previousProject" value="${task.project.name}"/>
    </c:if>
    &emsp;&emsp;${task.name}<br>
</c:forEach>
<br><br>
<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/tasks/list?message='">Back</button>
</body>
</html>
