<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>c:url, c:param - c:redirect </title>
    </head>

    <body>
        <c:url var="redirectUrl" value="/day2to6/hello.jsp">
            <c:param name="page" value="1" />
            <c:param name="size" value="15"/>
        </c:url>

        redirect url is ${redirectUrl}

        <c:redirect url="${redirectUrl}" />
    </body>
</html>
