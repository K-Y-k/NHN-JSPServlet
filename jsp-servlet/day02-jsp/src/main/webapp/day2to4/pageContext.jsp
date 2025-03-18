<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title> forward vs include </title>
  </head>

  <!--include 처리가 끝나면 다시 제어권은 원래의 페이지로 돌아온다.
      (즉 기존 페이지에 include한 url 페이지를 삽입하는것과 같음)
      forward는 요청과 응답에 대한 제어권을 URL로 지정된 주소로 영구적으로 넘기고 종료됨-->
  <body>
      <h1>THIS IS pageContext.jsp.</h1>

      <%
          String type = request.getParameter("type");

          // /day2to4/pageContext.jsp?type=include
          if ("include".equals(type)) {
              pageContext.include("sub.jsp");
          } else if ("forward".equals(type)) {
              // /day2to4/pageContext.jsp?type=forward
              pageContext.forward("sub.jsp");
          } else {
              out.println("type parameter is needed.");
          }
      %>
  </body>
</html>