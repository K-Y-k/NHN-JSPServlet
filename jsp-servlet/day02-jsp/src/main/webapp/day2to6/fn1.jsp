<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <title>function tag library</title>
    </head>

    <body>
        <p><c:if test="${fn:startsWith('nhnacademy', 'nhn')}">true</c:if></p>
        <p><c:if test="${fn:endsWith('nhnacademy', 'my')}">false</c:if></p>
        <p><c:if test="${fn:contains('nhnacademy', 'aca')}">true</c:if></p>
        <p><c:out value="${fn:trim('   nhnacademy ')}" />==nhnacademy</p>
        <p><c:out value="${'nhnacademy'.toUpperCase()}" />==NHNACADEMY</p>
    </body>
</html>
