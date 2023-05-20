package com.DmitryObraztsov.StudentDB.dataUtils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.DmitryObraztsov.StudentDB.R;
import com.DmitryObraztsov.StudentDB.adapters.GroupAdapter;
import com.DmitryObraztsov.StudentDB.daos.GroupDao;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Group;

import java.util.List;

public class FetchGroupData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_group_data);

        getRoomData();
    }

    public void getRoomData() {

        AppStudentDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();

        GroupDao groupDao = db.groupDao();

        RecyclerView groupRecyclerView = findViewById(R.id.group_recycler_view);

        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Group> groups = groupDao.getAllGroups();

        GroupAdapter adapter = new GroupAdapter(groups);

        groupRecyclerView.setAdapter(adapter);
    }
}
