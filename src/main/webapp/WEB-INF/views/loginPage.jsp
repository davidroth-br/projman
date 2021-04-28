<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<%--<%@include file="_menu.jsp" %>--%>

<h1>Login</h1>

<!-- /login?error=true -->
<c:if test="${param.error == 'true'}">
    <div style="color:red; margin:10px 0;">
        Login Failed!!!<br />
        Reason :
        <c:if test="${sessionScope != null and sessionScope['SPRING_SECURITY_LAST_EXCEPTION'] != null}">
            <span>"${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"</span>
        </c:if>
    </div>
</c:if>

<h3>Enter user name and password:</h3>
<form name='f' action="doLogin" method='POST'>
    <table>
        <tr>
            <td><label for='username'>User:</label></td>
            <td><input id='username' type='text' name='username' value='' autofocus /></td>
        </tr>
        <tr>
            <td><label for='password'>Password:</label></td>
            <td><input id='password' type='password' name='password' /></td>
        </tr>
        <tr>
            <td><label for='remember'>Remember Me?</label></td>
            <td><input id='remember' type="checkbox" name="remember-me" /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
        </tr>
    </table>
</form>

<br>
Username/pass:
<ul>
    <li>dbuser1/123</li>
    <li>dbadmin1/123</li>
</ul>

</body>

</html>