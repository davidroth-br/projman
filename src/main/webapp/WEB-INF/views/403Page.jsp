<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>
</head>

<body>
<%@include file="_menu.jsp" %>

<c:if test="${message != null}">
    <h3 style="color: red;">${message}</h3>
</c:if>

<c:if test="${userInfo != null}">
    <div>${userInfo}"></div>
</c:if>
</body>
</html>