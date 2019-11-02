package com.bh.live.task.component.issue;

import com.bh.live.model.lottery.IssueArgument;
import com.bh.live.task.component.issue.template.AbstractTemplate;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TemplateConfigDto
 * @description: TemplateConfigDto
 * @author: yq.
 * @date 2019-08-05 10:10:00
 */
@Data
public class TemplateConfigDto implements Serializable {

    private static final long serialVersionUID = 3897137660707988537L;

    private IssueArgument arg;

    private AbstractTemplate switchIssueTemplate;

    private AbstractTemplate closing0IssueTemplate;

    private AbstractTemplate closing60IssueTemplate;

    private Object[] extInfo;
}
