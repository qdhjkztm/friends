package com.ai.center.service;


import com.ai.center.mapper.CellRelationMapper;
import com.ai.center.model.CellRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class CellRelationService {
    @Autowired
    private CellRelationMapper cellRelationMapper;
    public List<CellRelation> findList(){
    	return cellRelationMapper.findList();
    }
    public int insert(CellRelation cellRelation) {
        return cellRelationMapper.insert(cellRelation);
    }
    public CellRelation findCellRelation(CellRelation cellRelation){
        return cellRelationMapper.findCellRelation(cellRelation);
    }
    public int update(CellRelation cellRelation){
    	return cellRelationMapper.update(cellRelation);
    }
    public CellRelation findTopOne(int cellId){
    	return cellRelationMapper.findTopOne(cellId);
    }
}
