package com.audit.modules.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.audit.modules.common.mybatis.MybatisRepostiory;
import com.audit.modules.report.entity.SmartUsable;
@Component
@MybatisRepostiory
public interface SmartUsableDao {
   
    List<SmartUsable> findByTypeAndYear(@Param("typeCode") String typeCode,@Param("year") String year);
  
   
}