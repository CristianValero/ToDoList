package com.titianvalero.todolist.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titianvalero.todolist.R;
import com.titianvalero.todolist.Utils.Database.DatabaseManager;
import com.titianvalero.todolist.Utils.Database.FeedReaderDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private final Context context;
    private final DatabaseManager databaseManager;

    public TaskAdapter(Context context, DatabaseManager databaseManager) {
        this.tasks = new ArrayList<>();
        this.context = context;
        this.databaseManager = databaseManager;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvName.setText(task.getName());
        holder.tvDate.setText(task.getDate());
        holder.cbCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deleteCompletedTask(task.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void insertNewTask(Task task) {
        tasks.add(task);
        this.notifyDataSetChanged();
    }

    public void loadTasksFromDatabase() {
        databaseTaskRead();
        this.notifyDataSetChanged();
    }

    private void databaseTaskRead() {
        final String query = "SELECT * FROM tasks;";
        Cursor cursor = databaseManager.getQuery(query);

        while ( cursor.moveToNext() )
            tasks.add(new Task(cursor.getString(1), cursor.getString(3), cursor.getInt(2)));

        cursor.close();
    }

    private void deleteCompletedTask(final String taskName) {
        databaseManager.deleteData(taskName);

        List<Task> updated = new ArrayList<>();

        for ( Task task : tasks ) {
            if ( !task.getName().equalsIgnoreCase(taskName) ) {
                updated.add(task);
            }
        }

        this.tasks = updated;
        this.notifyDataSetChanged();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvDate;
        private final CheckBox cbCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_entry_name);
            tvDate = itemView.findViewById(R.id.tv_date_view);
            cbCompleted = itemView.findViewById(R.id.cb_task_checked);
        }

    }

}
