package com.DmitryObraztsov.StudentDB.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.DmitryObraztsov.StudentDB.entities.Group;
import com.DmitryObraztsov.StudentDB.entities.GroupWithStudents;

import java.util.List;

@Dao
public interface GroupDao {

    @Insert
    void addGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("SELECT * FROM groups")
    List<Group> getAllGroups();

    @Query("DELETE FROM groups WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM groups WHERE id= :id)")
    boolean existsById(int id);

    @Query("UPDATE groups SET groupNumber=:newGroupNumber, facultyName=:newFacultyName  WHERE id = :id")
    void updateById(int id, int newGroupNumber, String newFacultyName);

    @Transaction
    default void deleteGroup(int id) {
        GroupWithStudents groupWithStudents = getGroupWithStudents(id);
        if (groupWithStudents.students.isEmpty()) {
            deleteGroup(groupWithStudents.group);
        }
    }

    @Query("SELECT * FROM groups WHERE id = :id")
    GroupWithStudents getGroupWithStudents(int id);

}

