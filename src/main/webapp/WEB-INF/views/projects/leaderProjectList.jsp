<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${sessionScope.currentUser.fullName}'s Projects</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous">
    </script>
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <c:if test="${message != null}">
        <h3 class="fs-5 text-center ${messageColor}">${message}</h3>
    </c:if>
    <h2 class="h3 text-center fw-bold">Projects You Lead</h2>
    <c:set var="projectName" value=""/>
    <table class="table table-sm table-borderless">
        <c:forEach items="${projectList}" var="project">
            <c:if test="${projectName != project.name}">
                <c:if test="${projectName != ''}">
                    <tr>
                        <td><br></td>
                    </tr>
                </c:if>
                <tr class="fs-5">
                    <th colspan="3" class="fw-bold text-decoration-underline">${project.name}</th>
                    <th colspan="4" class="fw-normal text-nowrap" style="text-align: right">
                        <a href="<c:url value="/tasks/leader/new/${project.id}"/>">Add Task</a><span> | </span>
                        <a href="<c:url value="/projects/leader/manageMembers/leader/${project.id}"/>">Manage
                            Members</a>
                    </th>
                </tr>
                <c:set var="projectName" value="${project.name}"/>
                <tr class="text-nowrap">
                    <th>Task</th>
                    <th>Allocated To</th>
                    <th class="text-center">Priority</th>
                    <th>State</th>
                    <th></th>
                </tr>
            </c:if>
            <c:if test="${empty project.tasks}">
                <tr>
                    <td colspan="7" class="text-center">No tasks yet</td>
                </tr>
            </c:if>
            <c:forEach items="${project.tasks}" var="task">
                <form method="post" action="${pageContext.request.contextPath}${action}/${task.id}" name="changeState">
                    <tr>
                        <td>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#taskModal"
                               data-bs-taskName="${task.name}"
                               data-bs-taskDescription="${task.description}"
                               data-bs-taskDeadline="${task.deadline}"
                               data-bs-taskCompletionDate="${not empty task.completionDate ? task.completionDate : 'Task has not been completed'}">${task.name}</a>
                        </td>
                        <td class="text-nowrap">
                            <c:forEach items="${task.users}" var="user">
                                <a href="#" data-bs-toggle="modal" data-bs-target="#userModal"
                                   data-bs-userFullName="${user.fullName}"
                                   data-bs-userEmail="${user.email}"
                                   data-bs-userPhone="${user.phone}">${user.fullName}</a>
                                <br>
                            </c:forEach>
                        </td>
                        <td class="text-center">${priorityList[task.priority]}</td>
                        <td>
                            <select name="state">
                                <c:forEach items="${stateList}" var="stateOption">
                                    <c:set var="selected" value=""/>
                                    <c:if test="${stateOption.key == task.state}">
                                        <c:set var="selected" value="selected"/>
                                    </c:if>
                                    <option value="${stateOption.key}" ${selected}>${stateOption.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <button type="submit" value="update" class="btn-sm btn-info">Update</button>
                        </td>
                        <input type="hidden" name="id" value="${task.id}"/>
                        <td class="text-right text-nowrap">
                            <a href="<c:url value="/tasks/leader/edit/${task.id}"/>">Edit</a><span> | </span>
                            <c:set var="deleteId" value="${task.id}"/>
                            <c:set var="deleteMessage" value="task"/>
                            <c:set var="deleteName" value="${task.name}"/>
                            <c:set var="deleteUri" value="/tasks/leader/remove/"/>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal_${deleteId}">Delete</a>
                        </td>
                </form>
                <%@include file="../modals/confirmDelete.html" %>
            </c:forEach>
        </c:forEach>
    </table>
</div>
<%@include file="../modals/taskDetails.html" %>
<script src="${pageContext.request.contextPath}/js/taskDetails.js"></script>
<%@include file="../modals/userDetails.html" %>
<script src="${pageContext.request.contextPath}/js/userDetails.js"></script>
</body>
</html>
