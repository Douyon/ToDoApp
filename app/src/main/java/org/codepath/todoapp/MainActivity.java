package org.codepath.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_RESPONSE = 1;
    ArrayList<String> todoItems;
    private Context mcontext;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvitems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText=(EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    todoItems.remove(position);
                    aToDoAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;

            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Nullable
                        public final AdapterView.OnItemClickListener getOnItemClickListener(){
                            return null;
                        }
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            Intent intent = new Intent(getApplicationContext(),EditItemActivity.class);
                            intent.putExtra("text", todoItems.get(position) );
                            Toast.makeText(getApplicationContext(), ""+todoItems.get(position), Toast.LENGTH_SHORT).show();
                            intent.putExtra("position",position);
                            startActivityForResult(intent,REQUEST_RESPONSE);
                            writeItems();

                        }

                    }
        );
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems
        );
    }
    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            todoItems = new ArrayList<String>();
    }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_RESPONSE){
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position", 0);
            aToDoAdapter.notifyDataSetChanged();
            todoItems.set(position, text);
            writeItems();

        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
