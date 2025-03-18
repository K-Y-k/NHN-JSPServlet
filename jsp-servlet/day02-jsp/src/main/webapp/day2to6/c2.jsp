<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>jstl - 조건문/반복문</title>
    </head>

    <body>
        <!--/day2to6/c2.jsp?email=@nhnacademy.com 일 경우-->
        <c:if test="${ param.email.contains('@nhnacademy.com') }" var="testResult">
            <p><c:out value="${param.email}" /> is one of nhnacademy email addresses.</p>
        </c:if>

        <p>test result is <c:out value="${testResult}" /></p>


        <c:set var="role" value="admin" />
        <c:choose>
            <c:when test="${role == 'member'}">
                <p>멤버입니다.</p>
            </c:when>
            <c:when test="${role =='guest'}">
                <p>손님은 접근이 제한됩니다.</p>
            </c:when>
            <c:when test="${role == 'admin'}">
                <p>관리자 권한으로 실행됩니다.</p>
            </c:when>
            <c:otherwise>
               <p>로그인하세요.</p>
            </c:otherwise>
        </c:choose>

        <c:set var="browsers" value="${['Chrome' , 'Safari', 'Firefox']}" />
        <table>
            <tr>
                <th>Browser</th>
            </tr>

            <c:forEach var="br" items="${browsers}">
                <tr>
                    <td>${br}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
