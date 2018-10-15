package com.ai.client;


import com.ai.center.model.*;
import com.ai.center.service.*;
import com.ai.client.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class Task {
    @Autowired
    private WordService wordService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SentenceService sentenceService;
    @Autowired
    private SentenceRelationService sentenceRelationService;
    @Autowired
    private WordRelationService wordRelationService;
    @Autowired
    private CellService cellService;
    @Autowired
    private CellRelationService cellRelationService;
    private boolean wordFlag=false;

    @Scheduled(fixedRate = 1000)
    public void taskParseSentence(){
        if(wordFlag){
            System.out.println("==============================忙==============================");
        }else{
            this.parseSentence();
        }


    }
    public void parseSentence(){
        wordFlag=true;
        List<String> sentenceKeyList= RedisUtil.getInstance().lrange("sentence_key", 0, 0);
        if(sentenceKeyList.size()>0){
            RedisUtil.getInstance().lpop("sentence_key");
            List<String> sentenceList=RedisUtil.getInstance().lrange("sentence_"+sentenceKeyList.get(0),0,-1);
            if(sentenceList.size()>0) {
                dealWithSentence(sentenceList);
                dealWithSentenceRelation(sentenceList);
                dealWithWord(sentenceList);
                dealWithCell(sentenceList);
            }
            RedisUtil.getInstance().del("sentence_"+sentenceKeyList.get(0));
        }
        wordFlag=false;
    }




    public void dealWithSentence(List<String> sentenceList) {
        for (String str : sentenceList) {
            this.insertSentence(str);
        }
    }
    public void dealWithSentenceRelation(List<String> sentenceList) {
        for(int i=0;i<sentenceList.size();i++) {
            if(i<sentenceList.size()-1) {
                Sentence formerSentence= sentenceService.findByContent(sentenceList.get(i));
                Sentence relatedSentence=sentenceService.findByContent(sentenceList.get(i+1));
                log.info("dealWithSentenceRelation");
                this.insertSentenceRelation(formerSentence,relatedSentence);
            }
        }
    }
    public void insertSentenceRelation(Sentence formerSentence, Sentence relatedSentence){
        log.info("insertSentenceRelation");
        SentenceRelation sentenceRelation=new SentenceRelation();
        sentenceRelation.setSentenceId(formerSentence.getId());
        sentenceRelation.setRelatedId(relatedSentence.getId());
        sentenceRelation= sentenceRelationService.findSentenceRelation(sentenceRelation);
        if(sentenceRelation==null) {
            sentenceRelation=new SentenceRelation();
            sentenceRelation.setSentenceId(formerSentence.getId());
            sentenceRelation.setRelatedId(relatedSentence.getId());
            sentenceRelation.setWeight(0.1);
            sentenceRelationService.insert(sentenceRelation);
        }else {
            sentenceRelation.setWeight(sentenceRelation.getWeight()+0.1);
            sentenceRelationService.update(sentenceRelation);
        }
    }
    public void dealWithWord(List<String> sentenceList) {
        for (String str : sentenceList) {
            List<String> wordList = this.departString(str);

            this.reverseString(wordList);
            this.fowardString(wordList);
        }
    }



    public void fowardString(List<String> wordList) {
        for (int i = 0; i < wordList.size(); i++) {
            String content = "";
            Word formerWord = wordService.findByContent(wordList.get(i));
            content += formerWord.getContent();
            for (int j = i + 1; j < wordList.size(); j++) {
                if(content.length()>4) {
                    break;
                }
                Word backWord = wordService.findByContent(wordList.get(j));
                WordRelation wordRelation = new WordRelation();
                wordRelation.setFormerRelatedId(formerWord.getId());
                wordRelation.setBackRelatedId(backWord.getId());
                WordRelation cr = wordRelationService.findWordRelation(wordRelation);
                if (cr != null && cr.getWeight() > Double.valueOf(100.0)) {
                    content += backWord.getContent();
                    i++;
                    this.insertWord(content);

                } else {
                    break;
                }
                formerWord = wordService.findByContent(wordList.get(i));

            }
        }
    }

    public void reverseString(List<String> wordList) {
        try {
            for (int i = wordList.size() - 1; i > 0; i--) {
                int j = i - 1;
                String backStr = String.valueOf(wordList.get(i));
                Word backWord = this.insertWord(backStr);
                String formerStr = wordList.get(j);
                if (formerStr.length() > 5) {
                    break;
                }
                Word formerWord = this.insertWord(formerStr);

                WordRelation wordRelation = new WordRelation();
                wordRelation.setFormerRelatedId(formerWord.getId());
                wordRelation.setBackRelatedId(backWord.getId());
                log.info("inserWordRelation");
                this.insertWordRelation(wordRelation);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void dealWithCell(List<String> sentenceList) {
        for(int i=0;i<sentenceList.size();i++) {
            if(i<sentenceList.size()-1) {
                List<String> wordList = this.departString(sentenceList.get(i));
                for(String word:wordList){
                    if(word.length()>1){
                        Sentence sentence=sentenceService.findByContent(sentenceList.get(i+1));
                        this.dealWithCell(word, sentence.getId());
                    }
                }
            }

        }
    }
    public void dealWithCell(String content,int relatedId){
        log.info("dealWithCell:"+content);
        Cell cell=cellService.findByContent(content);
        if(cell!=null){
            CellRelation conditionCellRelation=new CellRelation();
            conditionCellRelation.setCellId(cell.getId());
            conditionCellRelation.setRelatedId(relatedId);
            CellRelation cellRelation=cellRelationService.findCellRelation(conditionCellRelation);
            if(cellRelation==null){
                conditionCellRelation.setWeight(0.1);
                cellRelationService.insert(conditionCellRelation);
            }else{
                cellRelation.setWeight(cellRelation.getWeight()+0.1);
                cellRelationService.update(cellRelation);
            }
        }else{
            cell=new Cell();
            cell.setContent(content);
            cellService.insert(cell);
            CellRelation cellRelation=new CellRelation();
            cellRelation.setCellId(cell.getId());
            cellRelation.setRelatedId(relatedId);
            cellRelation.setWeight(0.1);
            cellRelationService.insert(cellRelation);
        }
    }
    public Word insertWord(String content) {
        Word word = wordService.findByContent(content);
        if (word == null) {
            word = new Word();
            word.setContent(content);
            wordService.insert(word);
            log.info("===============================insert word:"+word.getContent()+"===============================");
        }
        return word;
    }
    public void insertSentence(String content) {
        Sentence sentence=sentenceService.findByContent(content);
        if(sentence==null) {
            sentence=new Sentence();
            sentence.setContent(content);
            sentenceService.insert(sentence);
        }
    }
    public void insertWordRelation(WordRelation wordRelation) {
        WordRelation cr = wordRelationService.findWordRelation(wordRelation);
        if (cr == null) {
            cr = new WordRelation();
            cr.setFormerRelatedId(wordRelation.getFormerRelatedId());
            cr.setBackRelatedId(wordRelation.getBackRelatedId());
            cr.setWeight(0.1);
            wordRelationService.insert(cr);
        }else {
            wordRelation.setWeight(wordRelation.getWeight() + 0.1);
            wordRelationService.update(wordRelation);
        }
    }


    public List<String> departString(String str) {
        List<String> wordList = new ArrayList<String>();
        String content = "";
        String tempStr = "";
        for (int i = 0; i < str.length(); i++) {
            tempStr = content;
            content += str.charAt(i);
            Word word = wordService.findByContent(content);
            if (word == null && !"".equals(tempStr)) {
                wordList.add(tempStr);
                content = String.valueOf(str.charAt(i));
                if (i == (str.length() - 1)) {
                    wordList.add(content);
                }
            }
            if (word != null && i == (str.length() - 1)) {
                wordList.add(content);
            }
        }
        return wordList;
    }
}
