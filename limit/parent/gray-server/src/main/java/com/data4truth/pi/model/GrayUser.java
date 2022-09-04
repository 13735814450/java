package com.data4truth.pi.model;

/**
 * @author: null
 * @date: 2020-01-10 14:52:28
 * @description:
 */
public class GrayUser {
    /**
     * 主键
     */
    private Integer id;

    /**
     * memberid列表，以; 分割
     */
    private String memberid;

    /**
     * 类型
     */
    private String type;

    /**
     * 主键
     *
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * memberid列表，以; 分割
     *
     * @return memberId memberid列表，以; 分割
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * memberid列表，以; 分割
     *
     * @param memberid memberid列表，以; 分割
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    /**
     * 类型
     *
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}