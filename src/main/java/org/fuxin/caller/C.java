package org.fuxin.caller;

public class C {
	//移动标准文件位置
	public static String ydkhfile = "c:\\standnew\\ydkh_ear.wav";
	//public static String ydkhfile2 = "c:\\standnew\\ydkh2_ear.wav";
	public static String ydtjfile = "c:\\standnew\\ydtj_ear.wav";
	public static String ydgjfile = "c:\\standnew\\ydgj_ear.wav";
	public static String ydgjfile2 = "c:\\standnew\\ydgj2_ear.wav";
	public static String ydgjfile3 = "c:\\standnew\\ydgj3_ear.wav";
	
	
	
	//联通标准文件位置
	public static String ltkhfile = "c:\\standnew\\ltkh_ear.wav";
	public static String ltkhfile2 = "c:\\standnew\\ltkh2_ear.wav";
	public static String lttjfile = "c:\\standnew\\lttj_ear.wav";
	public static String lttjfile2 = "c:\\standnew\\lttj2_ear.wav";
	public static String ltgjfile = "c:\\standnew\\ltgj_ear.wav";
	public static String ltkhfile3 = "c:\\standnew\\ltkh3_ear.wav";
	public static String ltgjfile2 = "c:\\standnew\\ltgj2_ear.wav";
	
	//电信标准文件位置
	public static String dxkhfile = "c:\\standnew\\dxkh_ear.wav";
	public static String dxkhfile2 = "c:\\standnew\\dxkh2_ear.wav";
	public static String dxtjfile = "c:\\standnew\\dxtj_ear.wav";
	public static String dxgjfile = "c:\\standnew\\dxgj_ear.wav";
	public static String dxgjfile2= "c:\\standnew\\dxgj2_ear.wav";
	
	
	
	//判断少于多少是噪音
	public static int smallnoise=60;
	//WAV文件最大值ֵ
	public static int maxwave = 32768;
	
	//移动的号段
	public static String[] ydprefix={"134","135","136","137",
			"138","139","150","151","152",
			"157","158","159","182","183",
			"184","187","178","188","147"};
	//联通的号段
	public static String[] ltprefix={"130","131","132","145",
			"155","156","176","185","186"};

	//电信的号段
	public static String[] dxprefix={"133","153","177","180","181","189"};

	
	
	public enum Operator
	{
		Unknown,Yd,Lt,Dx
	}

    public enum Type
    {
//		Zc,Kh,Tj,Gj,Unknown,Undo

        Zc(1, "正常")
        ,Tj(2, "停机")
        ,Kh(3, "空号")
        ,Gj(4, "关机")
        ,Unknown(0, "未知")
        ,Undo(-1, "尚未处理，需要重新拨打");

        private Integer code;
        private String simpleName;
        private Type(Integer code, String simpleName)
        {
            this.code = code;
            this.simpleName = simpleName;
        }

        public Integer getCode() {
            return code;
        }

        public String getSimpleName() {
            return simpleName;
        }
    }
	
}
