<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${addOrEdit} Project</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <h3 class="h3 text-center fw-bold">${addOrEdit} Project</h3>
    <f:form method="post" action="${pageContext.request.contextPath}${action}" modelAttribute="project">
    <div class="container">
        <div class="row mb-3">
            <div class="col">
                <f:label path="name" cssClass="label" cssErrorClass="text-danger label">Project Name:</f:label>
                <f:errors cssClass="text-danger label" path="name"/><br>
                <f:input path="name" size="35"/>
            </div>
            <div class="col">
                <f:label path="leader" cssClass="label" cssErrorClass="text-danger label">Leader:</f:label><br>
                <f:select path="leader">
                    <f:option value="0" label="--- Select Leader ---"/>
                    <c:forEach items="${userList}" var="user">
                        <c:choose>
                            <c:when test="${user.id == project.leader.id}">
                                <f:option value="${user.id}" label="${user.fullName}" selected="true"/>
                            </c:when>
                            <c:otherwise>
                                <f:option value="${user.id}" label="${user.fullName}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </f:select>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="description" cssClass="label" cssErrorClass="text-danger label">Description:</f:label>
                <f:errors cssClass="text-danger label" path="description"/><br>
                <f:textarea path="description" rows="5" cols="80"/>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="startDate" cssClass="label ${empty startDateMessage ? '' : 'text-danger'}"
                         cssErrorClass="text-danger label">Start Date:</f:label>
                <f:errors cssClass="text-danger label" path="startDate"/>
                <span class="text-danger label">${startDateMessage}</span><br>
                <f:input path="startDate" type="date"/>
            </div>
            <div class="col">
                <f:label path="endDate" cssClass="label ${empty endDateMessage ? '' : 'text-danger'}"
                         cssErrorClass="text-danger label">End Date:</f:label>
                <f:errors cssClass="text-danger label" path="endDate"/>
                <span class="text-danger label">${endDateMessage}</span><br>
                <f:input path="endDate" type="date"/>
            </div>
        </div>
        <f:hidden path="id" value="${project.id}"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button class="btn btn-sm btn-info text-center me-2" type="submit">Save</button>
                <a href="<c:url value="/projects/admin/list"/>"
                   class="btn btn-sm btn-secondary text-center ms-2">Cancel</a>
            </div>
        </div>
        </f:form>
    </div>
</body>
</html>
