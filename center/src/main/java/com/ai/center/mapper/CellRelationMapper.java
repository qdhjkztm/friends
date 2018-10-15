package com.ai.center.mapper;

import com.ai.center.model.CellRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface CellRelationMapper {
    @Select("select * from t_cell_relation order by id desc")
	List<CellRelation> findList();
    @Select("select * from t_cell_relation t where t.cellId=#{cellId} order by weight desc limit 0,1")
	CellRelation findTopOne(int cellId);
    @Insert("insert into t_cell_relation(cellId,relatedId,weight) values(#{cellId},#{relatedId},#{weight})")
    int insert(CellRelation cellRelation);
    @Select("select * from t_cell_relation t where t.cellId=#{cellId} and relatedId=#{relatedId}")
    CellRelation findCellRelation(CellRelation cellRelation);
    @Update("update t_cell_relation set weight=#{weight} where id=#{id}")
    int update(CellRelation cellRelation);
}