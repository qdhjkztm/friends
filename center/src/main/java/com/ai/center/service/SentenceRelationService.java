package com.ai.center.service;



import com.ai.center.mapper.SentenceRelationMapper;
import com.ai.center.model.SentenceRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class SentenceRelationService {
    @Autowired
    private SentenceRelationMapper sentenceRelationMapper;
    public List<SentenceRelation> findList(){
    	return sentenceRelationMapper.findList();
    }
    public int insert(SentenceRelation sentenceRelation) {
        return sentenceRelationMapper.insert(sentenceRelation);
    }
    public SentenceRelation findSentenceRelation(SentenceRelation sentenceRelation){
        return sentenceRelationMapper.findSentenceRelation(sentenceRelation);
    }
    public int update(SentenceRelation sentenceRelation){
    	return sentenceRelationMapper.update(sentenceRelation);
    }
    public 	SentenceRelation findTopOne(int sentenceId){
    	return sentenceRelationMapper.findTopOne(sentenceId);
    }
}
