<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>숫자 포맷팅</title>
    </head>

    <body>
        <h1>locale : en_US</h1>
        <!--미국 단위 표현 설정-->
        <fmt:setLocale value="en_US" />
        <c:set var="price" value="12000" />
        <p><fmt:formatNumber value="${price}" type="currency" /></p>

        <!--한국 단위 표현 설정-->
        <h1>locale : ko_KR</h1>
        <fmt:setLocale value="ko_KR" />
        <p><fmt:formatNumber value="${price}" type="currency" /></p>

        <h2>123000000 : 3자리 , 표현</h2>
        <p><fmt:formatNumber value="123000000" type="number" maxFractionDigits="3" /></p>
    </body>
</html>