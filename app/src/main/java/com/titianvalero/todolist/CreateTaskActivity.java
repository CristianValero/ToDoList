package com.titianvalero.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;

@SuppressLint("SimpleDateFormat")
public class CreateTaskActivity extends AppCompatActivity {

    public static final String TASK_ENTRY_KEY = "TASK_ENTRY_KEY";
    public static final String TASK_DATE_KEY = "TASK_DATE_KEY";
    public static final String TASK_PRIORITY_KEY = "TASK_PRIORITY_KEY";

    private TextView tvShowSelectedTaskPriority;
    private FloatingActionButton btnAccept;
    private EditText etTaskEntry;

    private String taskEntry;
    private String date;
    private int taskPriority;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        final SeekBar sbSelectPriority = findViewById(R.id.sb_select_priority);
        final FloatingActionButton btnCancel = findViewById(R.id.btn_cancel_create_task);
        final CalendarView cvSelectTaskTime = findViewById(R.id.cv_select_task_time);

        etTaskEntry = findViewById(R.id.et_task_entry);
        btnAccept = findViewById(R.id.btn_accept_create_task);
        tvShowSelectedTaskPriority = findViewById(R.id.tv_show_task_priority);

        sbSelectPriority.setMin(1);
        sbSelectPriority.setMax(3);

        date = new SimpleDateFormat("dd/MM/yyyy").format(cvSelectTaskTime.getDate());

        etTaskEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTaskEntry();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) { }
        });

        cvSelectTaskTime.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/" + year;
            }
        });

        sbSelectPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateShowPriority(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptCreateTask();
            }
        });

        updateUI();
    }

    private void updateUI() {
        taskEntry = "";
        taskPriority = 1;

        updateShowPriority(1);

        btnAccept.setVisibility(View.GONE);
    }

    private void acceptCreateTask() {
        Intent intent = new Intent();
        intent.putExtra(TASK_ENTRY_KEY, taskEntry);
        intent.putExtra(TASK_DATE_KEY, date);
        intent.putExtra(TASK_PRIORITY_KEY, taskPriority);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateTaskEntry() {
        final String entry = etTaskEntry.getText().toString();

        if ( entry.length() != 0 ) {
            taskEntry = entry;
            btnAccept.setVisibility(View.VISIBLE);
        } else
            btnAccept.setVisibility(View.GONE);
    }

    private void updateShowPriority(final int progress) {
        switch (progress) {
            case 1:
                tvShowSelectedTaskPriority.setText(R.string.priority_low);
                break;
            case 2:
                tvShowSelectedTaskPriority.setText(R.string.priority_medium);
                break;
            case 3:
                tvShowSelectedTaskPriority.setText(R.string.priority_high);
        }

        taskPriority = progress;
    }
}