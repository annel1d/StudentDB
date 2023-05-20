package com.DmitryObraztsov.StudentDB.entities;


import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "students",
        foreignKeys = {
                @ForeignKey(entity = Group.class,
                        parentColumns = "id",
                        childColumns = "groupId"
//                        onDelete = ForeignKey.CASCADE
                )
        })
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "firstName", typeAffinity = ColumnInfo.TEXT)
    @Size(max = 20)
    @NonNull
    private String firstName;
    @Size(max = 20)
    @NonNull
    private String lastName;
    @Size(max = 20)
    @NonNull
    private String patronymic;
    private String dateOfBirth;
    private int groupId;

    public Student(@NonNull String firstName, @NonNull String lastName, @NonNull String patronymic, String dateOfBirth, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(@NonNull String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


}
