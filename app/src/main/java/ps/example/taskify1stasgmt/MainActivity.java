package ps.example.taskify1stasgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static final String DONE_TASK = "DONE_TASK";
    public static final String TO_DONE = "TO_DONE";
    //==============================================================//
    public static final String DUE_TASKS_LIST = "Due tasks list";
    public static final String DONE_TASKS_LIST = "Done tasks list";
    //==============================================================//
    private ListView lstvDue;
    private Button btnAdd;

    //    ==========================
    //    The Main Two Important Objects To Use "Shared Preferences":

    static SharedPreferences prefs;            //To Read
    static SharedPreferences.Editor editor;    //To Write

    //    ==========================
    static ArrayList<String> DueTasks;
    static ArrayAdapter<String> DueTasksAdapter;

    //    ==========================
    static int global_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();           //just making the hooks

        setupSharedPrefs();     //siting SharedPrefs up (making the app ready to Read/Write)

//        DueTasksAdapter.notifyDataSetChanged();

        //setup bottom section that enables us to move between due and done tasks:
        BottomNavigationView bv = findViewById(R.id.botView);
        bv.setSelectedItemId(R.id.due);

        bv.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.due) {
                return true;
            } else if (item.getItemId() == R.id.done) {
                saveDueData();

//                String task="From Due";
//            DoneTasksActivity.DoneTasks.add(task);
//            DoneTasksActivity.DoneTasksAdapter.notifyDataSetChanged();

//                Intent intent = new Intent(this, DoneTasksActivity.class);
//                intent.putExtra(TO_DONE, task);
//                startActivity(intent);


                startActivity(new Intent(getApplicationContext(), DoneTasksActivity.class));
//                finish();
                return true;
            }
            return false;

        });


        //setup "Add Task" Button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.newInstance().show(getSupportFragmentManager(), AddTask.TAG);
            }

        });



    }

    @Override
    protected void onStart() {

        loadDueData();

        //creating the arrayList of tasks, and making the needed ArrayAdapter to contain it
//        DueTasks = new ArrayList<>();
        DueTasksAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                DueTasks);
        lstvDue.setAdapter(DueTasksAdapter);

        //making listview responsive to hold(to choose between deleting the task or mark it as done)
        lstvDue.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                global_int = position;

                showPopup(view);

                return true;


            }
        });

        super.onStart();
    }

    @Override
    protected void onPause() {
        saveDueData();
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveDueData();
        super.onStop();
    }


    private void setupSharedPrefs() {   //siting SharedPrefs up (making the app ready to Read/Write)
//        prefs=getSharedPreferences("shared preferences",MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();  //to Write
    }

    public void saveDueData() {

//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        //getting data from gson and storing it in a string.
        String json = gson.toJson(DueTasks);

        //saving data in sharedPrefs
        editor.putString(DUE_TASKS_LIST, json);//main line

        editor.commit();

        Toast.makeText(this, "Saved DueTasks arrayList to Shared preferences.", Toast.LENGTH_SHORT).show();
    }

    private void loadDueData() {

//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString(DUE_TASKS_LIST, null);

        //getting the type of DueTasks arrayList
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        //saving data to DueTasks arrayList, after converting it from json using gson
        DueTasks = gson.fromJson(json, type);//main line

        //checking if DueTasks arrayList is not declared to make one
        if (DueTasks == null) {
            DueTasks = new ArrayList<>();
        }
//        DueTasksAdapter.notifyDataSetChanged();
        Toast.makeText(this, "DueTasks arrayList filled from Shared preferences. ", Toast.LENGTH_SHORT).show();

    }

    private void setupViews() { //just making the hooks
        lstvDue = findViewById(R.id.lstvDue);
        btnAdd = findViewById(R.id.btnAdd);
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.Done:
//                Toast.makeText(this,"Done clicked",Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.Delete:
//                Toast.makeText(this,"Delete clicked",Toast.LENGTH_SHORT).show();
//                Context con=getApplicationContext();
//                Toast.makeText(con,"Item Removed",Toast.LENGTH_LONG).show();//important
//
//                tasks.remove(position);
//                tasksAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return false;
//        }
//
//    }


    public void showPopup(View view) { //done and delete menu (when hold on a task)
//        Context con=getApplicationContext();
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.setOnMenuItemClickListener(this);
        popUp.inflate(R.menu.popup_menu);
        popUp.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Context con = getApplicationContext();

//        lstvDue.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        int itemId = item.getItemId();
        if (itemId == R.id.Done) {

            String task=DueTasks.get(global_int);
//            DoneTasksActivity.DoneTasks.add(task);
//            DoneTasksActivity.DoneTasksAdapter.notifyDataSetChanged();

            Intent intent = new Intent(this, DoneTasksActivity.class);
            intent.putExtra(DONE_TASK, task);
            startActivity(intent);


//            //adding the task to the second listView (Done listView)
//            DoneTasksActivity.DoneTasksAdapter.add(tasks.get(global_int));
//            DoneTasksActivity.DoneTasksAdapter.notifyDataSetChanged();

            //removing the task from the first listView (Due listView)
            DueTasks.remove(global_int);
            DueTasksAdapter.notifyDataSetChanged();


            Toast.makeText(con, "Done clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.Delete) {
            //                        Toast.makeText(con, "Delete clicked", Toast.LENGTH_SHORT).show();

            Toast.makeText(con, "Item Removed", Toast.LENGTH_LONG).show();//important

            DueTasks.remove(global_int);
            DueTasksAdapter.notifyDataSetChanged();

            return true;
        }
        return true;

    }


}