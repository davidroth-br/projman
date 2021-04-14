<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Project</title>
</head>
<body>
<f:form method="post" action="test" modelAttribute="project">
    Project Id: <f:input path="id"/> <f:errors path="id"/>
    <br><br>
    Project Name: <f:input path="name"/> <f:errors path="name"/>
    <br><br>
    Description: <f:input path="description"/> <f:errors path="description"/>
    <br><br>
    Start Date: <f:input path="startDate"/> <f:errors path="startDate"/>
    <br><br>
    End Date: <f:input path="endDate"/> <f:errors path="endDate"/>
    <br><br>
    Leader Id: <f:input path="leaderId"/> <f:errors path="leaderId"/>
    <br><br>
    <input type="submit"/>
    <br><br>
</f:form>
<h3>Project List</h3>
<c:if test="${!empty projectList}">
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
</c:if>
</body>
</html>
