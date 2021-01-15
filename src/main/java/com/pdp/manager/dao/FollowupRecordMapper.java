package com.pdp.manager.dao;

import com.pdp.manager.dto.SearchDTO;
import com.pdp.manager.dto.VWarnFollowDTO;
import com.pdp.manager.pojo.FollowupRecord;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowupRecordMapper {
	
    public int deleteByPrimaryKey(@Param("id")Integer id);

    public int insert(FollowupRecord record);

    public FollowupRecord selectByPrimaryKey(@Param("id")Integer id);

    public List<VWarnFollowDTO> selectWarn(SearchDTO searchDTO);
    
    public List<FollowupRecord> selectAll(SearchDTO searchDTO);

    public int updateByPrimaryKey(FollowupRecord record);
}