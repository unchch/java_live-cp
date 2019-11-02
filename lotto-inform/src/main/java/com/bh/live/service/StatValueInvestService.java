package com.bh.live.service;


import com.bh.live.pojo.res.inform.ValueInvestRep;

import java.util.List;


public interface StatValueInvestService {

    List<ValueInvestRep> queryValueInvest(int type, int position, int count, int variety);
}
