<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <div class="dashboard">
        <h2 class="h3 text-center fw-bold">${sessionScope.currentUser.firstName}'s Dashboard</h2>
        <h3 class="h4 text-center text-decoration-underline fw-bold">Your Tasks</h3>
        <table class="table table-sm table-striped caption-top">
            <thead>
            <tr>
                <th class="text-center align-bottom">Tasks</th>
                <th colspan="2" class="text-center align-bottom">Pending</th>
                <th colspan="2" class="text-center align-bottom">Completed</th>
            </tr>
            <tr>
                <th class="text-center">Assigned</th>
                <th class="text-center">On Time</th>
                <th class="text-center">Overdue</th>
                <th class="text-center">On Time</th>
                <th class="text-center">Late</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="text-center">${totalTasks}</td>
                <td class="text-center">${pendingOnTime}</td>
                <td class="text-center">${pendingOverdue}</td>
                <td class="text-center">${completedOnTime}</td>
                <td class="text-center">${completedLate}</td>
            </tr>
            </tbody>
        </table>
        <br>
        <c:if test="${sessionScope.currentUser.isLeader() || sessionScope.currentUser.isAdmin()}">
            <h3 class="h4 text-center text-decoration-underline fw-bold">${projectsMessage}</h3>
            <c:forEach var="project" items="${projectStats}">
                <c:if test="${project.projectName != null}">
                    <table class="table table-sm table-striped caption-top">
                        <caption class="fs-5 text-dark fw-bold text-decoration-underline">
                                ${project.projectName}</caption>
                        <thead>
                        <tr>
                            <th></th>
                            <th class="text-center align-bottom">Tasks</th>
                            <th colspan="2" class="text-center align-bottom">Pending</th>
                            <th colspan="2" class="text-center align-bottom">Completed</th>
                        </tr>
                        <tr class="left">
                            <th>Member</th>
                            <th class="text-center">Assigned</th>
                            <th class="text-center">On Time</th>
                            <th class="text-center">Overdue</th>
                            <th class="text-center">On Time</th>
                            <th class="text-center">Late</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${project.tasksInProject == 0}">
                                <td class="text-center" colspan="6">No tasks yet</td>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="member" items="${project.memberStats}">
                                    <tr>
                                        <td>${member.memberName}</td>
                                        <td class="text-center">${member.totalTasks}</td>
                                        <td class="text-center">${member.pendingTasksOnTime}</td>
                                        <td class="text-center">${member.pendingTasksOverdue}</td>
                                        <td class="text-center">${member.completedTasksOnTime}</td>
                                        <td class="text-center">${member.completedTasksLate}</td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </c:if>
            </c:forEach>
            <br>
        </c:if>
    </div>
</div>
</body>
</html>
