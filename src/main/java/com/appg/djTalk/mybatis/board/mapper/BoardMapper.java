package com.appg.djTalk.mybatis.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appg.djTalk.common.bean.DataMap;

@Repository
public interface BoardMapper{
	
	public List<DataMap> getListOfBoard(@Param("seq") int seq);
	
	public List<DataMap> getListOfNotice(@Param("seq") int seq);
	
	public int testQuery(); 
	
}
 