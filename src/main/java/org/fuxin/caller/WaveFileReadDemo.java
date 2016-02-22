package org.fuxin.caller;

import java.util.ArrayList;

import org.fuxin.util.AudioPlay;
import org.fuxin.util.FuOutput;

public class WaveFileReadDemo {
	public static void main(String[] args) {  
		FuOutput.sop("PhoneFilter Starting...");
		long start = System.currentTimeMillis();
		
		String path="c:\\sound20\\lt20";
		String prefix="1";
		//WaveFileReader w1=new WaveFileReader("C:\\sound20\\dxgtk\\18012590228_201602201641326597.wav");
		//FuOutput.sop(w1);
		
		ArrayList<WaveFileResult> resultlist=WaveFileFilter.CompareAllinPath(path,prefix);
		FuOutput.sop("共"+resultlist.size()+"个电话");
		FuOutput.writeToFile(resultlist, "list");
        
        long end = System.currentTimeMillis();
        FuOutput.sop((end-start)+"ms");
        new AudioPlay("e:\\work\\alarm07.wav");
    }

	

	
	
	
}
