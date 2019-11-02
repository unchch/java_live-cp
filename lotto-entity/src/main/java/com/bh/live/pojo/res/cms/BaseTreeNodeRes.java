package com.bh.live.pojo.res.cms;

import java.util.ArrayList;
import java.util.List;

/**
 *  tree 抽象节点 树可继承这个节点
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public abstract class BaseTreeNodeRes {

    /**
     * 本节点id
     */
    protected Integer id;

    /**
     * 父节点id
     */
    protected Integer parentId;

    protected List<BaseTreeNodeRes> children = new ArrayList<>();

    public void addChilren(BaseTreeNodeRes node) {
        this.children.add(node);
    }

    public List<BaseTreeNodeRes> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTreeNodeRes> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
