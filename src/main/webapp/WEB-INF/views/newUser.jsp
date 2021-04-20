<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Add User</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<f:form method="post" action="validate" modelAttribute="user">

  User Name: <f:input path="userName"/> <f:errors path="userName"/>
  <br><br>
  Password: <f:input path="encryptedPassword" type="password"/> <f:errors path="encryptedPassword"/>
  <br><br>
  First Name: <f:input path="firstName"/> <f:errors path="firstName"/>
  <br><br>
  Last Name: <f:input path="lastName"/> <f:errors path="lastName"/>
  <br><br>
  Email: <f:input path="email" type="email"/> <f:errors path="email"/>
  <br><br>
  Phone: <f:input path="phone" type="tel" placeholder="(999) 999-9999" /> <f:errors path="phone"/>
  <br><br>
  Role:
  User <input name="role" type="radio" value="2" checked>
  <br>
  Admin <input name="role" type="radio" value="1">
  <br><br>
  Enabled <input name="enabled" type="checkbox" checked>
  <br><br>
  <input type="submit"/>
  <br><br>
</f:form>
<h3>Project List</h3>
<c:if test="${!empty userList}">
  <table>
    <tr>
      <th>Id</th>
      <th>User Name</th>
      <th>Password</th>
      <th>Enabled</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Phone</th>
    </tr>
    <c:forEach items="${userList}" var="user">
      <tr>
        <td>${user.id}</td>
        <td>${user.userName}</td>
        <td>${user.encryptedPassword}</td>
        <td>${user.enabled}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.email}</td>
        <td>${user.phone}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>
</html>
