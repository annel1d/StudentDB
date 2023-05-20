package com.DmitryObraztsov.StudentDB;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.daos.GroupDao;
import com.DmitryObraztsov.StudentDB.dataUtils.FetchGroupData;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Group;

public class MainGroupActivity extends AppCompatActivity {
    private static final String SUCCESS_INSERT_TEXT = "Inserted Successfully";
    private static final String FAILURE_INSERT_TEXT = "An Error Occurred";
    private EditText groupNumber, facultyName;
    private TextView errorGroupLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        groupNumber = findViewById(R.id.group_number);
        facultyName = findViewById(R.id.group_faculty_name);

        Button buttonSaveGroup = findViewById(R.id.button_save_group_to_db);
        Button buttonFetchGroupData = findViewById(R.id.button_fetch_group_data);
        errorGroupLabel = findViewById(R.id.error_group_label);

        buttonSaveGroup.setOnClickListener(view -> {

            AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
            GroupDao groupDao = db.groupDao();
            try {
                groupDao.addGroup(new Group(Integer.parseInt(groupNumber.getText().toString()), facultyName.getText().toString()));
                clearFields();
                errorGroupLabel.setText(SUCCESS_INSERT_TEXT);
            } catch (Exception e) {
//                clearFields();
                errorGroupLabel.setText(FAILURE_INSERT_TEXT);
            }
        });
        buttonFetchGroupData.setOnClickListener(view ->

                startActivity(new Intent(getApplicationContext(), FetchGroupData.class)));
    }

    private void clearFields() {
        groupNumber.setText("");
        facultyName.setText("");
    }
}
