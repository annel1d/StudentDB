package com.DmitryObraztsov.StudentDB.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithStudents {
    @Embedded
    public Group group;
    @Relation(
            parentColumn = "id",
            entityColumn = "groupId"
    )
    public List<Student> students;
}