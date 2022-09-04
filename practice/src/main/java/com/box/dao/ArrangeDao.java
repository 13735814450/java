package com.box.dao;

import com.box.vo.req.ArrangeReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ArrangeDao {

    int add(ArrangeReq req);

}
