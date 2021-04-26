<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <title>Add User</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<c:if test="${newProjectName != null}">
  <h2>${newProjectName} was successfully added!</h2>
</c:if>

<a href="<c:url value="/projects/new"/>"><h2>Add New Project</h2></a>
<br>
<h2>User List</h2>
<c:if test="${!empty projectList}">
  <table>
    <tr>
      <th>Project</th>
      <th>Start Date</th>
      <th>End Date</th>
      <th>Leader</th>
    </tr>
    <c:forEach items="${projectList}" var="project">
      <tr>
        <td>${project.name}</td>
        <td><fmt:formatDate value="${project.startDate}" type="date" /></td>
        <td><fmt:formatDate value="${project.endDate}" type="date" /></td>
        <td>${project.leader.firstName} ${project.leader.lastName}</td>
        <td><a href="<c:url value="/projects/edit/${project.id}"/>">Edit</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>
</html>
