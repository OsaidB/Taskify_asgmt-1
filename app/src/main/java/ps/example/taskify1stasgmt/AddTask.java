package ps.example.taskify1stasgmt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText inputTask;
    private Button btnConfirmAdd;


    public static AddTask newInstance() {
        return new AddTask();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task_popup, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        inputTask = getView().findViewById(R.id.inputTask);
        btnConfirmAdd = getView().findViewById(R.id.btnConfirmAdd);

//        if(inputTask.equals("")){
//            btnConfirmAdd.setEnabled(false);
////            btnConfirmAdd.getIcon().setAlpha(130);
//        }


        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTask(v);
//                Toast.makeText(this, "Enter A Task To Add, Please...",Toast.LENGTH_LONG).show();
//                MainActivity.saveData();
            }
        });


    }

    public void addTask(View v) {
//        MainActivity d=new MainActivity();
//        ArrayAdapter<String> tasksAdapter = d.getTasksAdapter();

        String taskText = inputTask.getText().toString();

        if (!(taskText.equals(""))) {
            btnConfirmAdd.setEnabled(true);
            MainActivity.DueTasks.add(taskText);
//            MainActivity.DueTasksAdapter.add(taskText);
            MainActivity.DueTasksAdapter.notifyDataSetChanged();

//            Context con = getApplicationContext();
//            startActivity(new Intent(getApplicationContext(), DoneTasksActivity.class));
//            Intent intent=new Intent(this,MainActivity.class);
//            startActivity(intent);

            inputTask.setText("");
        } else {
            btnConfirmAdd.setEnabled(false);
//         Context con =MainActivity.getApplicationContext();
//            Toast.makeText(con, "Enter A Task To Add, Please...",Toast.LENGTH_LONG).show();
        }
        btnConfirmAdd.setEnabled(true);

    }
}
