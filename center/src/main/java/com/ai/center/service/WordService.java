package com.ai.center.service;

import com.ai.center.mapper.WordMapper;
import com.ai.center.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {
    @Autowired
    private WordMapper wordMapper;
    public List<Word> findWordList(){
        return wordMapper.findWordList();
    }
    public Word findByContent(String content){
        return wordMapper.findByContent(content);
    }
    public int insert(Word word){
        return wordMapper.insert(word);
    }
}
