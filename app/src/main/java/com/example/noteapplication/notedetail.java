package com.example.noteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notedetail extends AppCompatActivity {

    private TextView mtitleofnotedetail, mcontentofnotedetail;
    FloatingActionButton mgotoeditnote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notedetail);
        mtitleofnotedetail = findViewById(R.id.titleofnotedetail);
        mcontentofnotedetail = findViewById(R.id.contentofnotedetail);
        mgotoeditnote = findViewById(R.id.gotoeditnote);
        Toolbar toolbar = findViewById(R.id.toolbarofnotedetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        mgotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),editnoteactivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("noteID",data.getStringExtra("noteID"));
                v.getContext().startActivity(intent);
            }
        });
        mcontentofnotedetail.setText(data.getStringExtra("content"));
        mtitleofnotedetail.setText(data.getStringExtra("title"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}