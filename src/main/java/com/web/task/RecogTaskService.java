package com.web.task;

import com.common.exception.WException;
import com.common.util.IopUtils;
import mybatis.one.mapper.DBImgZBMapper;
import mybatis.one.mapper.DBRecogsMapper;
import mybatis.one.po.DBImgZB;
import mybatis.one.po.DBImgZBExample;
import mybatis.one.po.DBRecogs;
import mybatis.one.po.DBRecogsExample;
import org.fuxin.caller.WaveFileResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boshu on 2016/2/4.
 */
@Service
public class RecogTaskService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String imageserverUrl;

    private String sampleFolder = "";    //样本目录

    @Resource
    DBRecogsMapper recogsMapper;

    @Resource
    DBImgZBMapper imgZBMapper;

    /**
     * 语音识别
     */
    @Scheduled(fixedRate = 20*1000)   //间隔单位毫秒
    public void runIndentify(){

        log.info("recog start");
        Properties properties =  new Properties();
        try {
            properties.load(new ClassPathResource("config.properties").getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        sampleFolder = properties.getProperty("wave.sample.resource.folder");
        imageserverUrl = properties.getProperty("imageserver.url");

        WaveIdentifyUtil.setWaveSampleResourceDir(sampleFolder);

        List<DBRecogs> dbRecogsList = queryLast();

        for (int i=0;i<dbRecogsList.size();i++){
            DBRecogs dbRecogs = dbRecogsList.get(i);

            //查找文件
            String filename = "";
            Pattern p = Pattern.compile("filename=(.*).wav");
            Matcher m = p.matcher(dbRecogs.getDataurl());
            if (m.find()){
                filename = m.group(1);
            }
            if (IopUtils.isEmpty(filename)){
                log.info("文件在服务器上没找到");
                break;
            }

            DBImgZB imgZB = imgZBMapper.selectByPrimaryKey(filename);
            File file = readFile(imgZB.getGroupname(), imgZB.getMerchid(), imgZB.getBatchid(), imgZB.getImagename(), imgZB.getSuffix());

            if (file.canRead()){
                log.info("indentify start "+dbRecogs.getMobile());
                WaveFileResult wfr = WaveIdentifyUtil.indentify(dbRecogs.getMobile(), file);
                log.info("indentify end");
                dbRecogs.setStatus(4);//4 表示 已经识别
                dbRecogs.setResult(wfr.getType().getCode());
                dbRecogs.setRecogtime(new Date());
                recogsMapper.updateByPrimaryKey(dbRecogs);
            }else{
                dbRecogs.setStatus(5);//5 表示 录音文件丢失
                recogsMapper.updateByPrimaryKey(dbRecogs);
                log.info("文件:"+dbRecogs.getMobile()+" 找不到");
            }
        }

        log.info("recog end");
    }

    /**
     * 查找已经拨打的电话记录，取20个
     * @return
     */
    public List<DBRecogs> queryLast(){
        DBRecogsExample example = new DBRecogsExample();
        example.createCriteria().andStatusEqualTo(3);//已经拨打的电话记录
        example.setOrderByClause("seqid asc");
        List<DBRecogs> dbRecogsList = recogsMapper.selectByExample(example);
        if (dbRecogsList.size()>20){
            return dbRecogsList.subList(0,20);
        }
        return dbRecogsList;
    }

    File readFile(String project, String merchid, String batchid, String imagename, String suffix) {
        String imagePath = imageserverUrl+project+ File.separator+merchid+File.separator+batchid+File.separator+imagename+"."+suffix;
        if (IopUtils.isEmpty(batchid) || IopUtils.isEmpty(merchid)){
            imagePath = imageserverUrl+project+ File.separator+imagename+"."+suffix;
        }
        File file = new File(imagePath);
        return file;
    }
}
