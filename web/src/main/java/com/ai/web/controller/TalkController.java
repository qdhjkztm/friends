package com.ai.web.controller;

import com.ai.center.model.*;
import com.ai.center.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TalkController {
    @Autowired
    private CellRelationService cellRelationService;
    @Autowired
    private SentenceService sentenceService;
    @Autowired
    private CellService cellService;
    @Autowired
    private SentenceRelationService sentenceRelationService;
    @Autowired
    private WordService wordService;
    public String teachAnswer(int sentenceId,String content) {
        try {
            Sentence sentence = sentenceService.findByContent(content);
            if(sentence==null){
                sentence=this.insertSentence(content);
            }
            int relatedId = sentence.getId();
            this.insertSentenceRelation(sentenceId, relatedId, 0.1);
            List<String> wordList = this.departString(content);
            for(String word:wordList){
                if (word.length() >= 2) {
                    Cell cell=this.insertCell(word);
                    int cellId=cell.getId();
                    this.insertCellRelation(cellId, relatedId, 0.1);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    public Sentence getSentence(Sentence sentence)
    {
        List<String> wordList = this.departString(sentence.getContent());
        double flag = 0;
        for (String word : wordList) {
            if (word.length() >= 2) {
                Cell cell = cellService.findByContent(word);
                if (cell != null) {
                    CellRelation cellRelation = cellRelationService.findTopOne(cell.getId());
                    if (cellRelation.getWeight() > flag) {
                        flag = cellRelation.getWeight();
                        sentence = sentenceService.findById(cellRelation.getRelatedId());
                    }
                }
            }
        }
        if(flag>0){
            return sentence;
        }
        return null;
    }
    public Sentence insertSentence(String str){
        Sentence sentence =new Sentence();
        sentence.setContent(str);
        sentenceService.insert(sentence);
        return sentence;
    }
    public Cell insertCell(String str) {
        Cell cell=new Cell();
        cell.setContent(str);
        cellService.insert(cell);
        return cell;
    }
    public void insertSentenceRelation(int sentenceId,int relatedId,double weight){
        SentenceRelation sentenceRelation = new SentenceRelation();
        sentenceRelation.setSentenceId(sentenceId);
        sentenceRelation.setRelatedId(relatedId);
        sentenceRelation.setWeight(weight);
        sentenceRelationService.insert(sentenceRelation);
    }
    public void insertCellRelation(int cellId,int relatedId,double weight) {
        CellRelation cellRelation=new CellRelation();
        cellRelation.setCellId(cellId);
        cellRelation.setRelatedId(relatedId);
        cellRelation.setWeight(weight);
        cellRelationService.insert(cellRelation);
    }
    @RequestMapping(value = "/talk")
    public String response(@RequestParam("message")String message) {
        try {
            if(!"".equals(message)){
                Sentence sentence = sentenceService.findByContent(message);
                if (sentence == null) {
                    sentence = this.insertSentence(message);
                    int sentenceId = sentence.getId();
                    String content=sentence.getContent();
                    sentence=this.getSentence(sentence);
                    if (sentence == null) {
                        return "";
                    }else{
                        return sentence.getContent();
                    }
                } else {
                    SentenceRelation sentenceRelation = sentenceRelationService.findTopOne(sentence.getId());
                    if (sentenceRelation != null) {
                        sentence = sentenceService.findById(sentenceRelation.getRelatedId());
                        return sentence.getContent();
                    } else {
                        int sentenceId = sentence.getId();
                        String content=sentence.getContent();
                        sentence=this.getSentence(sentence);
                        if (sentence == null) {
                            return "";
                        }else{
                            return sentence.getContent();
                        }
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
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
