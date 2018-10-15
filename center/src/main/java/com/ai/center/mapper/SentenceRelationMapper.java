package com.ai.center.mapper;

import com.ai.center.model.SentenceRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface SentenceRelationMapper {
    @Select("select * from t_sentence_relation order by id desc")
	List<SentenceRelation> findList();
    @Select("select * from t_sentence_relation t where t.sentenceId=#{sentenceId} order by weight desc limit 0,1")
	SentenceRelation findTopOne(int sentenceId);
    @Insert("insert into t_sentence_relation(sentenceId,relatedId,weight) values(#{sentenceId},#{relatedId},#{weight})")
    int insert(SentenceRelation sentenceRelation);
    @Select("select * from t_sentence_relation t where t.sentenceId=#{sentenceId} and relatedId=#{relatedId}")
    SentenceRelation findSentenceRelation(SentenceRelation sentenceRelation);
    @Update("update t_sentence_relation set weight=#{weight} where id=#{id}")
    int update(SentenceRelation sentenceRelation);
}