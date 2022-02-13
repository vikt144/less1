package com.less;

import android.app.*;
import android.os.*;

import android.view.*; //View;
import android.view.View.*;  //OnClickListener
import android.view.KeyEvent.Callback ;

import android.widget.*;   
import android.util.*;
import android.net.*;

import android.content.*;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo; //  тут нужен для ориентации экрана

import java.util.*; 
import java.io.*;

public class MainActivity extends Activity implements OnClickListener {

  TextView text0 , text1;
   
  Button        btn0, btn1, btn2, btn3, btn4, btn5;

  
  EditText edText;

  Handler H , Hm;
 
  File direct ;  // директория с форт прогами

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


    final   TextView []  textArray = {text0 , text1};  //   оформление элементов gui в виде массива
    final   Button [] buttArray = { btn0, btn1, btn2, btn3, btn4, btn5};  // для Handler


// подробнее о handler -- 
// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/144-urok-81-handler-posylaem-prostoe-soobschenie.html 
// https://startandroid.ru/ru/uroki/vse-uroki-spiskom/145-urok-82-handler-primer-s-bolee-soderzhatelnymi-soobschenijami.html

    final int _txt_field_ = 0 , _txt_button_ = 1, _txt_edit_ = 2,
       	            _get_txt_  = 0, _set_txt_ = 1 , _set_Hint_ = 2, 
		 _screen_off_ = 100,
                 _on_  = 1 , _off_ = 0   ;
/*		;
подробнее о textview 
http://developer.alexanderklimov.ru/android/views/textview.php
*/
    H = new Handler() {
      public void handleMessage(android.os.Message msg) {

        switch (msg.what) {
          case _txt_field_ : textArray[msg.arg1] . setText( (String) msg.obj );
           break;
         case _txt_button_ : buttArray[msg.arg1] .  setText( (String) msg.obj );
          break;
         case _txt_edit_ :
	    switch (msg.arg2) {
	        case _get_txt_ :
	            String str = edText.getText().toString(); 
         ////    тут зависит рт реализации expect

                  break;
	        case _set_txt_  :  edText.setText( (String) msg.obj );
                  break;
	        case  _set_Hint_ : edText.setHint( (String) msg.obj );		  
                  break;		  
            }//sw 
          break;
	 }//sw
     }; 
  };// hndl


    Hm = new Handler() {
      public void handleMessage(android.os.Message msg) {
	
        switch (msg.what) {
          case 0 : text0.setText(" -" + msg.arg1+"  "+ (String) msg.obj  ); break;
          case 1 : text1.setText(" -" + msg.arg1+"  "+ (String) msg.obj  ); break;
	  }
	};
     };	  




   initForth() ;

   as.Hnd=H;  as.HndMes = Hm;

////////////////////////////////////////////////////////////   SharedPreferences\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

   String fileText0=null;  // начальная загрузка fort текстов

   SharedPreferences prf = getPreferences(MODE_PRIVATE);
   String dir = prf.getString("Direct", "");
   String sss=dir; text1.setText(dir);
   if (dir != "") {
                  direct = new File(dir);
                  if (  direct.exists() )  {
        	      btn1.setText("load");
                      text0.setText(dir);		      
		      File startFile = new File ( direct, "0" );
		      if ( startFile.exists() ) {
		            text0.setText(dir+"/0");   
                            String ff = direct.getAbsolutePath() +"/0";
			    fileText0 = as.loadTextFile( ff );

			     }

		      }
		      else   text0.setText(dir + " noexist");
		  }  else   text0.setText("pref -no exist");                   


    as._IN = 0;
    if (fileText0 != null) {  // считывание и исполнение 0-файла
     //  edText.setText(fileText0);
          as.TIB=fileText0;
	  try {
                 as.interpret();
		// if (as.error==0) text0.setText( as.ErrorString ); // так было
	          if (as.error!=0) text0.setText( as.ErrorString );
		} catch(Exception e)  {text0.setText("Крах системы " );}			
        }
   
   
    }  // //////////////////////////////////////////////////////////////\\ create


