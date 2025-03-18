<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>sessionCounter</title>
    </head>

    <body>
        <%
            Long counter = 0l;

            if (Objects.nonNull(session.getAttribute("counter"))) {
                counter = (Long) session.getAttribute("counter");
            }

            session.setAttribute("counter", ++counter);
        %>
        <h1>
            counter:<%=counter%>
        </h1>
    </body>
</html>