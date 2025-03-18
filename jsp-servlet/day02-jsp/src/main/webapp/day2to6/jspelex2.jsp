<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<html>
    <head>
        <title>el 기본문법 - 연산자</title>
    </head>

    <!--EL 기본문법 - 연산자-->
    <body>
        <p>${ 66 + 22 }=88</p>
        <p>${ 44 - 22 }=22</p>
        <p>${ 11 *  2 }=22</p>
        <p>${ 22 /  2 }=11</p>
        <p>${ 22 %  3 }=1</p>
        <p>${ 22 mod 3 }=1</p>
        <p>${ true && false }=false</p>
        <p>${ false and true }=false</p>
        <p>${ true || false }=true</p>
        <p>${ false or true }=true</p>
        <p>${not false}=true</p>
        <p>${!true}=false</p>
        <p>${ 123 == 123 }=true</p>
        <p>${ 123 eq 123 }=true</p>
        <p>${ 101 < 100 }=false</p>
        <p>${ 101 gt 100 }=true</p>
        <p>${ 101 != 100 }=true</p>
        <p>${ 20 > 10 ? "gt" : "lt" }=gt</p>
    </body>
</html>