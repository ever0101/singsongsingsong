package com.sample;

import java.util.ArrayList;

import JNI.Keypad;
import JNI.TextLcd;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class SampleActivity extends Activity {
    /** Called when the activity is first created. */
	

	private String name;
	private TextLcd tl;
	private Intent i;
	private Intent j;
	private ArrayList<String> array_items;
	private ArrayList<String> array_items2;
	private AutoCompleteTextView edit;
	
	private boolean isPlaying=true;
	private int ab;
	private Keypad keypad;
	private Thread thread;
	
	String[] items={"touch my body", "여름아 부탁해", "인생은 아름다워","under the sea",
			"cheer up", "bubble pop", "고속도로 로망스", "한 여름 밤의 꿀", "팥빙수", "여름 안에서"};

	Handler handler=new Handler(){
		//키패드의 값을 읽어오고 처리하기 위해 사용되는 핸들러
		public void handleMessage(Message m){
			if(m.what==28){
				//1행 4번째 버튼의 노래 예약 기능
				name=edit.getText().toString();
				array_items.add(name);
				array_items2.add(name);
				i.putStringArrayListExtra("get", array_items);
				edit.setText("");
			}
			else if(m.what==29){
				//2행 4번째 버튼의 노래 시작 기능
				if(array_items2.size()==0)
					Toast.makeText(SampleActivity.this, "예약을 먼저 하세요", Toast.LENGTH_SHORT).show();
				else{
					isPlaying=false;
					ab=40;
					j.putStringArrayListExtra("list", array_items2);
					startActivity(j);
				}
			}
		}
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tl=new TextLcd();
        tl.setStr("title", "singer");
        keypad=new Keypad();
        Thread();
        
        edit=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);

        array_items=new ArrayList<String>();
        array_items2=new ArrayList<String>();

        edit.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edit.showDropDown();
			}
		});
        
        Button start_button=(Button)findViewById(R.id.start_button);
        Button book_button=(Button)findViewById(R.id.reservation);
        Button check_book=(Button)findViewById(R.id.bookbook);
        
        
        i=new Intent(SampleActivity.this, CheckBook.class);
        j=new Intent(SampleActivity.this, SongPlayer.class);
        
        
        edit.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));
        edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				name=edit.getText().toString();
			}
        	
		});
        
        if(ab==3){
        	startActivity(i);
        }
        
        start_button.setOnClickListener(new View.OnClickListener() {
			//노래 시작 버튼
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(array_items2.size()==0)
					Toast.makeText(SampleActivity.this, "예약을 먼저 하세요", Toast.LENGTH_SHORT).show();
				else{
					//ArrayList의 노래를 SongPlayer 클래스로 넘겨줌
					j.putStringArrayListExtra("list", array_items2);
					startActivity(j);
				}
				
			}
		});
        
        book_button.setOnClickListener(new View.OnClickListener() {
			//노래 예약 버튼
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=edit.getText().toString();
				array_items.add(name);
				array_items2.add(name);
				//ArrayList의 노래를 CheckBook 클래스로 넘겨줌
				i.putStringArrayListExtra("get", array_items);
				edit.setText("");
			}
		});
        check_book.setOnClickListener(new View.OnClickListener() {
			//예약 된 플레이리스트 확인 버튼
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(array_items.size()!=0)
					startActivity(i);
			}
		});
    
      
    }
    
    public void Thread(){

           thread=new Thread(new Runnable(){
        	   public void run(){
                while(isPlaying){
                   /* try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                    ab=keypad.Update();
                	Message message=Message.obtain();
                	message.what=ab;
                	handler.sendMessage(message);
                }
            }
        });

        thread.start();
    
    }
}
