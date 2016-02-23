import com.web.task.WaveIdentifyUtil;
import org.fuxin.caller.C;
import org.fuxin.caller.WaveFileFilter;
import org.fuxin.caller.WaveFileResult;
import org.fuxin.util.AudioPlay;
import org.fuxin.util.FuOutput;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boshu on 2016/2/4.
 */
public class WaveTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test2(){
        WaveIdentifyUtil.setWaveSampleResourceDir("C:\\tools\\standard\\");
        String res = C.ydgjfile.substring(C.ydgjfile.lastIndexOf("\\")+1);

        log.info("测试 path   "+ WaveIdentifyUtil.getSamplePath(res));
    }

    @Test
    public void runtest(){
        WaveIdentifyUtil.setWaveSampleResourceDir("C:\\standnew\\");

        String urlstr = "http://monitor.taiyuedata.com:80/recognitionadmin/api.file.get?filename=18004600403_201602201646107284.wav";
//        String urlstr = "http://monitor.taiyuedata.com:80/recognitionadmin/api.file.get?filename=18004531565_201602201645582734.wav";
        String mobile = "";
        Pattern p = Pattern.compile("filename=(.*)_");
        Matcher m = p.matcher(urlstr);
        if (m.find()){
            mobile = m.group(1);
        }
        log.info("手机号 mobile:"+mobile);
        try {
            WaveFileResult wfr = WaveIdentifyUtil.indentify(mobile, urlstr);
            log.info(""+wfr.getType().getCode()+"   "+wfr.getType().getSimpleName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void runtest2(){
        WaveIdentifyUtil.setWaveSampleResourceDir("C:\\standnew\\");
        log.info("PhoneFilter Starting...");
        long start = System.currentTimeMillis();

        String path="c:\\sound20\\lt20";
        String prefix="1";

        ArrayList<WaveFileResult> resultlist= WaveFileFilter.CompareAllinPath(path,prefix);
        log.info("共"+resultlist.size()+"个电话");
        FuOutput.writeToFile(resultlist, "list");



        long end = System.currentTimeMillis();
        log.info((end-start)+"ms");
        new AudioPlay("e:\\work\\alarm07.wav");
    }
}
