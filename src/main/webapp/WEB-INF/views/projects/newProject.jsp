<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    Start Date:
    <f:input path="startDate" type="date"/> <f:errors path="startDate"/>
    <br><br>
    End Date:
    <f:input path="endDate" type="date"/> <f:errors path="endDate"/>
    <c:if test="${isEndDateBeforeStartDate}">End date must be after start date.</c:if>
    <br><br>
    Leader:
    <f:select path="leader">
        <f:option value="0" label="--- Select Leader ---"/>
        <c:forEach items="${userList}" var="user">
            <c:choose>
                <c:when test="${user.id == leaderId}">
                    <f:option value="${user.id}" label="${user.firstName} ${user.lastName}" selected="true"/>
                </c:when>
                <c:otherwise>
                    <f:option value="${user.id}" label="${user.firstName} ${user.lastName}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </f:select>
    <br><br>
    <input type="submit"/>
    <br><br>
</f:form>
</body>
</html>
