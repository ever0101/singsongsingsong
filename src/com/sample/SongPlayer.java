package com.sample;

import java.util.ArrayList;

import JNI.TextLcd;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SongPlayer extends Activity implements SensorEventListener{
	private MediaPlayer mp;
	public static Song current;
	
	SongChoice sc;
	TextLcd tl;
	
	private int position=0;
	
	private int vol1=0,vol2=0;
	private int temp1=0,temp2=0;
	
	private int list_length;
	
	private TextView tx=null;
	private TextView ty=null;
	private TextView tz=null;
	
	private TextView singer;
	private Thread thread;
	
	private SensorManager mSensorManager;  
	private Sensor mSensor; 
	private Sensor accSensor;
	private ArrayList<String> list;
	private SeekBar seekBar;
	private ListView listview;
	private ArrayAdapter<String> adapter;
	private AudioManager mAudioManager;

	//SensorEvent event;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_player);

		tl=new TextLcd();
		
		tx=(TextView)findViewById(R.id.tvAX);
		ty=(TextView)findViewById(R.id.tvAY);
		tz=(TextView)findViewById(R.id.tvAZ);
		
		singer=(TextView)findViewById(R.id.changesingername);
		 
		mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		accSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		
	    list=new ArrayList<String>();
		list=getIntent().getStringArrayListExtra("list");
		
		adapter=new ArrayAdapter(this, R.layout.simpleitem, list);
        
	    listview=(ListView)findViewById(R.id.playerlist);
	    listview.setAdapter(adapter);
	 	seekBar = (SeekBar)findViewById(R.id.seekBar1);
	    list_length=list.size(); 
	   
	    nextSong();
	 	
		Button b1=(Button)findViewById(R.id.button1);
		Button b2=(Button)findViewById(R.id.button2);
		listview.setOnItemClickListener(listener);

		b1.setOnClickListener(new View.OnClickListener() {
			//노래 재생 버튼
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				stopSong();
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			//노래 일시정지 버튼
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSong();
			}

			
		});
	}
	OnItemClickListener listener=new OnItemClickListener() {
		//리스트의 목록 클릭 시에 해당 노래 재생
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			stopSong();
			clickSong(position);
		}
	};
	public void clickSong(int position){
		//리스트의 노래를 클릭했을 때 실행되는 메소드
		for(int i=0;i<position;i++){
			list.remove(0);
			adapter.notifyDataSetChanged();
		}
		list_length=list.size();
		sc=new SongChoice(list.get(0));
		current=sc.choice();
		mp=new MediaPlayer();
		mp=MediaPlayer.create(getBaseContext(), current.getC());
		tl.setStr(current.getName(), current.getSinger_name());
		seekBar.setProgress(0);
        seekBar.setMax(mp.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser) {
                    mp.seekTo(progress);
                }
			}
		});
        singer.setText(current.getSinger_name());
        playSong();
	}


	public void nextSong(){
		//리스트의 다음 노래를 재생시키기 위해 실행되는 메소드
		sc=new SongChoice(list.get(position));
		current=sc.choice();
		mp=new MediaPlayer();
		mp=MediaPlayer.create(getBaseContext(), current.getC());
		tl.setStr(current.getName(), current.getSinger_name());
		seekBar.setProgress(0);
        seekBar.setMax(mp.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser) {
                    mp.seekTo(progress);
                }
			}
		});
        singer.setText(current.getSinger_name());
        playSong();
	}

	public void playSong(){
		//노래를 재생시키기 위한 메소드
        startSong();
  
        mp.setOnCompletionListener(new OnCompletionListener() {
			//노래가 끝났을 때 호출되는 메소드- 끝난 노래를 리스트에서 삭제하고 nextSong메소드를 호출하거나 이전 액티비티 실행
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				list.remove(0);
				adapter.notifyDataSetChanged();
				list_length--;
				mp.pause();
				if(list_length==0){
					Intent intent = new Intent(SongPlayer.this, SampleActivity.class);

					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent); 

					finish();
					return;
				}
				nextSong();
			}
		});
	}
	
	public void Thread(){
		//사용자가 SeekBar를 조작했을 때 노래가 위치에 맞게 재생되도록 하는 메소드
        Runnable task = new Runnable(){

            public void run(){
                while(mp.isPlaying()){
                   seekBar.setProgress(mp.getCurrentPosition());
                }
            }
        };
        thread = new Thread(task);
        thread.start();
    }
	
	protected void onResume() {  
    	super.onResume();  
    	mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    	mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

	protected void onPause() {  
    	super.onPause(); 
    	mSensorManager.unregisterListener(this);
    	mSensorManager.unregisterListener(this);
    	mp.pause();
    }
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//FLAG_ACTIVITY_NO_HISTORY : 이전 액티비티의 기록을 남기지 않기 위한 FLAG(재생된 노래는 예약리스트에서 삭제)
		switch(keyCode){

		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(SongPlayer.this, SampleActivity.class);

			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent); 

			finish();
		break;

		}

		return true;

	}
	public void onAccuracyChanged(Sensor sensor, int accuracy){
    	
    }
    public void onSensorChanged(SensorEvent event){
    	//Sensor의 값을 읽어오기 위한 메소드 
    	//Accelerometer Sensor에 의해 볼륨 조절
    	//Proximity Sensor에 의해 시작과 정지
    	switch(event.sensor.getType()){
    	case Sensor.TYPE_ACCELEROMETER:
    		vol1=(int) event.values[1];
    		vol2=(int) event.values[1];
    		if((vol1<temp1) &&(vol1<0)){
    			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    		}
    		else if((vol2>temp2) && (vol2>0)){
    			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    		}
    		temp1=vol1;
    		temp2=vol2;
    		tx.setText(String.valueOf(event.values[0]));
    		ty.setText(String.valueOf(event.values[1]));
    		tz.setText(String.valueOf(event.values[2]));
    		
    		break;
    	case Sensor.TYPE_PROXIMITY:
    		if(event.values[0]==0){
    			if(!mp.isPlaying())
    				startSong();
    			else
    				stopSong();
    		}	
    		break;
    		
    	}
    	
    	
    	
    	
    		
    }
	public Song getCurrent() {
		return current;
	}

	public static void setCurrent(Song current) {
		SongPlayer.current = current;
	}

	public void startSong() {
		// TODO Auto-generated method stub
		mp.start();
		Thread();
	}


	public void stopSong() {
		// TODO Auto-generated method stub
		mp.pause();
	}	
	

    protected void killplayer(){
    	//MediaPlayer를 종료할 때 실행시키기 위한 메소드 
    	if(mp!=null){
    		mp.release();
    		mp=null;
    	}
    	thread.stop();
    }
    
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		killplayer();
	}

	protected void onDestroy(){
    	killplayer();
    	super.onDestroy();
    	
    }
}
