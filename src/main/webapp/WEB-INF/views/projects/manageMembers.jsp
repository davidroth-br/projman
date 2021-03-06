<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${project.name} Members</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <c:if test="${message != null}">
        <h3 class="fs-5 text-center ${messageColor}">${message}</h3>
    </c:if>
    <h3 class="h3 text-center fw-bold">Manage Members</h3>
    <div class="content-sm">
        <h4 class="h4 text-center fw-bold mb-3">${project.name}</h4>
        <div class="container">
            <form method="post" action="${pageContext.request.contextPath}/projects/leader/addMember/${from}"
                  name="addMember">
                <div class="row mb-3">
                    <div class="col text-center">
                        <c:if test="${not empty availableUserList}">
                            <label for="availableUsers"></label>
                            <select name="availableUsers" id="availableUsers" class="me-1 align-middle">
                                <c:forEach items="${availableUserList}" var="availableUserOption">
                                    <option value="${availableUserOption.key}">${availableUserOption.value}</option>
                                </c:forEach>
                            </select>
                            <button class="btn btn-sm btn-info text-center ms-1" type="submit">Add</button>
                        </c:if>
                    </div>
                    <input type="hidden" name="projectId" value="${project.id}"/>
                </div>
            </form>
            <c:forEach items="${project.users}" var="user">
                <div class="row">
                    <div class="col">
                        <span>${user.fullName} ${user == project.leader ? '(Leader)' : ''}</span>
                        <a href="<c:url value="/projects/leader/removeMember/${from}/${user.id}/${project.id}"/>">Remove</a>
                    </div>
                </div>
            </c:forEach>
            <div class="row mt-3 mb-3">
                <div class="col text-center">
                    <a href="<c:url value="/projects/${from}/list"/>"
                       class="btn btn-sm btn-secondary text-center ms-2">Back</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
