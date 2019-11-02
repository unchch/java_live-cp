package com.bh.live.rpc.service.inform;

import com.bh.live.common.exception.ServiceRuntimeException;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.pojo.res.inform.IssueCurrentRes;

public class IssueFeignServiceFallback implements IIssueFeignService {

    @Override
    public IssueCurrentRes selectCurrentIssue(Integer seedNo) {
        throw new ServiceRuntimeException(CodeMsg.E_60202);
    }
}
