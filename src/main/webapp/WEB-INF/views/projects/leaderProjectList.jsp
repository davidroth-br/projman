<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${sessionScope.currentUser.fullName}'s Projects</title>
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
        <h3 class="fs-5 text-center" style="${messageColor}">${message}</h3>
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
                    <th colspan="4" class="fw-bold text-decoration-underline">${project.name}</th>
                    <th colspan="3" class="fw-normal text-nowrap" style="text-align: right">
                        <a href="<c:url value="/tasks/leader/new/${project.id}"/>">Add Task</a><span> | </span>
                        <a href="<c:url value="/projects/leader/manageMembers/${project.id}"/>">Manage Members</a>
                    </th>
                </tr>
                <c:set var="projectName" value="${project.name}"/>
                <tr class="text-nowrap">
                    <th>Task</th>
                    <th>Allocated To</th>
                    <th class="text-center">Deadline</th>
                    <th class="text-center">Priority</th>
                    <th>State</th>
                    <th></th>
                    <th class="text-center text-nowrap">Completed On</th>
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
                        <td><a href="<c:url value="/tasks/details/${task.id}/leader"/>">${task.name}</a></td>
                        <td>
                            <c:forEach items="${task.users}" var="user">
                                <a href="<c:url value="/users/projects/details/${user.id}"/>">${user.firstName} ${user.lastName}</a>
                                <br>
                            </c:forEach>
                        </td>
                        <td class="text-center text-nowrap"><fmt:formatDate value="${task.deadline}" type="date"/></td>
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
                        <td><input name="submit" type="submit" value="update" class="btn-sm btn-info"/></td>
                        <td class="text-center"><fmt:formatDate value="${task.completionDate}" type="date"/></td>

                        <input type="hidden" name="id" value="${task.id}"/>
                    </tr>
                    <tr>
                        <td colspan="6"></td>
                        <td style="text-align: right">
                            <a href="<c:url value="/tasks/leader/edit/${task.id}"/>">Edit</a><span> | </span>
                            <a href="<c:url value="/tasks/leader/remove/${task.id}"/>">Delete</a>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</body>
</html>
