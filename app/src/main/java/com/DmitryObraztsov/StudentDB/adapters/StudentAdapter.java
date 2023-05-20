package com.DmitryObraztsov.StudentDB.adapters;

import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_DATE_OF_BIRTH;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_FIRST_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_NUMBER;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_ID;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_LAST_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_PATRONYMIC;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.R;
import com.DmitryObraztsov.StudentDB.daos.StudentDao;
import com.DmitryObraztsov.StudentDB.dataUtils.UpdateStudentData;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Student;
import com.DmitryObraztsov.StudentDB.entities.StudentWithGroupNumber;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    List<Student> students;


    public StudentAdapter() {
        this.students = new ArrayList<>();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @NotNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentViewHolder holder, int position) {
        AppStudentDatabase db = Room.databaseBuilder(holder.recyclerStudentId.getContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
        StudentDao studentDao = db.studentDao();
        holder.recyclerStudentId.setText(String.valueOf(students.get(position).getId()));
        holder.recyclerFirstName.setText(students.get(position).getFirstName());
        holder.recyclerLastName.setText(students.get(position).getLastName());
        holder.recyclerPatronymic.setText(students.get(position).getPatronymic());
        holder.recyclerDateOfBirth.setText(students.get(position).getDateOfBirth());
        List<StudentWithGroupNumber> newStudents = studentDao.getAllStudentWithGroupNumber();
        holder.recyclerStudentGroupName.setText(String.valueOf(newStudents.get(position).groupNumber));

        holder.buttonDeleteStudent.setOnClickListener(view -> {

            studentDao.deleteById(students.get(position).getId());

            students.remove(position);

            notifyDataSetChanged();

        });
        holder.buttonEditStudent.setOnClickListener(view -> {

            Intent intent = new Intent(new Intent(holder.buttonEditStudent.getContext(), UpdateStudentData.class));

            intent.putExtra(UPDATED_ID, String.valueOf(students.get(position).getId()));
            intent.putExtra(UPDATED_FIRST_NAME, students.get(position).getFirstName());
            intent.putExtra(UPDATED_LAST_NAME, students.get(position).getLastName());
            intent.putExtra(UPDATED_PATRONYMIC, students.get(position).getPatronymic());

            intent.putExtra(UPDATED_DATE_OF_BIRTH, students.get(position).getDateOfBirth());
            intent.putExtra(UPDATED_GROUP_NUMBER, String.format(String.valueOf(students.get(position).getGroupId())));

            holder.buttonEditStudent.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView recyclerStudentId, recyclerFirstName, recyclerLastName, recyclerPatronymic, recyclerDateOfBirth, recyclerStudentGroupName;
        ImageButton buttonDeleteStudent, buttonEditStudent;

        public StudentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            recyclerStudentId = itemView.findViewById(R.id.recycler_student_id);
            recyclerFirstName = itemView.findViewById(R.id.recycler_first_name);
            recyclerLastName = itemView.findViewById(R.id.recycler_last_name);
            recyclerPatronymic = itemView.findViewById(R.id.recycler_patronymic);

            recyclerDateOfBirth = itemView.findViewById(R.id.recycler_date_of_birth);
            recyclerStudentGroupName = itemView.findViewById(R.id.recycler_student_group_number);

            buttonDeleteStudent = itemView.findViewById(R.id.button_delete_student);
            buttonEditStudent = itemView.findViewById(R.id.button_edit_student);
        }
    }
}
