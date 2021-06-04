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

<f:form method="post" action="${pageContext.request.contextPath}${action}" modelAttribute="task">

    Task Name: <f:input path="name"/> <f:errors path="name"/>
    <br><br>
    Description: <f:textarea path="description"/> <f:errors path="description"/>
    <br><br>
    Deadline:
    <f:input path="deadline" type="date"/> <f:errors path="deadline"/>
    <br><br>
    Completion Date:
    <f:input path="completionDate" type="date"/> <f:errors path="completionDate"/>
    <br><br>
    Priority: <f:select path="priority" items="${priorityList}"/>
    <br><br>
    State: <f:select path="state" items="${stateList}"/>
    <br><br>
    Project:
    <f:select path="project">
        <f:option value="0" label="--- Select Project ---"/>
        <c:forEach items="${projectList}" var="projectItem">
            <c:choose>
                <c:when test="${projectItem.id == task.project.id}">
                    <f:option value="${projectItem.id}" label="${projectItem.name}" selected="true"/>
                </c:when>
                <c:otherwise>
                    <f:option value="${projectItem.id}" label="${projectItem.name}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </f:select> <f:errors path="project"/>
    <br><br>
    User(s) Responsible:
    <f:select path="users" multiple="true">
        <c:forEach items="${userList}" var="user">
            <c:set var="isSelected" value="false"/>
            <c:forEach items="${task.users}" var="selected">
                <c:if test="${user.id == selected.id}">
                    <c:set var="isSelected" value="true"/>
                </c:if>
            </c:forEach>
            <c:choose>
                <c:when test="${isSelected}">
                    <f:option value="${user.id}" label="${user.firstName} ${user.lastName}" selected="true"/>
                </c:when>
                <c:otherwise>
                    <f:option value="${user.id}" label="${user.firstName} ${user.lastName}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </f:select>
    <br><br>
    <f:hidden path="id" value="${task.id}"/>
    <input type="submit"/>
    <br><br>
</f:form>
</body>
</html>
