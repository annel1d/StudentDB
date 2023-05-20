package com.DmitryObraztsov.StudentDB.dataUtils;

import static com.DmitryObraztsov.StudentDB.dataUtils.Const.FAILURE_INSERT_TEXT;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.SUCCESS_INSERT_TEXT;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_DATE_OF_BIRTH;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_FIRST_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_NUMBER;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_ID;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_LAST_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_PATRONYMIC;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.R;
import com.DmitryObraztsov.StudentDB.daos.StudentDao;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Group;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateStudentData extends AppCompatActivity {
    private EditText editFirstName, editLastName, editPatronymic;
    private Spinner editStudentGroupNumber;
    private TextView errorStudentLabel;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_data);

        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);
        editPatronymic = findViewById(R.id.edit_patronymic);
        datePicker = findViewById(R.id.edit_date_of_birth);
        editStudentGroupNumber = findViewById(R.id.edit_student_group_number);
        Button buttonUpdateStudentData = findViewById(R.id.update_student_data);
        errorStudentLabel = findViewById(R.id.error_edit_student_label);

        int updateId = Integer.parseInt(getIntent().getStringExtra(UPDATED_ID));
        editFirstName.setText(getIntent().getStringExtra(UPDATED_FIRST_NAME));
        editLastName.setText(getIntent().getStringExtra(UPDATED_LAST_NAME));
        editPatronymic.setText(getIntent().getStringExtra(UPDATED_PATRONYMIC));
        String dateString = getIntent().getStringExtra(UPDATED_DATE_OF_BIRTH);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = dateFormat.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            datePicker.updateDate(year, month, day);
        } catch (ParseException e) {
            errorStudentLabel.setText(FAILURE_INSERT_TEXT);
            datePicker.updateDate(2000, 5, 15);
        }

        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();

        List<Group> groups = db.groupDao().getAllGroups();

        List<String> groupNumberList = new ArrayList<>();
        for (Group group : groups) {
            groupNumberList.add(String.valueOf(group.getGroupNumber()));
        }

        ArrayAdapter<String> groupNumberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groupNumberList);
        groupNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStudentGroupNumber.setAdapter(groupNumberAdapter);

//        // Получить идентификатор группы выбранного студента
//        int studentGroupId = getIntent().getIntExtra(UPDATED_GROUP_NUMBER, -1);
//
//        // Найти позицию группы в списке по идентификатору
//        int selectedGroupPosition = -1;
//        for (int i = 0; i < groups.size(); i++) {
//            if (groups.get(i).getId() == studentGroupId) {
//                selectedGroupPosition = i;
//                break;
//            }
//        }
//
//        // Если позиция найдена, установить выбранную позицию в Spinner
//        if (selectedGroupPosition != -1) {
//            editStudentGroupNumber.setSelection(selectedGroupPosition);
////        }
//
//        int finalSelectedGroupPosition = selectedGroupPosition;
        buttonUpdateStudentData.setOnClickListener(view -> {
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            String patronymic = editPatronymic.getText().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            int selectedGroupPosition = editStudentGroupNumber.getSelectedItemPosition();


            String dateOfBirth = String.format("%02d.%02d.%04d", day, month, year);

//            // Получить идентификатор группы по выбранной позиции в Spinner
//            int selectedGroupId = groups.get(finalSelectedGroupPosition).getId();

            try {
                StudentDao studentDao = db.studentDao();
                studentDao.updateById(updateId, firstName, lastName, patronymic, String.valueOf(dateOfBirth), groups.get(selectedGroupPosition).getId());
                errorStudentLabel.setText(SUCCESS_INSERT_TEXT);
            } catch (Exception e) {
                errorStudentLabel.setText(FAILURE_INSERT_TEXT);
            }
        });
    }
}
