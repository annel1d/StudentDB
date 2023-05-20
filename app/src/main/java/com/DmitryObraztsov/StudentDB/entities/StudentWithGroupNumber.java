package com.DmitryObraztsov.StudentDB.entities;

import androidx.room.Embedded;

public class StudentWithGroupNumber {
    @Embedded
    public Student student;

    public int getGroupNumber() {
        return groupNumber;
    }

    public int groupNumber;

    public Student getStudent() {
        return student;
    }
}
