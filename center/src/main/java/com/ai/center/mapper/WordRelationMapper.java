package com.ai.center.mapper;

import com.ai.center.model.WordRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface WordRelationMapper {
    @Select("select * from t_word_relation order by id desc")
	List<WordRelation> findList();
    @Insert("insert into t_word_relation(formerRelatedId,weight,backRelatedId) values(#{formerRelatedId},#{weight},#{backRelatedId})")
    int insert(WordRelation wordRelation);
    @Select("select * from t_word_relation t where t.formerRelatedId=#{formerRelatedId} and backRelatedId=#{backRelatedId}")
    WordRelation findWordRelation(WordRelation wordRelation);
    @Update("update t_word_relation set weight=#{weight} where id=#{id}")
    int update(WordRelation wordRelation);
}