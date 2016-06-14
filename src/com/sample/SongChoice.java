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
			current.setSinger_name("����Ÿ(Sistar)");
			current.c=R.raw.touch_my_body;
		}
		else if(name.equals("������ ��Ź��")){
			current.setSinger_name("��(Cool)");
			current.c=R.raw.summerplease;
		}
		else if(name.equals("�λ��� �Ƹ��ٿ�")){
			current.setSinger_name("����(Toy)");
			current.c=R.raw.lifeisbeautiful;
		}
		else if(name.equals("under the sea")){
			current.setSinger_name("�ξ����");
			current.c=R.raw.underthesea;
		}
		else if(name.equals("cheer up")){
			current.setSinger_name("Ʈ���̽�(twice)");
			current.c=R.raw.cheerup;
		}
		else if(name.equals("�� ���� ���� ��")){
			current.setSinger_name("San E");
			current.c=R.raw.honeyofsummer;
		}
		else if(name.equals("��ӵ��� �θ���")){
			current.setSinger_name("������");
			current.c=R.raw.highwayromance;
		}
		else if(name.equals("�Ϻ���")){
			current.setSinger_name("������");
			current.c=R.raw.iceredbean;
		}
		else if(name.equals("���� �ȿ���")){
			current.setSinger_name("Deuce");
			current.c=R.raw.insummer;
		}
		return current;
	}
}
