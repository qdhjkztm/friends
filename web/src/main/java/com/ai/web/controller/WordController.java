package com.ai.web.controller;

import com.alibaba.fastjson.JSON;
import com.ai.center.model.Word;
import com.ai.center.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordController {
    @Autowired
    private WordService wordService;
    @RequestMapping(value = "/wordList")
    public String findWordList(){
        List<Word> wordList=wordService.findWordList();
        return JSON.toJSONString(wordList);
    }
}
