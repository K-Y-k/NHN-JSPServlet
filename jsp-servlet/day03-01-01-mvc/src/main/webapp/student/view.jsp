<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="cfmt" uri="http://nhnacademy.com/cfmt" %>

<html>
    <head>
        <title>학생-조회</title>
        <link rel="stylesheet" href="/style.css" />

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
        <h1>학생-조회</h1>
        <table>
            <tbody>
            <!-- todo view 구현 -->
            <tr>
                <td style="text-align:center; font-weight: bold">아이디 </td>
                <td>${student.id}</td>
            </tr>
            <tr>
                <td style="text-align:center; font-weight: bold">이름 </td>
                <td>${student.name}</td>
            </tr>
            <tr>
                <td style="text-align:center; font-weight: bold">성별 </td>
                <td>${student.gender}</td>
            </tr>
            <tr>
                <td style="text-align:center; font-weight: bold">나이 </td>
                <td>${student.age}</td>
            </tr>
            <tr>
                <td style="text-align:center; font-weight: bold">등록일 </td>
                <td>${cfmt:formatDate(student.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
            </tr>
            </tbody>
        </table>

        <ul>
            <li><a href="/student/list">리스트</a></li>

            <li>
                <!-- todo ${update_link} 설정 c:url -->
                <a href="/student/update?id=${student.id}">수정</a>
            </li>

            <li>
                <!-- todo 삭제버튼 구현, method=post-->
                <form method="post" action="/student/delete?id=${student.id}">
                    <button type="submit">삭제</button>
                </form>
            </li>
         </ul>
    </body>
</html>