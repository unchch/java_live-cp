package com.bh.live.common.constant;

import com.bh.live.common.enums.*;
import com.bh.live.common.enums.lottery.IssueEnum;
import com.bh.live.common.enums.user.UserEnum;
import com.bh.live.common.utils.CommonUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 枚举类型公共类
 * @date 2019/5/1516:57
 */
@Component
public class EnumConstants {

    private static List<EnumCategory> categories = new ArrayList<>();

    static {
        categories.add(new EnumCategory("C1001", "上传图片文件夹枚举", FileLoaderEnum.class));
        categories.add(new EnumCategory("C1002", "礼物分类枚举", GiftTypeEnum.class));
        categories.add(new EnumCategory("C1003", "踢下线原因枚举", KickOffLineEnum.class));
        categories.add(new EnumCategory("C1004", "主播直播状态枚举", HostUserStatusEnum.class));
        categories.add(new EnumCategory("C1005", "直播经验枚举", ExperienceEnum.class));
        categories.add(new EnumCategory("C1006", "是否可用枚举", UserEnum.IsUsableEnum.class));
        categories.add(new EnumCategory("C1007", "主播审核状态枚举", VerifyStatusEnum.class));
        categories.add(new EnumCategory("C1008", "主播结算类型枚举", SettlementTypeEnum.class));
        categories.add(new EnumCategory("C1009", "主播结算周期枚举", SettlementCycleEnum.class));
        categories.add(new EnumCategory("C1010", "直播间状态枚举", HostRoomStatusEnum.class));

        //**竞猜配置
        categories.add(new EnumCategory("C1013", "日期周期", DateTypeEnum.class));
        categories.add(new EnumCategory("C1011", "价格类别", PriceTypeEnum.class));
        categories.add(new EnumCategory("C1012", "用户类别", UserEnum.UserTypeEnum.class));

        categories.add(new EnumCategory("C1014", "禁言原因枚举", NoTalkEnum.class));
        categories.add(new EnumCategory("C1015", "账户类型枚举", AccountTypeEnum.class));

        //彩种管理枚举配置
        categories.add(new EnumCategory("C1016","彩期状态枚举", IssueEnum.IssueStatusEnum.class));

    }

    public static Map<String, EnumDesc> getAllEnumConstants() {

        Map<String, EnumDesc> res = Maps.newHashMap();

        categories.forEach((category) -> {
            List<Map<String, Object>> constants = new ArrayList<>();
            String code = category.getCode();
            String name = category.getName();

            Class<?> enumClass = category.getEnumClass();
            Enum[] objects = (Enum[]) enumClass.getEnumConstants();

            if (objects.length > 0) {
                for (Enum object : objects) {
                    BaseEnum data = (BaseEnum) object;
                    if (CommonUtil.isEmpty(data.code())) {
                        continue;
                    }
                    Map<String, Object> dataItem = new HashMap<>();
                    dataItem.put("code", data.code());
                    dataItem.put("name", data.value());
                    constants.add(dataItem);
                }
            }
            res.put(code, new EnumDesc(name, constants));
        });
        return res;
    }
}
