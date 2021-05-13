<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>All Projects</title>
</head>
<body>
<h3>Project List</h3>

<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Leader Id</th>
    </tr>
    <c:forEach items="${projectList}" var="project">
        <tr>
            <td>${project.id}</td>
            <td>${project.name}</td>
            <td>${project.description}</td>
            <td>${project.startDate}</td>
            <td>${project.endDate}</td>
            <td>${project.leaderId}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
