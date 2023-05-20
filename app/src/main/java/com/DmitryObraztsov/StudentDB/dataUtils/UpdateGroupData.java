package com.DmitryObraztsov.StudentDB.dataUtils;

import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_FACULTY_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_ID;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.MainActivity;
import com.DmitryObraztsov.StudentDB.R;
import com.DmitryObraztsov.StudentDB.daos.GroupDao;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;

public class UpdateGroupData extends AppCompatActivity {

    private EditText editGroupNumber, editFacultyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_group_data);

        editGroupNumber = findViewById(R.id.edit_group_number);
        editFacultyName = findViewById(R.id.edit_faculty_name);

        Button buttonUpdateGroupData = findViewById(R.id.update_group_data);

        int updateGroupId = Integer.parseInt(getIntent().getStringExtra(UPDATED_GROUP_ID));
        editGroupNumber.setText(getIntent().getStringExtra(UPDATED_GROUP_NUMBER));
        editFacultyName.setText(getIntent().getStringExtra(UPDATED_FACULTY_NAME));

        buttonUpdateGroupData.setOnClickListener(view -> {
            AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
            GroupDao groupDao = db.groupDao();
            groupDao.updateById(updateGroupId, Integer.parseInt(editGroupNumber.getText().toString()), editFacultyName.getText().toString());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("updateSpinner", true);
            startActivity(intent);

            startActivity(new Intent(getApplicationContext(), FetchGroupData.class));
            finish();
        });
    }
}
