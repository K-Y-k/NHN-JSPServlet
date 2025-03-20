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
        <!-- todo /register -> /register.do 변경  -->
        <!-- todo /update -> /update.do 변경  -->
        <c:choose>
            <c:when test="${empty student}">
                <c:set var="action" value="/student/register.do" />
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/student/update.do" />
            </c:otherwise>
        </c:choose>

        <form method="post" action="${action}">
            <table>
                <tbody>
                <tr>
                    <th>ID</th>
                    <td><input type="text" name="updateId" value="${student.id}" required /></td>

                    <!--수정일 때 기존 id 값을 전송하기 위한 태그-->
                    <input type="hidden" name="id" value="${student.id}" />
                </tr>
                <tr>
                    <th>이름</th>
                    <td><input type="text" name="updateName" value="${student.name}" required /></td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>
                        <input type="radio" name="updateGender" value="M" ${student.gender eq 'M' ? 'checked' : '' } />남
                        <input type="radio" name="updateGender" value="F" ${student.gender eq 'F' ? 'checked' : '' } />여
                    </td>
                </tr>
                <tr>
                    <th>나이</th>
                    <td><input type="number" name="updateAge" value="${student.age}" required /></td>
                </tr>
                </tbody>
            </table>
            <p>
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
            </p>
        </form>
    </body>
</html>
