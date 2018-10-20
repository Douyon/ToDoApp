package org.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {
    private TextView TodoDescription;
    public Button SaveButton;
    ArrayList<String> items;
    private String myString= "ITEM EDIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        TodoDescription = (TextView) findViewById(R.id.itemEdit);
        SaveButton = (Button) findViewById(R.id.btnSave);
        Intent intent = getIntent();
        if(intent.getExtras().getString("text") != null){
            String text = intent.getExtras().getString("text");
            String action = intent.getAction();
            String  type = intent.getType();
            TodoDescription.setText(text);
        }

    }
    public void onSubmit(View v){
        EditText TodoDescription = (EditText) findViewById(R.id.etEditText);
        Intent data = new Intent();
        data.putExtra("text", TodoDescription.getText().toString());
        int TodoIndex = getIntent().getIntExtra("position",  0);
        data.putExtra("position", TodoIndex);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onSave(View view) {
EditText editText =(EditText) findViewById(R.id.etEditText);
Intent data = new Intent();
data.putExtra("etEditText", editText.getText().toString());
data.putExtra("REQUEST",  0);


    }
}
