import com.web.task.RecogTaskService;
import com.web.task.WaveIdentifyUtil;
import org.fuxin.caller.C;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by boshu on 2016/1/2.
 */
public class BankTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        try {
            File file = new File("C:\\Users\\boshu\\Downloads\\13061603533_201602071728204338.wav");

            log.info(""+file.length());

//            String page = IOUtils.toString(new FileInputStream(file));
//            Document document = Jsoup.parse(page);
//            Element realForm = document.select("div.info-show ").first();
//            if (realForm != null) {
//                String name = realForm.select(".item").get(1).html().replace("<label>账户户名：</label>", "");
//                String cardno = realForm.select(".item").get(2).html().replace("<label>账户账号：</label>", "");
//                log.info("name:"+name +"\n cardno:"+cardno);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2(){
        WaveIdentifyUtil.setWaveSampleResourceDir("C:\\tools\\standard\\");
        log.info("测试 path   "+ WaveIdentifyUtil.getSamplePath(C.ydgjfile));
    }

}
