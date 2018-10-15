package com.ai.center.mapper;

import com.ai.center.model.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WordMapper {
    @Results({
            @Result(property = "content",column = "content")
    })
    @Select("SELECT * FROM t_word")
    List<Word> findWordList();
    @Select("select * from t_word t where t.content=#{content}")
    Word findByContent(String content);
    @Insert("insert into t_word(content) values(#{content})")
    int insert(Word word);
}
