package com.DmitryObraztsov.StudentDB.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.DmitryObraztsov.StudentDB.daos.GroupDao;
import com.DmitryObraztsov.StudentDB.daos.StudentDao;
import com.DmitryObraztsov.StudentDB.entities.Group;
import com.DmitryObraztsov.StudentDB.entities.Student;


@Database(entities = {Student.class, Group.class}, version = 1)
public abstract class AppStudentDatabase extends RoomDatabase {
    public static final String DB_NAME = "myDB1";

    public abstract StudentDao studentDao();

    public abstract GroupDao groupDao();
}
