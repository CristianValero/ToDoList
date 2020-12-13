package com.titianvalero.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.titianvalero.todolist.Utils.Database.DatabaseManager;
import com.titianvalero.todolist.Utils.Database.FeedReaderDatabaseHelper;
import com.titianvalero.todolist.Utils.Task;
import com.titianvalero.todolist.Utils.TaskAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CREATE_TASK_ACTIVITY = 100;

    private TaskAdapter taskAdapter;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton btnAddTask = findViewById(R.id.fab_add_task);
        final Toolbar tbMain = findViewById(R.id.tb_main);
        final RecyclerView rvTasks = findViewById(R.id.rv_show_tasks);

        databaseManager = new DatabaseManager(new FeedReaderDatabaseHelper(getApplicationContext()), this);

        taskAdapter = new TaskAdapter(getApplicationContext(), databaseManager);
        taskAdapter.loadTasksFromDatabase();

        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateTaskActivity();
            }
        });

        setSupportActionBar(tbMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null ) {
            if ( resultCode == RESULT_OK ) {
                if ( requestCode == REQUEST_CODE_CREATE_TASK_ACTIVITY ) {
                    createTask(data);
                }
            }
        }
    }

    private void createTask(final Intent data) {
        final String entry = data.getStringExtra(CreateTaskActivity.TASK_ENTRY_KEY);
        final String date = data.getStringExtra(CreateTaskActivity.TASK_DATE_KEY);
        final int priority = data.getIntExtra(CreateTaskActivity.TASK_PRIORITY_KEY, 0);

        if ( entry != null && date != null && priority != 0 ) {
            final String query = "INSERT INTO tasks ( entry, priority, date_expire ) VALUES ( '" + entry + "', " + priority + ", '" + date + "' );";
            databaseManager.insertData(query);
        }

        taskAdapter.insertNewTask(new Task(entry, date, priority));
    }

    private void openCreateTaskActivity() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CREATE_TASK_ACTIVITY);
    }

}