final int PICK_FOLDER_RESULT_CODE = 111;

final int REQUEST_SAVE =222;

void pickFolder(File aFolder) {
    Intent theIntent = new Intent(Intent.ACTION_PICK);
    theIntent.setData(Uri.parse("folder://"+aFolder.getPath()));  //default folder / jump directly to this folder
    theIntent.putExtra(Intent.EXTRA_TITLE,"A Custom Title"); //optional
    theIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); //optional
    try {
        startActivityForResult(theIntent,PICK_FOLDER_RESULT_CODE);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
		
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data){
    switch (requestCode) {
        case PICK_FOLDER_RESULT_CODE:  
            if (resultCode==RESULT_OK && data!=null && data.getData()!=null) {
                String theFolderPath = data.getData().getPath();
                text0.setText(theFolderPath);
             }
            break;
	case REQUEST_SAVE :    text1.setText("input yes");
	    break; 
        }
    
}



        long back_pressed=0; // последнее нажатие
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
			case R.id.btn0 :   // добавить  проверку корректности созданного пути.
                        btn4.setText("intp");
			long time = System.currentTimeMillis();
			if (back_pressed + 2000 > time ) {
			        back_pressed=0; 
			        String filepath = edText.getText().toString();
			        text0.setText( filepath  );
				SharedPreferences prf; 
                                prf = getPreferences(MODE_PRIVATE);    
			        Editor ed = prf.edit();
				ed.putString("Direct",filepath );
                                ed.commit(); 
				} 
                              else {		     
			          back_pressed=time;
				  text0.setText("double click this button for pref change" );  
 		                 }
                     //   File gogo = new File("lololossa");	   
		//	pickFolder(gogo); 
		/*
		 Intent intent = new Intent(getBaseContext(), FileDialog.class); 
		 intent.putExtra(FileDialog.START_PATH, "/sdcard");
                 intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_CREATE);
 startActivityForResult(intent, REQUEST_SAVE);
		*/
		
			break;	
			case R.id.btn1 :
			    String f = edText.getText().toString().trim();
			    String ff = direct.getAbsolutePath() +"/" + f;
			//    try {
			    String data = as.loadTextFile( ff  );
			    // edText
			    if (data != null)  edText.setText(data);
			     else text1.setText("not файла " +ff);		
			break;		
	
			case R.id.btn2 :
			break;		
			case R.id.btn3 :
			break;		

			case R.id.btn4 : 
			String gtext = edText.getText().toString();
			if ( ! gtext.equals("") )
				{
                                  try {
	                               as._IN = 0;
                                       as.TIB=null;	  
                                       as.TIB=gtext;
                                       as.error=0;   as.ErrorString=null;
                                       as.interpret();
				   //    text0.setText("intpr start goot");
	                              }  catch (Exception e) {String s = null;
	                                                   if (as.ErrorString==null) s="bobo " ; 
							       else s = as.ErrorString+" " ;   
	                                                    text0.setText(s + e );
	                                                   }
	                } //fi
			break;		
			case R.id.btn5 :
			String s = null; 
			if (as.ErrorString==null) s = " no " ; else s = as.ErrorString;
			text0.setText("vector= "+as.StringVector.size()   + " array=" + as.VM. procedureIndex + s) ;
			
			break;		
              }
	}


  fas  as ;
