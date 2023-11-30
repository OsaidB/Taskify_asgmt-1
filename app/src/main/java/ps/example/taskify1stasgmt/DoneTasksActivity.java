package ps.example.taskify1stasgmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DoneTasksActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    static ArrayList<String> DoneTasks;
    static ArrayAdapter<String> DoneTasksAdapter;

    private ListView lstvDone;

    static int global_int;


//    private static final int FIRST_ID=Menu.FIRST;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_done_tasks);

        setupViews();           //just making the hooks

        loadDoneData();

//        invalidateOptionsMenu();

        BottomNavigationView bv = findViewById(R.id.botView);
        bv.setSelectedItemId(R.id.done);

        bv.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.due) {
                saveDoneData();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
                return true;

            } else if (item.getItemId() == R.id.done) {

                return true;
            } else {

                return false;
            }



        });



    }
    @Override
    protected void onStart() {
//        invalidateOptionsMenu();
        loadDoneData();

        //creating the arrayList of tasks, and making the needed ArrayAdapter to contain it
//        DoneTasks = new ArrayList<>();
        DoneTasksAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                DoneTasks);
        lstvDone.setAdapter(DoneTasksAdapter);

        //making listview responsive to hold(to delete a Done task if needed)
        lstvDone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                global_int = position;

                showPopup(view);

                return true;


            }
        });

        Intent intenttt=getIntent();
        String doneTask=intenttt.getStringExtra(MainActivity.DONE_TASK);

        if (doneTask != null) {
            DoneTasks.add(doneTask);
            DoneTasksAdapter.notifyDataSetChanged();
        }
//        else {
//            // There was no extra string
//        }
//

        super.onStart();
    }

    @Override
    protected void onPause() {
        saveDoneData();
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveDoneData();
        super.onStop();
    }

    private void setupViews() { //just making the hooks
        lstvDone = findViewById(R.id.lstvDone);
    }


    private void saveDoneData() {
        Gson gson = new Gson();

        //getting data from gson and storing it in a string.
        String json = gson.toJson(DoneTasks);

        //saving data in sharedPrefs
        MainActivity.editor.putString(MainActivity.DONE_TASKS_LIST, json);//main line

        MainActivity.editor.commit();

        Toast.makeText(this, "Saved DoneTasks arrayList to Shared preferences.", Toast.LENGTH_SHORT).show();

    }


    public void loadDoneData() {
        Gson gson = new Gson();

        String json = MainActivity.prefs.getString(MainActivity.DONE_TASKS_LIST, null);

        //getting the type of DoneTasks arrayList
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();

        //saving data to DoneTasks arrayList, after converting it from json using gson
        DoneTasks = gson.fromJson(json, type);//main line

        //checking if DoneTasks arrayList is not declared to make one
        if (DoneTasks == null) {
            DoneTasks = new ArrayList<>();
        }

//        DoneTasksAdapter.notifyDataSetChanged();
        Toast.makeText(this, "DoneTasks arrayList filled from Shared preferences. ", Toast.LENGTH_SHORT).show();
    }



    public void showPopup(View view) { //done and delete menu (when hold on a task)
//        Context con=getApplicationContext();
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.setOnMenuItemClickListener(this);
        popUp.inflate(R.menu.popup_menu_done);
//        popUp.disable(done);
        popUp.show();
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
////        menu.getMenu().removeItem(FIRST_ID);
//
//
////        menu.removeItem(menu.FIRST);
//
////
////        MenuItem item = menu.findItem(R.id.Done);
////
////            item.setVisible(false);
//
//
//        super.onPrepareOptionsMenu(menu);
//        menu.findItem(R.id.Done).setVisible(false);
////        menu.findItem(R.id.menuItemToRight).setVisible(false);
//        return true;
//
//
////        MenuItem item = menu.findItem(R.id.Done);
////        item.setEnabled(false);
//////        if (menu.) {
////            menu.getItem(1).setEnabled(false);
////            // You can also use something like:
////            // menu.findItem(R.id.example_foobar).setEnabled(false);
////        }
////        return true;
////
////
////        menu.getItem(R.id.Done).setEnabled(false);
////        return super.onPrepareOptionsMenu(menu);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.Done);
//
//        item.setVisible(false);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Context con = getApplicationContext();

//        lstvDue.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        int itemId = item.getItemId();
//        if (itemId == R.id.Done) {
//
////            Intent intent = new Intent(this, DoneTasksActivity.class);
////            intent.putExtra(DONE_TASK, DueTasks.get(global_int));
////            startActivity(intent);
////
////
//////            //adding the task to the second listView (Done listView)
//////            DoneTasksActivity.DoneTasksAdapter.add(tasks.get(global_int));
//////            DoneTasksActivity.DoneTasksAdapter.notifyDataSetChanged();
////
////            //removing the task from the first listView (Due listView)
////            DueTasks.remove(global_int);
////            DueTasksAdapter.notifyDataSetChanged();
////
////
////            Toast.makeText(con, "Done clicked", Toast.LENGTH_SHORT).show();
//            //do nothing
//            return true;
//        } else
            if (itemId == R.id.Delete) {
            //                        Toast.makeText(con, "Delete clicked", Toast.LENGTH_SHORT).show();

            Toast.makeText(con, "Item Removed", Toast.LENGTH_LONG).show();//important

            DoneTasks.remove(global_int);
            DoneTasksAdapter.notifyDataSetChanged();
            return true;
        }
        return true;

    }



}