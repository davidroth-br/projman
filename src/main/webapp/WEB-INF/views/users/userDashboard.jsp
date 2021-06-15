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
<c:if test="${projectAmount != 0}">
    You are the leader of ${projectAmount} projects:<br>
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
            </tr>
            <c:forEach var="member" items="${project.memberStats}">
                <tr>
                    <td></td>
                    <td>${member.memberName}</td>
                    <td class="center">${member.pendingTasksOnTime}</td>
                    <td class="center">${member.pendingTasksOverdue}</td>
                    <td class="center">${member.completedTasksOnTime}</td>
                    <td class="center">${member.completedTasksLate}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</c:if>
<br>
Tasks assigned to you: ${tasks}
<br><br>
&emsp;Completed: ${completed}
<br>
&emsp;&emsp;On time: ${onTime}
<br>
&emsp;&emsp;Late: ${late}
<br><br>
&emsp;Pending: ${pending}
<br>
&emsp;&emsp;overdue: ${overdue}
</body>
</html>
