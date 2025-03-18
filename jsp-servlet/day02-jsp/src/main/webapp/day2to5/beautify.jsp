<%@ page contentType="text/plan;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<jsp:useBean id="htmlBeautifier" scope="request" class="com.nhnacademy.day2toJsp.day2to5.HtmlBeautifier" />

<%--HtmlBeautifier 클래스의 setProperty 메소드호출--%>
<%--HtmlBeautifier 클래스의 property명과 받아온 매개변수 param명이 서로 일치하면 param 생략가능--%>
<jsp:setProperty name="htmlBeautifier" property="html" param="html" />

<%--HtmlBeautifier 클래스의 getProperty 메소드호출--%>
<jsp:getProperty name="htmlBeautifier" property="html" />