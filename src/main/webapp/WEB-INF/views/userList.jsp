<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Add User</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<c:if test="${newUserName != null}">
  <h2>${newUser} was successfully added as ${newUserName}!</h2>
</c:if>

<a href="<c:url value="/users/new"/>"><h2>Add New User</h2></a>
<br>
<h2>User List</h2>
<c:if test="${!empty userList}">
  <table>
    <tr>
      <th>Collaborator</th>
      <th>User Name</th>
      <th>Email</th>
      <th>Phone</th>
      <th>Enabled</th>
    </tr>
    <c:forEach items="${userList}" var="user">
      <tr>
        <td>${user.firstName} ${user.lastName}</td>
        <td>${user.userName}</td>
        <td>${user.email}</td>
        <td>${user.phone}</td>
        <td>${user.enabled}</td>
        <td><a href="<c:url value="/users/edit/${user.id}"/>">Edit</a></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>
</html>
