package com.sample;

public class SongChoice {
	
	private Song current;
	private String name;
	
	
	
	public SongChoice(String name) {
		super();
		this.name = name;
	}



	public Song choice(){
		current=new Song(name);
		
		if(name.equals("bubble pop")){
			current.setSinger_name("Don't know");
			current.c=R.raw.bubblepop;
		}
		else if(name.equals("touch my body")){
			current.setSinger_name("씨스타(Sistar)");
			current.c=R.raw.touch_my_body;
		}
		else if(name.equals("여름아 부탁해")){
			current.setSinger_name("쿨(Cool)");
			current.c=R.raw.summerplease;
		}
		else if(name.equals("인생은 아름다워")){
			current.setSinger_name("토이(Toy)");
			current.c=R.raw.lifeisbeautiful;
		}
		else if(name.equals("under the sea")){
			current.setSinger_name("인어공주");
			current.c=R.raw.underthesea;
		}
		else if(name.equals("cheer up")){
			current.setSinger_name("트와이스(twice)");
			current.c=R.raw.cheerup;
		}
		else if(name.equals("한 여름 밤의 꿀")){
			current.setSinger_name("San E");
			current.c=R.raw.honeyofsummer;
		}
		else if(name.equals("고속도로 로망스")){
			current.setSinger_name("윤종신");
			current.c=R.raw.highwayromance;
		}
		else if(name.equals("팥빙수")){
			current.setSinger_name("윤종신");
			current.c=R.raw.iceredbean;
		}
		else if(name.equals("여름 안에서")){
			current.setSinger_name("Deuce");
			current.c=R.raw.insummer;
		}
		return current;
	}
}
