<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <h3 class="h3 text-center fw-bold">Projects You Lead</h3>
    <c:set var="projectName" value=""/>
    <table class="table table-sm table-borderless">
        <c:forEach items="${projectList}" var="project">
            <c:if test="${projectName != project.name}">
                <c:if test="${projectName != ''}">
                    <tr>
                        <td><br></td>
                    </tr>
                </c:if>
                <tr>
                    <th colspan="3" class="fs-5 fw-bold">
                        <c:set var="members" value=""/>
                        <c:forEach var="member" items="${project.users}">
                            <c:set var="memberName" value="${member.fullName} / "/>
                            <c:set var="members" value="${members}${memberName}"/>
                        </c:forEach>
                        <a href="#" data-bs-toggle="modal" data-bs-target="#projectModal"
                           data-bs-projectName="${project.name}"
                           data-bs-projectMembers="${fn:substring(members, 0, fn:length(members) - 3)}"
                           data-bs-projectDescription="${project.description}"
                           data-bs-projectStartDate="<fmt:formatDate value="${project.startDate}" type="date"/>"
                           data-bs-projectEndDate="<fmt:formatDate value="${project.endDate}" type="date"/>">${project.name}</a>
                    </th>
                    <th colspan="4" class="fs-5 fw-bold text-nowrap text-right">
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
                        <td class="align-middle">
                            <fmt:formatDate value="${task.completionDate}" type="date" var="formattedCompletion"/>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#taskModal"
                               data-bs-taskName="${task.name}"
                               data-bs-taskDescription="${task.description}"
                               data-bs-taskDeadline="<fmt:formatDate value="${task.deadline}" type="date"/>"
                               data-bs-taskCompletionDate="${task.completionDate != null ? formattedCompletion : "Task has not been completed"}">${task.name}</a>
                        </td>
                        <td class="align-middle text-nowrap">
                            <c:forEach items="${task.users}" var="user">
                                <a href="#" data-bs-toggle="modal" data-bs-target="#userModal"
                                   data-bs-userFullName="${user.fullName}"
                                   data-bs-userEmail="${user.email}"
                                   data-bs-userPhone="${user.phone}">${user.fullName}</a>
                                <br>
                            </c:forEach>
                        </td>
                        <td class="text-center align-middle">${priorityList[task.priority]}</td>
                        <td class="align-middle">
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
                        <td class="align-middle">
                            <button type="submit" value="update" class="btn btn-sm btn-info text-center">Update</button>
                        </td>
                        <input type="hidden" name="id" value="${task.id}"/>
                        <td class="text-right align-middle text-nowrap">
                            <a href="<c:url value="/tasks/leader/edit/${task.id}"/>">Edit</a><span> | </span>
                            <c:set var="deleteId" value="${task.id}"/>
                            <c:set var="deleteMessage" value="task"/>
                            <c:set var="deleteName" value="${task.name}"/>
                            <c:set var="deleteUri" value="/tasks/leader/remove/"/>
                            <a href="#" data-bs-toggle="modal"
                               data-bs-target="#confirmDeleteModal_${deleteId}">Delete</a>
                        </td>
                    </tr>
                </form>
                <%@include file="../modals/confirmDelete.html" %>
            </c:forEach>
        </c:forEach>
    </table>
</div>
<%@include file="../modals/projectDetails.html" %>
<script src="${pageContext.request.contextPath}/js/projectDetails.js"></script>
<%@include file="../modals/taskDetails.html" %>
<script src="${pageContext.request.contextPath}/js/taskDetails.js"></script>
<%@include file="../modals/userDetails.html" %>
<script src="${pageContext.request.contextPath}/js/userDetails.js"></script>
</body>
</html>
