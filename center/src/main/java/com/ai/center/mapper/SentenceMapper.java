package com.ai.center.mapper;

import com.ai.center.model.Sentence;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface SentenceMapper {
    @Insert("insert into t_sentence(content) values(#{content})")
    int insert(Sentence sentence);
    @Select("select * from t_sentence t where t.id=#{id}")
    Sentence findById(long id);
    @Select("select * from t_sentence t where t.content=#{content}")
    Sentence findByContent(String content);
}