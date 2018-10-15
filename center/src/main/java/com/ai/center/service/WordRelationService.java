package com.ai.center.service;



import com.ai.center.mapper.WordRelationMapper;
import com.ai.center.model.WordRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class WordRelationService {
    @Autowired
    private WordRelationMapper wordRelationMapper;

    public List<WordRelation> findList(){
    	return wordRelationMapper.findList();
    }
    public int insert(WordRelation wordRelation) {
        return wordRelationMapper.insert(wordRelation);
    }
    public WordRelation findWordRelation(WordRelation wordRelation){
        return wordRelationMapper.findWordRelation(wordRelation);
    }
    public int update(WordRelation wordRelation){
    	return wordRelationMapper.update(wordRelation);
    }
}
