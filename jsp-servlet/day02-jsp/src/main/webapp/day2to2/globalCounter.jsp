<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
      <title>globalCounter</title>
    </head>

    <body>
        <%!
            private long counter = 0;

            private long increaseCounter(){
                return ++counter;
            }
        %>

        <h1>counter:<%=increaseCounter()%></h1>
    </body>
</html>