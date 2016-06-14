package JNI;

public class Keypad{
	private int key;
	
	public native int Open();
	public native int Close();
	public native int GetValue();
	
	static {
		System.loadLibrary("keytest");
	}

	public Keypad(){
		int res=Open();
	}
	public int Update(){
		key=GetValue();
		return key;
	}
	
}