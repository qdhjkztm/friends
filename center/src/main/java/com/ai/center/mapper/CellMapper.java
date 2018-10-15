package com.ai.center.mapper;

import com.ai.center.model.Cell;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CellMapper {
    @Insert("insert into t_cell(content) values(#{content})")
    int insert(Cell cell);
    @Select("select * from t_cell t where t.id=#{id}")
    Cell findById(long id);
    @Select("select * from t_cell t where t.content=#{content}")
    Cell findByContent(String content);
}