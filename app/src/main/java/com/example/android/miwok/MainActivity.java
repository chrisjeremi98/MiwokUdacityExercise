/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        //NumberClickListener clickListener=new NumberClickListener();
        TextView view1 =(TextView) findViewById(R.id.numbers);
        TextView view2 =(TextView) findViewById(R.id.family);
        TextView view3 =(TextView) findViewById(R.id.colors);
        TextView view4 =(TextView) findViewById(R.id.phrases);

        //view1.setOnClickListener(clickListener);
        view1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i= new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(i);
            }
        });
        view2.setOnClickListener(new View.OnClickListener(){
            @Override
            // menuju activity lain
            public void onClick(View view){
                Intent intent1=new Intent(MainActivity.this, FamilyMembersActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });

        view3.setOnClickListener(new View.OnClickListener(){
            @Override
            // menuju activity lain
            public void onClick(View view){
                Intent intent1=new Intent(MainActivity.this, ColorsActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });
        //
        view4.setOnClickListener(new View.OnClickListener(){
            @Override
            // menuju activity lain
            public void onClick(View view){
                Intent intent1=new Intent(MainActivity.this, PhrasesActivity.class);
                MainActivity.this.startActivity(intent1);
            }
        });




    }
}
