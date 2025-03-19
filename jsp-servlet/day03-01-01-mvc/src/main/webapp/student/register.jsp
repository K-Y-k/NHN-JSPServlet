<%--
  Created by IntelliJ IDEA.
  User: kyk49
  Date: 25. 3. 17.
  Time: 오전 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<html>
    <head>
        <title>Title</title>

        <style>
            table {
                width: 800px;
                border-collapse: collapse;
                border:1px #ccc solid;
            }

            table tr,td,th{
                padding: 5px;
                border: 1px #ccc solid;
            }
        </style>
    </head>

    <body>
        <%
            String action = "";
            // 등록/수정 여부에 따라 경로를 다르게 설정
            String state = (String) request.getAttribute("state");

            // 기존 id 값 설정
            String id = request.getParameter("id");
            request.setAttribute("id", id);

            if (state.equals("register")) {
                action = "/student/register"; // 등록
            } else {
                action = "/student/update";   // 수정
            }

            request.setAttribute("action", action);
        %>

        <c:choose>
            <c:when test="${state == 'register'}">
                <h1>학생-등록</h1>
            </c:when>
            <c:otherwise>
                <h1>학생-수정</h1>
            </c:otherwise>
        </c:choose>

        <form method="post" action="${action}">
            <table>
                <tr>
                    <td style="text-align: center; font-weight:bold;">ID</td>
                    <td>
                        <input type="text" name="updateId" value="${student.id}" required /> <br/>
                    </td>

                    <!--수정일 때 기존 id 값을 전송하기 위한 태그-->
                    <% if (state.equals("update")) { %>
                        <input type="hidden" name="id" value="<%= id %>" />
                    <% } %>
                </tr>

                <tr>
                    <td style="text-align: center; font-weight:bold;">이름</td>
                    <td>
                        <input type="text" name="updateName" value="${student.name}" required /> <br/>
                    </td>
                </tr>

                <tr>
                    <td style="text-align: center; font-weight:bold;">성별</td>
                    <td>
                        <input type="radio" name="updateGender" value="M" ${student.gender eq 'M' ? 'checked' : '' } />남
                        <input type="radio" name="updateGender" value="F" ${student.gender eq 'F' ? 'checked' : '' } />여
                    </td>
                </tr>

                <tr>
                    <td style="text-align: center; font-weight:bold;">나이</td>
                    <td>
                        <input type="text" name="updateAge" value="${student.age}" required /> <br/>
                    </td>
                </tr>
            </table>

            <button type="submit">
                <c:choose>
                    <c:when test="${empty student}">
                        등록
                    </c:when>
                    <c:otherwise>
                        수정
                    </c:otherwise>
                </c:choose>
            </button>
        </form>
    </body>
</html>
