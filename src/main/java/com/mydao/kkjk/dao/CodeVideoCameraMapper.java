package com.mydao.kkjk.dao;


import com.mydao.kkjk.entity.CodeVideoCamera;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeVideoCameraMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(CodeVideoCamera record);

    CodeVideoCamera selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CodeVideoCamera record);

    List<CodeVideoCamera> selectCameraIP(CodeVideoCamera record);
    String selectByIp(CodeVideoCamera record);

    List<CodeVideoCamera> selectList(CodeVideoCamera record);
    Integer count(CodeVideoCamera record);
}