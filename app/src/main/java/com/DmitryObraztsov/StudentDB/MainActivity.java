package com.DmitryObraztsov.StudentDB;

import static com.DmitryObraztsov.StudentDB.dataUtils.Const.FAILURE_INSERT_TEXT;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.SUCCESS_INSERT_TEXT;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.daos.StudentDao;
import com.DmitryObraztsov.StudentDB.dataUtils.FetchStudentData;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Group;
import com.DmitryObraztsov.StudentDB.entities.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText firstName, secondName, patronymic;
    private Spinner groupNumber;
    private DatePicker datePicker;
    private TextView errorStudentLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.student_first_name);
        secondName = findViewById(R.id.student_second_name);
        patronymic = findViewById(R.id.student_patronymic);
        groupNumber = findViewById(R.id.student_group_number);

        Button buttonSaveStudent = findViewById(R.id.button_save_student_to_db);
        Button buttonFetchStudentData = findViewById(R.id.button_fetch_student_data);
        Button buttonGoToGroups = findViewById(R.id.button_go_to_groups);
        errorStudentLabel = findViewById(R.id.error_student_label);

        datePicker = findViewById(R.id.student_date_of_birth);
//        updateGroupSpinner();
        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();

        List<Group> groups = db.groupDao().getAllGroups();

        List<String> groupNumberList = new ArrayList<>();
        for (Group group : groups) {
            groupNumberList.add(String.valueOf(group.getGroupNumber()));
        }

        ArrayAdapter<String> groupNumberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupNumberList);
        groupNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupNumber.setAdapter(groupNumberAdapter);


        buttonSaveStudent.setOnClickListener(view -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Добавляем 1, так как возвращаемое значение начинается с 0 для января
            int year = datePicker.getYear();
            int selectedGroupPosition = groupNumber.getSelectedItemPosition();

            String dateOfBirth = String.format("%02d.%02d.%04d", day, month, year);
//            AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
//                    AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
            StudentDao studentDao = db.studentDao();
            try {
                if ((firstName.getText().toString().length() < 20) && (secondName.getText().toString().length() < 20) &&
                        (patronymic.getText().toString().length() < 20) && (selectedGroupPosition != AdapterView.INVALID_POSITION) &&
                        (!firstName.getText().toString().isEmpty()) && (!secondName.getText().toString().isEmpty()) && (!patronymic.getText().toString().isEmpty())) {
                    studentDao.addStudent(new Student(firstName.getText().toString(), secondName.getText().toString(), patronymic.getText().toString(),
                            dateOfBirth, groups.get(selectedGroupPosition).getId()));
                    clearFields();
                    errorStudentLabel.setText(SUCCESS_INSERT_TEXT);
                    updateGroupSpinner();
                } else {
                    errorStudentLabel.setText(FAILURE_INSERT_TEXT);
                    updateGroupSpinner();
                }
            } catch (Exception e) {
//                clearFields();
                updateGroupSpinner();
                errorStudentLabel.setText(FAILURE_INSERT_TEXT);
            }
        });

        buttonFetchStudentData.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), FetchStudentData.class)));

        buttonGoToGroups.setOnClickListener(v -> {
            Intent group_intent = new Intent(MainActivity.this, MainGroupActivity.class);
            startActivity(group_intent);
        });

    }

    private void clearFields() {
        firstName.setText("");
        secondName.setText("");
        patronymic.setText("");
        datePicker.updateDate(2000, 5, 15);
        groupNumber.setSelection(0);
    }

    private void updateGroupSpinner() {
        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();

        List<Group> groups = db.groupDao().getAllGroups();

        List<String> groupNumberList = new ArrayList<>();
        for (Group group : groups) {
            groupNumberList.add(String.valueOf(group.getGroupNumber()));
        }

        ArrayAdapter<String> groupNumberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupNumberList);
        groupNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupNumber.setAdapter(groupNumberAdapter);
    }

}