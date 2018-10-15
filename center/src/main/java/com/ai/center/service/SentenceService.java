package com.ai.center.service;

import com.ai.center.mapper.SentenceMapper;
import com.ai.center.model.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class SentenceService {
    @Autowired
    private SentenceMapper sentenceMapper;
    public int insert(Sentence sentence) {
        return sentenceMapper.insert(sentence);
    }
    public Sentence findById(long id){
        return sentenceMapper.findById(id);
    }
    public Sentence findByContent(String content){
    	return sentenceMapper.findByContent(content);
    }
}
