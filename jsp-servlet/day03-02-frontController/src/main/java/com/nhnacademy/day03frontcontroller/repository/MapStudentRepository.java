package com.nhnacademy.day03frontcontroller.repository;


import com.nhnacademy.day03frontcontroller.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapStudentRepository implements StudentRepository{
    private Map<String, Student> studentsMap = new ConcurrentHashMap<>();

    @Override
    public void save(Student student) {
        studentsMap.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        studentsMap.replace(student.getId(), student);
    }

    @Override
    public void deleteById(String id) {
        studentsMap.remove(id);
    }

    @Override
    public Student getStudentById(String id) {
        return studentsMap.get(id);
    }

    @Override
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();

        for (Map.Entry<String, Student> entry : studentsMap.entrySet()) {
            students.add(entry.getValue());
        }

        return students;
    }

    @Override
    public boolean existById(String id) {
        return studentsMap.containsKey(id);
    }
}
