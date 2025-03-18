<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"  %>

<%--EL 기본문법 -배열, 리스트, 맵--%>
<%
    pageContext.setAttribute("array", new String[] { "this", "is", "how", "we", "do", "it" });

    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(3);
    list.add(5);
    pageContext.setAttribute("list", list);

    Map<String, String> map1 = new HashMap<>();
    map1.put("Mariah Carey", "Without you");
    map1.put("Whitney Houston", "I will always love you");
    map1.put("Celine Dion", "My heart will go on");
    pageContext.setAttribute("map1", map1);

    Map<String, Integer> map2 = new HashMap<>();
    map2.put("j", 1);
    map2.put("s", 2);
    map2.put("p", 7);
    pageContext.setAttribute("map2", map2);
%>

<html>
    <head>
        <title>el - array, list, map test</title>
    </head>

    <!--EL 기본문법 -배열, 리스트, 맵-->
    <body>
        <p>array=${array[2]}</p>
        <p>list=${list[1]}</p>
        <p>map1=${map1['Whitney Houston']}</p>
        <p>map2=${map2.p}</p>
    </body>
</html>