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
<%--  Project Id: <f:input path="id"/> <f:errors path="id"/>--%>
<%--  <br><br>--%>
  User Name: <f:input path="userName"/> <f:errors path="userName"/>
  <br><br>
  Password: <f:input path="encryptedPassword"/> <f:errors path="encryptedPassword"/>
  <br><br>
  First Name: <f:input path="firstName"/> <f:errors path="firstName"/>
  <br><br>
  Last Name: <f:input path="lastName"/> <f:errors path="lastName"/>
  <br><br>
  Email: <f:input path="email"/> <f:errors path="email"/>
  <br><br>
  Phone: <f:input path="phone"/> <f:errors path="phone"/>
  <br><br>
<%--  Project: <f:input path="projects"/> <f:errors path="projects"/>--%>
<%--  <br><br>--%>
<%--  Task: <f:input path="tasks"/> <f:errors path="tasks"/>--%>
<%--  <br><br>--%>
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
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Phone</th>
    </tr>
    <c:forEach items="${userList}" var="user">
      <c:set var="id" value="${user[0]}"/>
      <c:set var="name" value="${user[1]}"/>
      <c:set var="password" value="${user[2]}"/>
      <c:set var="firstName" value="${user[3]}"/>
      <c:set var="lastName" value="${user[4]}"/>
      <c:set var="email" value="${user[5]}"/>
      <c:set var="phone" value="${user[6]}"/>
      <tr>
        <td>${id}</td>
        <td>${name}</td>
        <td>${password}</td>
        <td>${firstName}</td>
        <td>${lastName}</td>
        <td>${email}</td>
        <td>${phone}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>
</html>
