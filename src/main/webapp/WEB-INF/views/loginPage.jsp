<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<%--<h1>Login</h1>--%>

<c:if test="${param.error == 'true'}">
    <div style="color:red; margin:10px 0;">
        Login Failed!!!<br/>
        Reason :
        <c:if test="${sessionScope != null and sessionScope['SPRING_SECURITY_LAST_EXCEPTION'] != null}">
            <span>"${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"</span>
        </c:if>
    </div>
</c:if>

<main class="form-signin">
    <form name='f' action="doLogin" method='POST'>
        <h1 class="h1 mb-3 fw-bold">Projet Manager</h1>
        <h2 class="h3 mb-3 fw-normal">Please sign in</h2>

        <div class="form-floating">
            <input type="text" class="form-control" id="floatingInput" placeholder="Login" name='username' value='' autofocus>
            <label for="floatingInput">User Name</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name='password'>
            <label for="floatingPassword">Password</label>
        </div>

        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me" name="remember-me"> Remember me
            </label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit" name="submit" value="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2021</p>
    </form>
</main>

<%--<h3>Enter user name and password:</h3>--%>
<%--<form name='f' action="doLogin" method='POST'>--%>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <td><label for='username'>User:</label></td>--%>
<%--            <td><input id='username' type='text' name='username' value='' autofocus/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td><label for='password'>Password:</label></td>--%>
<%--            <td><input id='password' type='password' name='password'/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td><label for='remember'>Remember Me?</label></td>--%>
<%--            <td><input id='remember' type="checkbox" name="remember-me"/></td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td><input name="submit" type="submit" value="submit"/></td>--%>
<%--        </tr>--%>
<%--    </table>--%>
<%--</form>--%>
</body>
</html>