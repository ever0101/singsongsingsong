package JNI;

public class TextLcd {
	public native int TextLCDOut(String str, String str2); 
    public native int IOCtlClear();
    public native int IOCtlReturnHome();
    public native int IOCtlDisplay(boolean bOn);
    public native int IOCtlCursor(boolean bOn);
    public native int IOCtlBlink(boolean bOn);
    boolean disp,cursor,blink;
    int ret;
    String str1,str2;
	static {
		System.loadLibrary("textlcd");
	}
	
	public TextLcd(){
		disp = true; cursor = false; blink = false;
        IOCtlClear();
        IOCtlReturnHome(); 
		IOCtlDisplay(disp); 
		IOCtlCursor(cursor); 
		IOCtlBlink(blink); 
        
		ret = TextLCDOut("title", "singer");
	}
	
	public void setStr(String a,String b){
		str1=a;
		str2=b;
		TextLCDOut(str1, str2);
	}
}
