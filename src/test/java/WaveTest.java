import com.web.task.WaveIdentifyUtil;
import org.fuxin.caller.C;
import org.fuxin.caller.WaveFileResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//        TelePhone phone = new TelePhone(mobile);
//        phone.identifyWave(urlstr);
//
//       log.info(""+phone.getStatus().getSimpleName());
    }
}
