<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${project.name} Members</title>
</head>
<body>
<%@include file="../_menu.jsp" %>
<h2>${project.name} Members</h2>
<c:if test="${message != null}">
    <h3 style="color: red">${message}</h3>
</c:if>
<table>
    <form method="post" action="${pageContext.request.contextPath}/projects/leader/addMember" name="addMember">
        <tr>
            <th>
                <label for="availableUsers">Add User to Project:
                    <select name="availableUsers" id="availableUsers">
                        <c:forEach items="${availableUserList}" var="availableUserOption">
                            <option value="${availableUserOption.key}">${availableUserOption.value}</option>
                        </c:forEach>
                    </select>
                </label>
            </th>
            <th>
                <input name="submit" type="submit" value="Add"/>
                <input type="hidden" name="projectId" value="${project.id}"/>
            </th>
        </tr>
    </form>
    <tr>
        <td></td>
    </tr>
    <c:forEach items="${project.users}" var="user">
        <tr>
            <td>${user.fullName}
                <c:if test="${user == project.leader}">
                    (Leader)
                </c:if>
            </td>

            <td><a href="<c:url value="/projects/leader/removeMember/${user.id}/${project.id}"/>">Remove</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
