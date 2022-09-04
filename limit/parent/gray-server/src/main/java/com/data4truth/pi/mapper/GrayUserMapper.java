package com.data4truth.pi.mapper;

import com.data4truth.pi.model.GrayUser;
import com.data4truth.pi.model.GrayUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: null
 * @date: 2020-01-10 14:52:28
 * @description:  
 */
public interface GrayUserMapper {
    /**
     * countByExample
     * 
     * @param example GrayUserExample 
     * @return long 
     */
    long countByExample(GrayUserExample example);

    /**
     * deleteByExample
     * 
     * @param example GrayUserExample 
     * @return int 
     */
    int deleteByExample(GrayUserExample example);

    /**
     * deleteByPrimaryKey
     * 
     * @param id Integer 
     * @return int 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     * 
     * @param record GrayUser 
     * @return int 
     */
    int insert(GrayUser record);

    /**
     * insertSelective
     * 
     * @param record GrayUser 
     * @return int 
     */
    int insertSelective(GrayUser record);

    /**
     * selectByExample
     * 
     * @param example GrayUserExample 
     * @return List<GrayUser> 
     */
    List<GrayUser> selectByExample(GrayUserExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id Integer 
     * @return GrayUser 
     */
    GrayUser selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record GrayUser 
     * @param example GrayUserExample 
     * @return int 
     */
    int updateByExampleSelective(@Param("record") GrayUser record, @Param("example") GrayUserExample example);

    /**
     * updateByExample
     * 
     * @param record GrayUser 
     * @param example GrayUserExample 
     * @return int 
     */
    int updateByExample(@Param("record") GrayUser record, @Param("example") GrayUserExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record GrayUser 
     * @return int 
     */
    int updateByPrimaryKeySelective(GrayUser record);

    /**
     * updateByPrimaryKey
     * 
     * @param record GrayUser 
     * @return int 
     */
    int updateByPrimaryKey(GrayUser record);
}