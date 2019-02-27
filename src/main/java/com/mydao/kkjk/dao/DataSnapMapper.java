package com.mydao.kkjk.dao;


import com.mydao.kkjk.entity.DataSnap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSnapMapper {

    int deleteByPrimaryKey(String id);

    int insert(DataSnap record);

    int insertSelective(DataSnap record);

    DataSnap selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataSnap record);

    int updateByPrimaryKey(DataSnap record);

    DataSnap selectByPlateNo(String plateNo);

    String selectLastData(String netSiteNo);
}