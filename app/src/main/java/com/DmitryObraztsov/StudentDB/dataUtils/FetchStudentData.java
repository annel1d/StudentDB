package com.DmitryObraztsov.StudentDB.dataUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.R;
import com.DmitryObraztsov.StudentDB.adapters.StudentAdapter;
import com.DmitryObraztsov.StudentDB.daos.StudentDao;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Student;
import com.DmitryObraztsov.StudentDB.entities.StudentWithGroupNumber;

import java.util.ArrayList;
import java.util.List;

public class FetchStudentData extends AppCompatActivity {

    private List<StudentWithGroupNumber> studentsWithGroupNumbers;
    private List<Student> students;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_student_data);

        getRoomData();

        Button filterButton = findViewById(R.id.filter_by_lastname);
        filterButton.setOnClickListener(v -> filterStudentsByLastName());

        Button filterByGroupButton = findViewById(R.id.filter_by_group_number);
        filterByGroupButton.setOnClickListener(v -> filterStudentsByGroupNumber());
    }

    public void getRoomData() {
        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
        StudentDao studentDao = db.studentDao();

        RecyclerView studentRecyclerView = findViewById(R.id.student_recycler_view);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentsWithGroupNumbers = studentDao.getAllStudentWithGroupNumber();

        students = new ArrayList<>();
        for (StudentWithGroupNumber studentWithGroupNumber : studentsWithGroupNumbers) {
            students.add(studentWithGroupNumber.getStudent());
        }

        adapter = new StudentAdapter(students);
        studentRecyclerView.setAdapter(adapter);
    }

    public void filterStudentsByLastName() {
        EditText lastNameEditText = findViewById(R.id.last_name_edit_text);
        String lastName = lastNameEditText.getText().toString().trim();

        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                filteredStudents.add(student);
            }
        }

        adapter.setStudents(filteredStudents);
        adapter.notifyDataSetChanged();
    }

    public void filterStudentsByGroupNumber() {
        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
        StudentDao studentDao = db.studentDao();

        EditText groupNumberEditText = findViewById(R.id.group_number_edit_text);
        String groupNumberText = groupNumberEditText.getText().toString().trim();

        if (!groupNumberText.isEmpty()) {
            int groupNumber = Integer.parseInt(groupNumberText);

            List<Student> filteredStudents = new ArrayList<>();
            for (StudentWithGroupNumber studentWithGroupNumber : studentsWithGroupNumbers) {
                if (studentWithGroupNumber.getGroupNumber() == groupNumber) {
                    filteredStudents.add(studentWithGroupNumber.getStudent());
                }
            }

            adapter.setStudents(filteredStudents);
            adapter.notifyDataSetChanged();
        }
    }
}

