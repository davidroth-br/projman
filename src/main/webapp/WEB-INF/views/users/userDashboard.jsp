<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
    <style>
        .bottom {
            vertical-align: bottom;
        }

        .center {
            text-align: center;
        }

        .underline {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<%@include file="../_menu.jsp" %>
<h2>${user.fullName}'s Dashboard</h2>
<c:set var="today" value="<%= new java.util.Date()%>"/>
<c:if test="${isLeader}">
    <h3>Stats of the ${projectAmount} projects you lead:</h3>
    <table>
        <c:forEach var="project" items="${projectStats}">
            <tr style="text-align:left">
                <th><br>${project.projectName}</th>
                <th></th>
                <th colspan="2" class="center bottom">Pending Tasks</th>
                <th colspan="2" class="center bottom">Completed Tasks</th>
            </tr>
            <tr style="text-align:left">
                <th></th>
                <th class="underline">Member</th>
                <th class="center underline">On Time</th>
                <th class="center underline">Overdue</th>
                <th class="center underline">On Time</th>
                <th class="center underline">Late</th>
                <th class="underline">Total Assigned</th>
            </tr>
            <c:forEach var="member" items="${project.memberStats}">
                <tr>
                    <td></td>
                    <td>${member.memberName}</td>
                    <td class="center">${member.pendingTasksOnTime}</td>
                    <td class="center">${member.pendingTasksOverdue}</td>
                    <td class="center">${member.completedTasksOnTime}</td>
                    <td class="center">${member.completedTasksLate}</td>
                    <td class="center">${member.totalTasks}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
    <br>
</c:if>
<h3>Your tasks stats:</h3>
<table>
    <tr>
        <th colspan="2" class="center">Pending</th>
        <th colspan="2" class="center">Completed</th>
    </tr>
    <tr>
        <th class="center underline">On Time</th>
        <th class="center underline">Overdue</th>
        <th class="center underline">On Time</th>
        <th class="center underline">Late</th>
        <th class="underline">Total Assigned</th>
    </tr>
    <tr>
        <td class="center">${pendingOnTime}</td>
        <td class="center">${pendingOverdue}</td>
        <td class="center">${completedOnTime}</td>
        <td class="center">${completedLate}</td>
        <td class="center">${totalTasks}</td>
    </tr>
</table>
</body>
</html>
