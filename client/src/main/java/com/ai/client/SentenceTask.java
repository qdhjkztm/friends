package com.ai.client;

import com.ai.center.model.Address;
import com.ai.center.service.AddressService;
import com.ai.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Component
public class SentenceTask{
    @Autowired
    AddressService addressService;
    private boolean sentenceFlag=false;
    @Scheduled(fixedRate = 1000)
    public void taskParseAddress() {
        if(sentenceFlag){
            System.out.println("==============================忙==============================");
        }else{
            this.parseAddress();
        }
    }
    public void insertAddress(String content){
        try{
            synchronized (this) {
                Address address = addressService.findByContent(content);
                if (address == null) {
                    if (!this.isOtherAddress(content)) {
                        address = new Address();
                        address.setContent(content);
                        address.setCategory(0);
                        address.setVisited(0);
                        addressService.insert(address);
                    }
                }
            }
        }catch(Exception e){
            log.info("==================可能是多线程问题开始==================");
            log.info(e.toString());
            log.info("问题Address："+content);
            log.info("==================可能是多线程问题结束==================");
        }

    }
    public boolean isOtherAddress(String path) {
        boolean result = false;
        String regex = "[^\\s]+\\.(jpg|gif|png|bmp|swf|jpeg|js|css|exe|JPG|JPEG|APK|IOS)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(path);
        if (matcher.find()) {
            result = true;
        }
        return result;
    }
    public void parseAddress() {
        try{
            sentenceFlag=true;
            Vector<Thread> threads= new Vector<Thread>();
            List<Address> addressesList=addressService.findAddressList(10);
            for(Address address:addressesList){
                address.setVisited(1);
                addressService.update(address);
            }
            for(int i=0;i<addressesList.size();i++){
                AddressUtil addressUtil=new AddressUtil();
                addressUtil.setAddress(addressesList.get(i).getContent());
                Thread thread=new Thread(addressUtil);
                threads.add(thread);
                thread.start();
            }
            for(Thread thread:threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            log.info(e.toString());
        }finally {
            sentenceFlag=false;
        }
    }
    private class AddressUtil implements  Runnable{
        public void run(){
            this.parseAddress();
        }
        private String address;

        public void setAddress(String address) {
            this.address = address;
        }

        public void parseAddress(){
            if(isVideo(this.address)){
                return;
            }else {
                String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
                Pattern p = Pattern.compile(regex);
                try {
                    List<String> contentList = parseHtmlContent(this.address);
                    for (String content : contentList) {
                        Matcher matcher = p.matcher(content);
                        while (matcher.find()) {
                            insertAddress(matcher.group());
                        }
                        List<String> sentenceList = getList(content);
                        if(sentenceList.size()>0){
                            long currentTime=System.currentTimeMillis();
                            log.info("parseAddress");
                            RedisUtil.getInstance().lpush("sentence_key",String.valueOf(currentTime));
                            for(String sentence:sentenceList){
                                RedisUtil.getInstance().lpush("sentence_"+currentTime,sentence);
                            }
                        }

                    }
                }catch(Exception e){
                    log.info(e.toString());
                }finally {
                    RedisUtil.getInstance().close();
                }
            }
        }
    }
    public boolean isVideo(String address){
        if(address.contains(".iqiyi.")||address.contains(".bilibili.")||address.contains(".mgtv.")||address.contains(".hao123.")||address.contains("v.ifeng.")||address.contains(".cctv.")||address.contains("v.pptv.")){
            return true;
        }
        return false;
    }
    public List<String> parseHtmlContent(String address){
        URL url = null;
        URLConnection urlconn = null;
        BufferedReader br = null;
        List<String> contentList=new ArrayList<>();
        try {
            url = new URL(address);
            urlconn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                contentList.add(buffer);
            }
        } catch (Exception e) {
            log.info(e.toString());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                log.info(e.toString());
            }finally{
                return contentList;
            }

        }

    }
    public List<String> getList(String text) {
        String tempStr = "";
        List<String> sentenceList = new ArrayList<String>();
        for (int i = 0; i < text.length(); i++) {
            if (text.contains("<p>")) {
                char c = text.charAt(i);
                if (this.isChinese(c)) {
                    tempStr += c;
                } else {
                    if (!"".equals(tempStr) && tempStr.length() > 5 && !sentenceList.contains(tempStr)) {
                        sentenceList.add(tempStr);
                        tempStr = "";
                    } else {
                        tempStr = "";
                    }
                }
            }

        }
        tempStr = "";
        for(int i=0;i<sentenceList.size();i++){
            tempStr+=sentenceList.get(i);
        }
        if(tempStr.length()<100){
            sentenceList = new ArrayList<String>();
        }
        return sentenceList;

    }
    public boolean isChinese(char c) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(String.valueOf(c));
        if (m.find()) {
            temp = true;
        }
        return temp;
    }
}
