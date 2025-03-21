package com.nhnacademy.jdbc.student.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Student {

    public enum GENDER{
        M,F
    }
    private String id;
    private String name;
    private GENDER gender;
    private Integer age;
    private LocalDateTime createdAt =  LocalDateTime.now();

    public Student(String id, String name, GENDER gender, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    //todo#0 필요한 method가 있다면 추가합니다.
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
