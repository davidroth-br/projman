<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<%@include file="_menu.jsp" %>

<h2>${title}</h2>
<h3>Welcome :
    <span>${pageContext.request.userPrincipal.name}</span>
</h3>
<b>This is protected page!</b>

<br/><br/>

<c:if test="${userInfo != null}">
    <div>${userInfo}"></div>
</c:if>
</body>
</html>