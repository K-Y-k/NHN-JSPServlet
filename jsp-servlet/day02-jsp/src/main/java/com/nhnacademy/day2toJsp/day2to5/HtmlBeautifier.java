package com.nhnacademy.day2toJsp.day2to5;

import org.jsoup.Jsoup;

import java.io.Serializable;

/**
 * beautify.html JavaBeans 방식으로 만들어보기
 * - Jsoup 라이브러리를 활용하여 html을 가공
 */
public class HtmlBeautifier implements Serializable {

    public HtmlBeautifier() {}

    private String html;

    public String getHtml() {
        return Jsoup.parse(this.html).html();
    }

    public void setHtml(String html) {
        this.html = html;
    }
}