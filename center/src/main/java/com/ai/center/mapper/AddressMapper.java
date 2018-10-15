package com.ai.center.mapper;

import com.ai.center.model.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Insert("insert into t_address(content,category,visited) values(#{content},#{category},#{visited})")
    int insert(Address address);
    @Update("update t_address set visited=#{visited} where id=#{id}")
    int update(Address address);
    @Select("select * from t_address t where t.id=#{id}")
    Address findById(long id);
    @Select("select * from t_address t where t.content=#{content}")
    Address findByContent(String content);
    @Select("select * from t_address t where t.visited=#{visited}  order by id asc limit 0,1")
    Address findTopOne(int visted);
    @Select("select * from t_address t where t.visited=0  order by id asc limit 0,#{pageSize}")
    List<Address> findAddressList(int pageSize);
}