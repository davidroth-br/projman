<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Projects</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="menu text-center">
    <%@include file="../_menu.jsp" %>
</div>
<div class="content">
    <c:if test="${message != null}">
        <h3 class="fs-5 text-center" style="${messageColor}">${message}</h3>
    </c:if>
    <h3 class="h3 text-center fw-bold">Project List</h3>
    <h5 class="h5 text-center"><a href="<c:url value="/projects/admin/new"/>">Add Project</a></h5>
    <c:if test="${!empty projectList}">
        <table class="table table-sm table-borderless">
            <tr class="text-nowrap">
                <th>Project</th>
                <th class="text-center">Start Date</th>
                <th class="text-center">End Date</th>
                <th>Leader</th>
            </tr>
            <c:forEach items="${projectList}" var="project">
                <tr>
                    <td>
                        <c:set var="members" value=""/>
                        <c:forEach var="member" items="${project.users}">
                            <c:set var="memberName" value="${member.fullName} / "/>
                            <c:set var="members" value="${members}${memberName}"/>
                        </c:forEach>
                        <a href="#" data-bs-toggle="modal" data-bs-target="#projectModal"
                           data-bs-projectName="${project.name}"
                           data-bs-projectMembers="${fn:substring(members, 0, fn:length(members) - 3)}"
                           data-bs-projectDescription="${project.description}">${project.name}</a>
                    </td>
                    <td class="text-center"><fmt:formatDate value="${project.startDate}" type="date"/></td>
                    <td class="text-center"><fmt:formatDate value="${project.endDate}" type="date"/></td>
                    <td>${project.leader.fullName}</td>
                    <td><a href="<c:url value="/projects/leader/manageMembers/admin/${project.id}"/>">Manage Members</a></td>
                    <td><a href="<c:url value="/projects/admin/edit/${project.id}"/>">Edit</a></td>
                    <td><a href="<c:url value="/projects/admin/remove/${project.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../modals/projectDetails.html" %>
<script src="${pageContext.request.contextPath}/js/projectDetails.js"></script>
</body>
</html>
