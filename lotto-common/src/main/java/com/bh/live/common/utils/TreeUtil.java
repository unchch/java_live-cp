package com.bh.live.common.utils;

import com.bh.live.pojo.res.cms.BaseTreeNodeRes;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构相关工具类
 *
 * @author yq.
 * @version 1.0.0 2019-04-07
 * @since 1.0.0 2019-04-07
 **/
public class TreeUtil {


    /**
     * description 用双重循环建树
     *
     * @param treeNodes 1 树节点列表, root根标志
     * @param root 2
     * @return java.util.List<T>
     */
    public static <T extends BaseTreeNodeRes> List<T> buildTreeBy2Loop(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T node : treeNodes) {
            if (root.equals(node.getParentId())) {
                trees.add(node);
            }

            for (T treeNode : treeNodes) {
                if (node.getId().equals(treeNode.getParentId())) {
                    if (node.getChildren()==null) {
                        node.setChildren(new ArrayList<>());
                    }
                    node.addChilren(treeNode);
                }
            }
        }
        return trees;
    }

}