//  fas.STACK ST;
//  fas.STACK RST;
//  fas.FVM VM;

  public void  initForth() {
      as = new fas(); 

      fas.STACK ST =  as. new STACK();
      int[] stackarray=new int[100];
      ST.stack=stackarray;//new int[100]; //stackarray;
      as.ST=ST;	

      fas.STACK RST =  as. new STACK();
      int[] ret_stackarray=new int[30];
      RST.stack=ret_stackarray;//new int[100]; //stackarray;


     as.mem = as.createMemory(null,100000);
     if (as.mem==null) sysmes("memo  error"); 
        else sysmes("memoyes");	

      as.here    = 2; //  адреса в памяти, где хранятся эти  переменные
      as.latest  =6;
      as.state   =10;      
      as.context =  15;
      as.current =  20;
      as.forthVoc=  25; 

      as.mem.putInt(as. here,45);//  as.  memory[as.here]=6; //взято от балды
      as.mem.putInt(as. latest,0);//      as.  memory[as.latest]=0; // записи в словари еще не создавались
      as.mem.putInt(as. state,0);//     as.  memory[as.state] =0; // 0 на данный момент исполнение     

      Vector V=null;               // хранилище строк
      V = as.initVirtualMem(0);
      as.StringVector=V;

     int currentHere = as.newDict("FORTH",0,(byte)0);


    as.mem.putInt(as. context, currentHere);// as.latest);   // для работы create -- начальное должно указывать на  0
    as.mem.putInt(as. current, currentHere);//as.latest);  // см   void cre0(String s)
    as.mem.putInt(as.forthVoc, currentHere);    

    as.initFVMwords();

      fas.FVM VM = as. new FVM();
      VM.stack=as.ST;
      VM.adrStack=RST;
      int[] ports = new int[20]; 
      VM.ports=ports; 
      as.VM=VM;     

     double[] FL = new double[30]; 
    as.ST.fstack=FL;
 
 as.cre1();  // создается  определение для exit
 
 as.exit_addr=as.mem.getInt(as.here);// as.memory[as.here];//сохранить точску входа в exit,чтобы положить на стек возвратов  
 as.setout_(0,0,false); 

  as.ST.push(2);  as.setCFA();

// определяется "." 
 as.cre1(); 
 as.setout_(1,0,true);
  as.ST.push(2);  as.setCFA();


  as.VM.Ob=as;             // object for reflections api
  as.initEXTwords_2(as);

   as.interpret();
//   if (as.ErrorString!=null) text0.setText(" huynia i r kaka "); else text0.setText("goot");
  }

public void sysmes(String str) {text0.setText(str);}



       public int startInterpret(String tib, int in, String title) {  
          if (title != null)
	        text0.setText(title);
	  int ret=0;
	  as._IN = in;
          as.TIB=null;	  
          as.TIB=tib;
          as.error=0; as.ErrorString=null;
          as.interpret();
          return ret;
       }



/////////////////////////////////////////////////////////////////menu//////////////////////////////////
// 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      menu.add("clr");      
      menu.add("http");
      menu.add("map");
      menu.add("prf");      //настройки
//      menu.add("gpson");   //настройкии gps
//      menu.add("all_prf");//главэкран
//      menu.add("file");   //     
//      menu.add("fileSam");   //
//      menu.add("tel");   //
      return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
	String error="функция несовместима с устройством";
	try {
                                      //  Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        String menu = item.getTitle().toString();

	if (menu.equals("clr") ) {
	    startInterpret( " clr " , 0 ,"startforth"); 
	   }
	
	if (menu.equals("http") ) {
	   intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://fforum.winglion.ru"));
           startActivity(intent);
	    }
	 else
	 if (menu.equals("map") ) {	    
           intent = new Intent();
           intent.setAction(Intent.ACTION_VIEW);
           intent.setData(Uri.parse("geo:55.754283,37.62002"));
           startActivity(intent);         
	  }
	 else 
	 if (menu.equals("prf") ) {
               startActivity(new Intent(  android.provider.Settings.ACTION_DATA_USAGE_SETTINGS ));
	  }     	
	 //https://developer.android.com/reference/android/provider/Settings#ACTION_DATA_USAGE_SETTINGS  
	 //http://developer.alexanderklimov.ru/android/theory/intent.php

	 } catch(Exception e) { text0.setText(error); } 	     
      return super.onOptionsItemSelected(item);
    }


//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\menu end\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
 
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
