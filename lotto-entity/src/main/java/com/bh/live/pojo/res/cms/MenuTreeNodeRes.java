package com.bh.live.pojo.res.cms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 菜单树
 *
 * @author yq.
 * @version 1.0.0 2019-04-24
 * @since 1.0.0 2019-04-24
 **/
@JsonPropertyOrder({"id","parentId","orderNum","code","name","path","type","method","icon"})
@JsonIgnoreProperties({"createTime","updateTime"})
public class MenuTreeNodeRes extends BaseTreeNodeRes {

    /**
     *
     */
    private String code;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点对应URL
     */
    private String path;

    /**
     * 节点类型
     */
    private Short type;

    private String method;

    private String icon;

    /** 显示顺序 */
    private Integer orderNum;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
