package com.DmitryObraztsov.StudentDB.daos;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.DmitryObraztsov.StudentDB.entities.Student;
import com.DmitryObraztsov.StudentDB.entities.StudentWithGroupNumber;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void addStudent(Student student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudent();

    @Query("DELETE FROM students WHERE id = :id")
    void deleteById(int id);

    @Query("UPDATE students SET firstName = :newFirstName, lastName=:newLastName, patronymic=:newPatronymic, dateOfBirth=:newDateOfBirth, groupId=:newStudentGroupNumber  WHERE id = :id")
    void updateById(int id, String newFirstName, String newLastName, String newPatronymic, String newDateOfBirth, int newStudentGroupNumber);

    @Query("SELECT students.*, groups.groupNumber FROM students JOIN groups ON students.groupId = groups.id")
    List<StudentWithGroupNumber> getAllStudentWithGroupNumber();

}
