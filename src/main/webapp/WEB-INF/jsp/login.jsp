<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/8/22
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<form action="/login" method="post">
    <input type="text" placeholder="用户名" name="username"><br>
    <input type="text" placeholder="密码" name="password"><br>
    <input type="submit" value="登录">
    <c:out value="${message}"/>
</form>
</body>
</html>
