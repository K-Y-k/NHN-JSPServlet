<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Title</title>
    </head>

    <body>
        <c:set var="email" value="marco@nhnacademy.com" scope="request" />
        <c:set var="name">marco</c:set>

        <c:out value="1" /><br/>
        <c:out value="${email}" /><br/>

        <c:out value="${pageScope.email}" /><br/>
        <c:out value="${requestScope.email}" /><br/>
        <c:out value="${name}" /><br/>

        <c:remove var="name" />
        removedName:<c:out value="${name}" /><br/>
    </body>
</html>
