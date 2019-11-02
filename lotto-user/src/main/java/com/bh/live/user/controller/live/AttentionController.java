package com.bh.live.user.controller.live;


import com.bh.live.common.core.controller.BaseController;
import com.bh.live.common.result.CodeMsg;
import com.bh.live.common.result.Result;
import com.bh.live.model.user.Attention;
import com.bh.live.model.user.LiveUser;
import com.bh.live.pojo.res.user.AttentionUserRes;
import com.bh.live.user.service.IAttentionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关注表 前端控制器
 * </p>
 *
 * @author wuhuanrong
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/attention")
@Api(tags ="用户关注列表")
public class AttentionController extends BaseController {

    @Autowired
    IAttentionService attentionService;

    /**
     * 关注用户列表
     * @return
     */
    @ApiOperation(value = "关注列表", notes = "关注列表查询", response = Result.class)
    @ApiResponses(value = {@ApiResponse(response = AttentionUserRes.class, code = 0, message = "关注用户列表")})
    @GetMapping("/page")
    public Result getPage(@RequestParam("type") @ApiParam("类型") Integer type,
                          @RequestParam("pageNum") @ApiParam("页码") Integer pageNum,
                          @RequestParam("pageSize") @ApiParam("数量") Integer pageSize) {
        LiveUser user = getHeaderLiveUser();
        if (user==null){
            return  Result.error(CodeMsg.E_20001);
        }
        return Result.success(attentionService.queryAttentList(type,user.getId(),pageNum,pageSize));
    }

    /**
     * 添加获取取消关注
     *
     * @return
     */
    @ApiOperation(value = "关注", notes = "添加关注或者取消关注", response = Result.class)
    @GetMapping("/update")
    public Result update(@RequestParam("targetId") @ApiParam("被关注Id") Integer targetId,@RequestParam("isAttent") @ApiParam("关注类型 0取消 1关注")Integer isAttent) {
        Attention attention = new Attention();
        return Result.success(attentionService.insertOrUpdate(attention));
    }


}

