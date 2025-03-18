
<%--page 내장 객체--%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %>

<%--trimDirectiveWhitespaces
    - true이면 앞뒤에 있는 불필요한 공백이 제거
    - alse이면 앞뒤에 있는 공백이 그대로 유지됨--%>
<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>

<%!
    private List<String> getClasses(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return Collections.emptyList();
        }

        List<String> classes = new ArrayList<>();
        while (clazz != null) {
            classes.add(clazz.getName());
            clazz = clazz.getSuperclass();
        }

        return classes;
    }
%>

<%
    response.setContentType("text/plain");                                  // response

    out.println("Hello, " + request.getParameter("name"));                  // request
    out.println("servlet name = " + config.getServletName());               // config
    out.println("context path = " + application.getContextPath());          // application

    out.println("this == page? " + (this == page));

    List<String> classes = getClasses(getClass());                          // page
    out.println("page classes = " + String.join(" > ", classes));

    classes = getClasses(pageContext.getClass());                           // pageContext
    out.println("pageContext classes = " + String.join(" > ", classes));
%>