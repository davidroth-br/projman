<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Project</title>
</head>
<body>
<%@include file="../_menu.jsp" %>

<f:form method="post" action="validateNew" modelAttribute="project">

    Project Name: <f:input path="name"/> <f:errors path="name"/>
    <br><br>
    Description: <f:textarea path="description"/> <f:errors path="description"/>
    <br><br>
    Start Date: <f:input path="startDate"/> <f:errors path="startDate"/>
    <br><br>
    End Date: <f:input path="endDate"/> <f:errors path="endDate"/>
    <c:if test="${isEndDateBeforeStartDate}">End date cannot be before start date.</c:if>
    <br><br>

    Leader:
    <select name="leaderId">
        <option value="0">--- Select Leader ---</option>
        <c:forEach items="${userList}" var="user">
            <option value="${user.id}">${user.firstName} ${user.lastName}</option>
        </c:forEach>
    </select>

    <br><br>
    <input type="submit"/>
    <br><br>
</f:form>
</body>
</html>
