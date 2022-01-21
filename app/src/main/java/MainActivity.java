package com.less;

import android.app.*;
import android.os.*;

import android.view.*; //View;
import android.view.View.*;  //OnClickListener
import android.view.KeyEvent.Callback ;

import android.widget.*;   
import android.util.*;
import android.content.*;

import java.util.*; 


public class MainActivity extends Activity implements OnClickListener {

  TextView text0;
  TextView text1;
    
  Button        btn0, btn1, btn2, btn3, btn4, btn5;
//  Button [] buttArray = { btn0, btn1, btn2, btn3, btn4, btn5};
  
  EditText edText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    text0 = (TextView) findViewById(R.id.text0);    
    text1 = (TextView) findViewById(R.id.text1);  

    btn0=(Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this); 
    btn1=(Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);  
    btn2=(Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
   btn3=(Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);   
   btn4=(Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);  
  btn5=(Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);      
    
   text0.setText("input your text to editBox and push bytton");   

   edText=(EditText)findViewById(R.id.edText);



    }  // \\ create


	@Override
	public void onClick(View v) {
		text0.setText("btn off");

		switch (v.getId()) {
		
			case R.id.btn0 :

			break;	
			case R.id.btn1 :
			  text0.setText("btn On");
			  String str = edText.getText().toString();
			  if (str != null && str != "" )
			                         btn2.setText(str);			
			break;		
	
			case R.id.btn2 :
			break;		
			case R.id.btn3 :
			break;		
			case R.id.btn4 :
			break;		
			case R.id.btn5 :
			break;		
              }
	}

/*
Подробнее см в 
https://startandroid.ru/ru/uroki/vse-uroki-spiskom/61-urok-24-activity-lifecycle-primer-smeny-sostojanij-s-dvumja-activity.html
и 
https://startandroid.ru/ru/uroki/vse-uroki-spiskom/60-urok-23-activity-lifecycle-v-kakih-sostojanijah-mozhet-byt-activity.html 
*/
 
 
 @Override
  protected void onStart() {
    super.onStart();
  //  Log.d(TAG, "MainActivity: onStart()");
  }

  @Override
  protected void onResume() {
    super.onResume();
  //  Log.d(TAG, "MainActivity: onResume()");
  }

  @Override
  protected void onPause() {
    super.onPause();
  //  Log.d(TAG, "MainActivity: onPause()");
  }

  @Override
  protected void onStop() {
    super.onStop();
  //  Log.d(TAG, "MainActivity: onStop()");
  }
    
  @Override
  protected void onDestroy() {
    super.onDestroy();
  //  Log.d(TAG, "MainActivity: onDestroy()");
  }



} //all
