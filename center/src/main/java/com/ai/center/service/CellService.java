package com.ai.center.service;

import com.ai.center.mapper.CellMapper;
import com.ai.center.model.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class CellService {
    @Autowired
    private CellMapper cellMapper;
    public int insert(Cell cell) {
        return cellMapper.insert(cell);
    }
    public Cell findById(long id){
        return cellMapper.findById(id);
    }
    public Cell findByContent(String content){
    	return cellMapper.findByContent(content);
    }
}
