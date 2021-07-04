<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${addOrEdit} Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <script>
        sessionStorage.setItem("previousPageURL", sessionStorage.getItem("currentPageURL"));
        sessionStorage.setItem("currentPageURL", "${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/tasks/user/list");
        history.pushState({page: 1}, "", "");
        onbeforeunload = function(event) {
            if(event){
                location.href = sessionStorage.getItem("previousPageURL");
            }
        }
    </script>
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <h3 class="h3 text-center fw-bold">${addOrEdit} Task</h3>
    <f:form method="post" action="${pageContext.request.contextPath}${action}" modelAttribute="task">
    <div class="container">
        <div class="row mb-3">
            <div class="col">
                <f:label path="name" cssClass="label" cssErrorClass="text-danger label">Task Name:</f:label>
                <f:errors cssClass="text-danger label" path="name"/><br>
                <f:input path="name" size="40"/>
            </div>
            <div class="col">
                <f:label path="priority" cssClass="label">Priority:</f:label><br>
                <f:select path="priority" items="${priorityList}"/>
            </div>
            <div class="col">
                <f:label path="state" cssClass="label">State:</f:label><br>
                <f:select path="state" items="${stateList}"/>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="description" cssClass="label" cssErrorClass="text-danger label">Description:</f:label>
                <f:errors cssClass="text-danger label" path="description"/><br>
                <f:textarea path="description" rows="6" cols="70"/>
            </div>
            <div class="col">
                <f:label path="users" cssClass="label" cssErrorClass="text-danger label">Assigned to:</f:label><br>
                <f:select path="users" multiple="true">
                    <c:forEach items="${task.project.users}" var="user">
                        <c:set var="isSelected" value="false"/>
                        <c:forEach items="${task.users}" var="selected">
                            <c:if test="${user.id == selected.id}">
                                <c:set var="isSelected" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${isSelected}">
                                <f:option value="${user.id}" label="${user.firstName} ${user.lastName}"
                                          selected="true"/>
                            </c:when>
                            <c:otherwise>
                                <f:option value="${user.id}" label="${user.firstName} ${user.lastName}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </f:select>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <f:label path="deadline" cssClass="label" cssErrorClass="text-danger label">Deadline:</f:label>
                <f:errors cssClass="text-danger label" path="deadline"/>
                <span class="text-danger label"> ${deadlineMessage}</span><br>
                <f:input path="deadline" type="date"/>
            </div>
            <div class="col">
                <f:label path="deadline" cssClass="label" cssErrorClass="text-danger label">Completion Date:</f:label>
                <f:errors cssClass="text-danger label" path="completionDate"/><br>
                <f:input path="completionDate" type="date"/>
            </div>
        </div>
        <f:hidden path="id" value="${task.id}"/>
        <f:hidden path="project" value="${task.project.id}"/>
        <div class="row mb-3">
            <div class="col text-center">
                <button class="btn btn-sm btn-info text-center me-2" type="submit">Save</button>
                <a href="<c:url value="/projects/leader/list"/>"
                   class="btn btn-sm btn-secondary text-center ms-2">Cancel</a>
            </div>
        </div>
        </f:form>
    </div>
</body>
</html>
