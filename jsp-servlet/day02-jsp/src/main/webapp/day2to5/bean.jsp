<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>jsp bean action tag</title>
    </head>

    <body>
        <!--scope를 request-->
        <jsp:useBean id="user1" scope="request" class="com.nhnacademy.day2toJsp.day2to5.User" />

        <!--url value 대신 param 속성 사용하기 /bean.jsp?userName=marco&userAge=38-->
        <jsp:setProperty name="user1" property="name" value="marco" />
        <jsp:setProperty name="user1" property="age" value="38" />

        <p>my name is <jsp:getProperty name="user1" property="name"/>.</p>
        <p>i am <jsp:getProperty name="user1" property="age"/> years old.</p>
        <p>toString : <%=request.getAttribute("user1")%></p>


        <!--scope를 session으로 변경해보기 -> toString : null이 나옴-->
        <%--<jsp:useBean id="user1" scope="session" class="com.nhnacademy.day2toJsp.day2to5.User" />
             <p>toString : <%=request.getAttribute("user1")%></p>--%>

        <!--scope를 application으로 변경해보기 -> toString : null이 나옴--->
        <%--<jsp:useBean id="user1" scope="application" class="com.nhnacademy.day2toJsp.day2to5.User" />
             <p>toString : <%=request.getAttribute("user1")%></p>--%>

    </body>
</html>