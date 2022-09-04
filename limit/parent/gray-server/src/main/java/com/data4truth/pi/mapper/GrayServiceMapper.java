package com.data4truth.pi.mapper;

import com.data4truth.pi.model.GrayService;
import com.data4truth.pi.model.GrayServiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: null
 * @date: 2019-12-16 15:32:23
 * @description:  
 */
public interface GrayServiceMapper {
    /**
     * countByExample
     * 
     * @param example GrayServiceExample 
     * @return long 
     */
    long countByExample(GrayServiceExample example);

    /**
     * deleteByExample
     * 
     * @param example GrayServiceExample 
     * @return int 
     */
    int deleteByExample(GrayServiceExample example);

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
     * @param record GrayService 
     * @return int 
     */
    int insert(GrayService record);

    /**
     * insertSelective
     * 
     * @param record GrayService 
     * @return int 
     */
    int insertSelective(GrayService record);

    /**
     * selectByExample
     * 
     * @param example GrayServiceExample 
     * @return List<GrayService> 
     */
    List<GrayService> selectByExample(GrayServiceExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id Integer 
     * @return GrayService 
     */
    GrayService selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record GrayService 
     * @param example GrayServiceExample 
     * @return int 
     */
    int updateByExampleSelective(@Param("record") GrayService record, @Param("example") GrayServiceExample example);

    /**
     * updateByExample
     * 
     * @param record GrayService 
     * @param example GrayServiceExample 
     * @return int 
     */
    int updateByExample(@Param("record") GrayService record, @Param("example") GrayServiceExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record GrayService 
     * @return int 
     */
    int updateByPrimaryKeySelective(GrayService record);

    /**
     * updateByPrimaryKey
     * 
     * @param record GrayService 
     * @return int 
     */
    int updateByPrimaryKey(GrayService record);
}