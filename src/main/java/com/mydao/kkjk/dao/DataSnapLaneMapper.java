package com.mydao.kkjk.dao;

import com.mydao.kkjk.entity.DataSnap;
import com.mydao.kkjk.entity.DataSnapLane;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSnapLaneMapper {

    int deleteByPrimaryKey(String id);

    int insertSelective(DataSnapLane record);

    DataSnapLane selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataSnapLane record);

    DataSnapLane selectByPlateNo(String plateNo);

}