<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Access Denied</title>
</head>

<body>
<%@include file="_menu.jsp" %>

<h3 style="color: red;">
    Hi ${sessionScope.currentUser.fullName}.
    <br> You do not have permission to access this page!
</h3>

</body>
</html>