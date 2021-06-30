<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tasks</title>
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
<h2 class="h3 text-center fw-bold">Your Tasks</h2>
<c:choose>
    <c:when test="${!empty taskList}">
        <c:set var="projectName" value=""/>
        <table class="table table-sm caption-top table-borderless">
            <c:forEach items="${taskList}" var="task">
                <c:if test="${projectName != task.project.name}">
                    <tr class="fs-5 fw-bold text-decoration-underline">
                        <th colspan="6">
                            <c:if test="${projectName != ''}"><br></c:if>
                                ${task.project.name}
                        </th>
                    </tr>
                    <tr class="fs-5">
                        <th colspan="5" class="fw-normal">
                            Leader: ${task.project.leader.fullName}
                        </th>
                        <th class="fw-normal text-nowrap" style="text-align: right">
                            <a href="<c:url value="/projects/user/members/${task.project.id}"/>">Show members</a>
                        </th>
                    </tr>
                    <c:set var="projectName" value="${task.project.name}"/>
                    <tr style="text-align:left">
                        <th>Task</th>
                        <th class="text-center">Deadline</th>
                        <th class="text-center">Priority</th>
                        <th>State</th>
                        <th></th>
                        <th class="text-center">Completion Date</th>
                    </tr>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}${action}/${task.id}" name="changeState">
                    <tr class="align-top">
                        <td><a href="<c:url value="/tasks/details/${task.id}/user"/>">${task.name}</a></td>
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
                        <td class="align-top"><input name="submit" type="submit" value="update" class="btn-sm btn-info"/></td>
                        <td class="text-center"><fmt:formatDate value="${task.completionDate}" type="date"/></td>
                        <input type="hidden" name="id" value="${task.id}"/>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h3 class="fs-5 fw-normal text-center">You don't have any tasks assigned to you</h3>
    </c:otherwise>
</c:choose>
</div>
</body>
</html>
