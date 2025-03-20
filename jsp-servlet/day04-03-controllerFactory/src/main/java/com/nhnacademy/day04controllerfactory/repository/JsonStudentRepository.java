package com.nhnacademy.day04controllerfactory.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.day04controllerfactory.domain.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonStudentRepository implements StudentRepository {
    private final ObjectMapper objectMapper;

    //json file 저장 경로
    private static final String JSON_FILE_PATH="/Users/kyk49/intelliJ-ultimate-workspace/day04-03-controllerFactory/src/main/json/student.json";

    // 저장할 파일
    File file = new File(JSON_FILE_PATH);

    public JsonStudentRepository(){
        objectMapper = new ObjectMapper();

        //LocalDatetime json 직열화/역직렬화 가능하도록 설정
        objectMapper.registerModule(new JavaTimeModule());

        // todo JSON_FILE_PATH 경로에 json 파일이 존재하면 삭제 합니다.
        if (file.exists()) {
            file.delete();
        }
    }

    private synchronized List<Student> readJsonFile(){
        List<Student> studentList = new ArrayList<>();

        // todo json 파일이 존재하지 않다면 비어있는 List<Student> 리턴
        if (!file.exists()) {
            return studentList;
        }

        // json read & 역직렬화 ( json string -> Object )
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            studentList = objectMapper.readValue(bufferedReader, new TypeReference<List<Student>>() {});
            return studentList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeJsonFile(List<Student> studentList){
        File file = new File(JSON_FILE_PATH);

        // List<Student> 객체를 -> json 파일로 저장 (직렬화)
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            objectMapper.writeValue(bufferedWriter, studentList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Student student) {
        // json String -> Object 형태로 변화 (역직렬화)
        List<Student> studentList = readJsonFile();

        // List에 student 추가
        studentList.add(student);

        // List<Student>객체를 -> json String 형태로 저장(직렬화)
        writeJsonFile(studentList);
    }

    //todo 나머지 method는 직접 구현하기
    @Override
    public void update(Student student) {
        // json String -> Object 형태로 변화 (역직렬화)
        List<Student> studentList = readJsonFile();

        // student 찾기
        for (Student stu : studentList) {
            if (stu.getId().equals(student.getId())) {
                stu.setName(student.getName());
                stu.setGender(student.getGender());
                stu.setAge(student.getAge());
            }
        }

        // List<Student>객체를 -> json String 형태로 저장(직렬화)
        writeJsonFile(studentList);
    }

    @Override
    public void deleteById(String id) {
        // json String -> Object 형태로 변화 (역직렬화)
        List<Student> studentList = readJsonFile();

        // student 찾고 삭제
        for (Student stu : studentList) {
            if (stu.getId().equals(id)) {
                studentList.remove(stu);
                break;
            }
        }

        // List<Student>객체를 -> json String 형태로 저장(직렬화)
        writeJsonFile(studentList);
    }

    @Override
    public Student getStudentById(String id) {
        // json String -> Object 형태로 변화 (역직렬화)
        List<Student> studentList = readJsonFile();

        // student 찾고 반환
        Student findStudent = null;
        for (Student stu : studentList) {
            if (stu.getId().equals(id)) {
                findStudent = stu;
                break;
            }
        }
        return findStudent;
    }

    @Override
    public List<Student> getStudents() {
        return readJsonFile();
    }

    @Override
    public boolean existById(String id) {
        // json String -> Object 형태로 변화 (역직렬화)
        List<Student> studentList = readJsonFile();

        // student 찾고 반환
        Boolean existStudent = false;
        for (Student stu : studentList) {
            if (stu.getId().equals(id)) {
                existStudent = true;
                break;
            }
        }
        return existStudent;
    }
}