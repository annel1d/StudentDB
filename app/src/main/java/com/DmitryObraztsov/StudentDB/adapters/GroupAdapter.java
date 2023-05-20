package com.DmitryObraztsov.StudentDB.adapters;

import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_FACULTY_NAME;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_ID;
import static com.DmitryObraztsov.StudentDB.dataUtils.Const.UPDATED_GROUP_NUMBER;

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
import com.DmitryObraztsov.StudentDB.daos.GroupDao;
import com.DmitryObraztsov.StudentDB.dataUtils.UpdateGroupData;
import com.DmitryObraztsov.StudentDB.db.AppStudentDatabase;
import com.DmitryObraztsov.StudentDB.entities.Group;
import com.DmitryObraztsov.StudentDB.viewUtils.PopupWindowCreator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    List<Group> groups;

    public GroupAdapter(List<Group> groups) {
        this.groups = groups;
    }

    @NonNull
    @NotNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupAdapter.GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GroupAdapter.GroupViewHolder holder, int position) {

        holder.recyclerGroupId.setText(String.valueOf(groups.get(position).getId()));

        holder.recyclerGroupNumber.setText(String.format(String.valueOf(groups.get(position).getGroupNumber())));
        holder.recyclerFacultyName.setText(groups.get(position).getFacultyName());

        holder.buttonDeleteGroup.setOnClickListener(view -> {
            AppStudentDatabase db = Room.databaseBuilder(holder.recyclerGroupId.getContext(),
                    AppStudentDatabase.class, AppStudentDatabase.DB_NAME).allowMainThreadQueries().build();
            GroupDao groupDao = db.groupDao();
            groupDao.deleteGroup(groups.get(position).getId());
            if (!groupDao.existsById(groups.get(position).getId())) {
                groups.remove(position);
            } else {
                PopupWindowCreator popupWindowCreator = new PopupWindowCreator();
                popupWindowCreator.createPopupWindow(view);
            }

            notifyDataSetChanged();

        });
        holder.buttonEditGroup.setOnClickListener(view -> {

            Intent intent = new Intent(new Intent(holder.buttonEditGroup.getContext(), UpdateGroupData.class));

            intent.putExtra(UPDATED_GROUP_ID, String.valueOf(groups.get(position).getId()));
            intent.putExtra(UPDATED_GROUP_NUMBER, String.format(String.valueOf(groups.get(position).getGroupNumber())));
            intent.putExtra(UPDATED_FACULTY_NAME, groups.get(position).getFacultyName());

            holder.buttonEditGroup.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView recyclerGroupId, recyclerGroupNumber, recyclerFacultyName;
        ImageButton buttonDeleteGroup, buttonEditGroup;

        public GroupViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            recyclerGroupId = itemView.findViewById(R.id.recycler_group_id);

            recyclerGroupNumber = itemView.findViewById(R.id.recycler_group_number);
            recyclerFacultyName = itemView.findViewById(R.id.recycler_faculty_name);

            buttonDeleteGroup = itemView.findViewById(R.id.button_delete_group);
            buttonEditGroup = itemView.findViewById(R.id.button_edit_group);
        }
    }


}
