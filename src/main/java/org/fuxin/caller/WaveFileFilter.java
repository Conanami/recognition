package org.fuxin.caller;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.web.task.WaveIdentifyUtil;
import org.fuxin.caller.C.Operator;
import org.fuxin.caller.C.Type;
import org.fuxin.util.WaveFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 这是个如何使用接口
 * IClassifyWave
 * 的示例类
 * @author Administrator
 *
 */
public class WaveFileFilter {
	private static Logger log = LoggerFactory.getLogger(WaveFileFilter.class);
	/***
	 * 处理目录下所有的文件
	 * @param path
	 * @param prefix
	 * @return 
	 */
	public static ArrayList<WaveFileResult> CompareAllinPath(String path, String prefix) {

		File[] tempList = new File(path).listFiles();
		ArrayList<WaveFileResult> resultlist = new ArrayList<WaveFileResult>();

		ArrayList<StandFile> stanlist = new ArrayList<StandFile>();


		//上面的都是要重新做的
		
		stanlist.add(new StandFile(new WaveFileReader(C.ydkhfile),4200,Operator.Yd,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.ydtjfile), 3600,Operator.Yd,Type.Tj));
		stanlist.add(new StandFile(new WaveFileReader(C.ydgjfile), 3000,Operator.Yd,Type.Gj));
		stanlist.add(new StandFile(new WaveFileReader(C.ydgjfile2), 3000,Operator.Yd,Type.Gj));
		stanlist.add(new StandFile(new WaveFileReader(C.ydgjfile3), 2500,Operator.Yd,Type.Gj));
		//电信
		stanlist.add(new StandFile(new WaveFileReader(C.dxkhfile),3000,Operator.Dx,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.dxtjfile), 3100,Operator.Dx,Type.Tj));
		stanlist.add(new StandFile(new WaveFileReader(C.dxkhfile2),3000,Operator.Dx,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.dxgjfile), 2500,Operator.Dx,Type.Gj));
		//联通
		stanlist.add(new StandFile(new WaveFileReader(C.ltkhfile),2000,Operator.Lt,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.ltkhfile2),1500,Operator.Lt,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.ltkhfile3),2600,Operator.Lt,Type.Kh));
		stanlist.add(new StandFile(new WaveFileReader(C.lttjfile), 1500,Operator.Lt,Type.Tj));
		stanlist.add(new StandFile(new WaveFileReader(C.lttjfile2), 1500,Operator.Lt,Type.Tj));
		stanlist.add(new StandFile(new WaveFileReader(C.ltgjfile), 2400,Operator.Lt,Type.Gj));
		stanlist.add(new StandFile(new WaveFileReader(C.ltgjfile2), 1600,Operator.Lt,Type.Gj));
		
		
		for (int i = 0; i < tempList.length; i++) {
		   if (tempList[i].isFile() 
				   && tempList[i].getName().endsWith(".wav")
				   && tempList[i].getName().startsWith(prefix)) {
			   
			   
			   //对文件进行分类
			   //获得手机号码
			   String mobile = tempList[i].getName().substring(0,11);

			   log.info("手机号 mobile:"+mobile);
			   try {
				   WaveFileResult wfr = WaveIdentifyUtil.indentify(mobile, tempList[i]);
				   log.info(""+wfr.getType().getCode()+"   "+wfr.getType().getSimpleName());
			   }catch (Exception e){
				   e.printStackTrace();
			   }
		   }
		   if (tempList[i].isDirectory()) {
			   
		   }
		}
		return resultlist;
	}
	
	
}
