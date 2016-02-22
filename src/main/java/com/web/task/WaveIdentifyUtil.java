package com.web.task;

/**
 * Created by boshu on 2016/2/21.
 */

import com.common.exception.WException;
import com.common.util.IopUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.fuxin.caller.C;
import org.fuxin.caller.ClassifyWave;
import org.fuxin.caller.StandFile;
import org.fuxin.caller.WaveFileResult;
import org.fuxin.util.WaveFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boshu on 2016/2/4.
 */
public class WaveIdentifyUtil {
    private static Logger log = LoggerFactory.getLogger(WaveIdentifyUtil.class);

    private static ArrayList<StandFile> stanlist = new ArrayList<>();

    private static String standfolder = "";

    public static String getSamplePath(String path){
        if (path.lastIndexOf("\\")!=-1){
            path = path.substring(path.lastIndexOf("\\")+1);
        }
        return standfolder+path;
    }

    public static WaveFileResult indentify(String mobile, File file){
        if (stanlist.size()==0){

            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ydkhfile)),4200, C.Operator.Yd, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ydtjfile)), 3600, C.Operator.Yd, C.Type.Tj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ydgjfile)), 3000, C.Operator.Yd, C.Type.Gj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ydgjfile2)), 3000, C.Operator.Yd, C.Type.Gj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ydgjfile3)), 2500, C.Operator.Yd, C.Type.Gj));
            //电信
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.dxkhfile)),3000, C.Operator.Dx, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.dxtjfile)), 3100, C.Operator.Dx, C.Type.Tj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.dxkhfile2)),3000, C.Operator.Dx, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.dxgjfile)), 2500, C.Operator.Dx, C.Type.Gj));
            //联通
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ltkhfile)),2000, C.Operator.Lt, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ltkhfile2)),1500, C.Operator.Lt, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ltkhfile3)),2600, C.Operator.Lt, C.Type.Kh));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.lttjfile)), 1500, C.Operator.Lt, C.Type.Tj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.lttjfile2)), 1500, C.Operator.Lt, C.Type.Tj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ltgjfile)), 2400, C.Operator.Lt, C.Type.Gj));
            stanlist.add(new StandFile(new WaveFileReader(getSamplePath(C.ltgjfile2)), 1600, C.Operator.Lt, C.Type.Gj));
        }

        //对文件进行分类
        ClassifyWave cw= new ClassifyWave();
        WaveFileResult wfr = cw.Filter(mobile, file, stanlist);

        return wfr;
    }

    public static WaveFileResult indentify(String mobile, String urlstr) throws Exception{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet post = new HttpGet(urlstr);

        HttpResponse httpresponse = client.execute(post);
        HttpEntity resEntity = httpresponse.getEntity();
        byte[] message = EntityUtils.toByteArray(resEntity);
        File file = File.createTempFile(mobile, "wav");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(message);
        fos.flush();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("start "+sdf.format(new Date()));
        WaveFileResult waveFileResult = indentify(mobile, file);
        log.info("end "+sdf.format(new Date()));
        return waveFileResult;
    }

    public static void setWaveSampleResourceDir(String dir){
        standfolder = dir;
    }
}