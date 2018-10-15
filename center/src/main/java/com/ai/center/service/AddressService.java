package com.ai.center.service;

import com.ai.center.mapper.AddressMapper;
import com.ai.center.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZYQ on 2017/4/8.
 */
@Service
public class AddressService {
    @Autowired
    AddressMapper addressMapper;
    public int insert(Address address) {
        return addressMapper.insert(address);
    }
    public int update(Address address) {
    	return addressMapper.update(address);
    }
    public Address findById(long id){
        return addressMapper.findById(id);
    }
    public Address findByContent(String content){
    	return addressMapper.findByContent(content);
    }
    public Address findTopOne(int visted) {
    	return addressMapper.findTopOne(visted);
    }
    public List<Address> findAddressList(int pageSize){
        return addressMapper.findAddressList(pageSize);
    }
}
