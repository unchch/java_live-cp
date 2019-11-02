package com.bh.live.common.constant;


/**
 * 2019/06/21
 * 缓存键
 */
public class RedisKey {

    /**
     * 亚冠和走势
     */
    public static final String AFC_TREND_REDIS_KEY = "AFC_TREND:";

    public static String afcTrendRedisKey(int expect,int variety) {
        return String.format("%s_%d_d%", AFC_TREND_REDIS_KEY, expect,variety);
    }

    /**
     * 位置走势
     */
    public static final String POSITION_TREND_REDIS_KEY = "POSITION_TREND:";

    public static String positionTrendRedisKey(int position,int expect) {
        return String.format("%s_%d_%d", POSITION_TREND_REDIS_KEY, position,expect);
    }

    /**
     * 今日号码
     */
    public static final String TODAY_NUMBER_REDIS_KEY = "TODAY_NUMBER:";

    public static String todayNumberRedisKey() {
        return String.format("%s_%d", TODAY_NUMBER_REDIS_KEY, 1);
    }

    /**
     * 开号统计
     */
    public static final String OPENING_NUMBER_STATISTICS = "OPEN_NUMBER:";

    public static String opemNumberRedisKey(int param) {
        return String.format("%s_%d", OPENING_NUMBER_STATISTICS, param);
    }

    /**
     * MESSAGE_TEMPLATE
     */
    public static final String MESSAGE_TEMPLATE = "message_template:";

    public static String messageTemplateRedisKey(long domainId, Integer messageType) {
        return String.format("%s%d_%d", MESSAGE_TEMPLATE, domainId, messageType);
    }

    /**
     * 投资价值
     */
    public static final String VALUE_INVEST_REDIS_KEY = "VALUE_INVEST:";

    public static String valueInvestRedisKey(int type, int position, int count,int variety) {
        return String.format("%s_%d_%d_%d_%d", VALUE_INVEST_REDIS_KEY, type,position,count,variety);
    }

    /**
     * 冷热分析
     */
    public static final String HOT_COLD_ANALYSIS = "HOT_COLD_ANALYSIS:";

    public static String hotColdAnalysisRedisKey( Integer variety) {
        return String.format("%s_%d", HOT_COLD_ANALYSIS, variety);
    }
    
    /**
     * 露珠
     * */
    public static final String DEWDROP = "DEWDROP:";
    
    /**
     * 前后露珠
     * */
    public static final String AROUND = "AROUND:";
    public static String dewdropAroundRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, AROUND,varietyType,openTime);
    }
    /**numberDewdropRedisKey
     * 号码露珠
     * */
    public static final String NUMBER = "NUMBER:";
    public static String numberDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, NUMBER,varietyType,openTime);
    }
	/**
	 * 冠亚军露珠
	 * */
    public static final String CHAMPION = "CHAMPION:";
    public static String dewdropChampionRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, CHAMPION,varietyType,openTime);
    }
    /**
     * 综合露珠
     * */
    public static final String COMPREHENSIVE = "COMPREHENSIVE:";
    public static String comprehensiveDewdropRedisKey(String openTime,String varietyType,String type){
    	return String.format("%s_%s_%s_%s_%s", DEWDROP, COMPREHENSIVE,varietyType,openTime,type);
    }
    /**
     * 单双露珠
     * */
    public static final String SINGLEDOUBLE = "SINGLEDOUBLE:";
    public static String singleDoubleDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, SINGLEDOUBLE,varietyType,openTime);
    }
    /**
     * 龙虎露珠
     * */
    public static final String DRAGONTIGER = "DRAGONTIGER:";
    public static String dragonTigerDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, DRAGONTIGER,varietyType,openTime);
    }
    /**
     * 总和露珠
     * */
    public static final String TOTAL = "TOTAL:";
    public static String totalDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, TOTAL,varietyType,openTime);
    }
    /**
     *奇偶露珠
     * */
    public static final String ODDEVE = "ODDEVE:";
    public static String oddEveDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, ODDEVE,varietyType,openTime);
    }
    /**
     * 上下盘露珠
     * */
    public static final String UPDOWN = "UPDOWN:";
    public static String upDownDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, UPDOWN,varietyType,openTime);
    }
    /**
     * 尾数大小露珠
     * */
    public static final String LASTNUMBER = "LASTNUMBER:";
    public static String lastNumberDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, LASTNUMBER,varietyType,openTime);
    }
    /**
     * 合数单双露珠
     * */
    public static final String SUMNUMBER = "SUMNUMBER:";
    public static String sumNumberDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, SUMNUMBER,varietyType,openTime);
    }
    /**
     * 中发白露珠
     * */
    public static final String MIDDLEHAIR = "MIDDLEHAIR:";
    public static String middleHairDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, MIDDLEHAIR,varietyType,openTime);
    }
    /**
     * 东南西北露珠
     * */
    public static final String NORTHSOUTH = "NORTHSOUTH:";
    public static String northSouthDewdropRedisKey(String openTime,String varietyType){
    	return String.format("%s_%s_%s_%s", DEWDROP, NORTHSOUTH,varietyType,openTime);
    }
    /**
     * 香港彩露珠
     * */
    public static final String HongKong = "HongKong:";
    public static String HongKongColourRedisKey(String dode){
    	return String.format("%s_%s_%s", DEWDROP, HongKong,dode);
    }
    /**
     * 香港彩龙湖正码露珠
     * */
    public static final String CODEDRAGONTIGER = "CODEDRAGONTIGER:";
    public static String codeDragonTigerRedisKey(){
    	return String.format("%s_%s", DEWDROP, CODEDRAGONTIGER);
    }
    
    /**
     * 号码规律
     * */
    public static final String NUMBERLAW = "NUMBERLAW:";
    public static String numberLawRedisKey(int number,int expect,String varietyType){
    	return String.format("%s_%s_%d_%d_%s", DEWDROP, NUMBERLAW,number,expect,varietyType);
    }
    /**
     * 两面数据统计
     * */
    public static final String TWODATASTATISTICS = "TWODATASTATISTICS:";
    public static String twoDataStatisticsRedisKey(int ball,String varietyType){
    	return String.format("%s_%s_%d_%s", DEWDROP, TWODATASTATISTICS,ball,varietyType);
    }
    /**
     * 遗漏大数据
     * */
    public static final String MISSINGBIGDATA = "MISSINGBIGDATA:";
    public static String missingBigDataRedisKey(String dateType,int index,String varietyType){
    	return String.format("%s_%s_%s_%d_%s", DEWDROP, MISSINGBIGDATA,dateType,index,varietyType);
    }

    /**
     * 十二生肖和五行
     */
    public static final String ZODIAC_FIVE_PHASES = "ZODIAC_FIVE_PHASES:";
    public static String zodiacFivePhasesRedisKey(int year) {
        return String.format("%s_%s", ZODIAC_FIVE_PHASES,year);
    }

    /**
     * 三色波(红波)
     */
    public static final String REDXIAO = "REDXIAO:";
    public static String redXiaoRedisKey(int type) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",REDXIAO,type);
    }

    /**
     * 蓝波
     */
    public static final String BLUEXIAO = "BLUEXIAO:";
    public static String blueXiaoRedisKey(int type) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",BLUEXIAO,type);
    }

    /**
     * 三色波(绿波)
     */
    public static final String GREENXIAO = "GREENXIAO:";
    public static String greenXiaoRedisKey(int type) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",GREENXIAO,type);
    }

    /**
     * 家禽
     */
    public static final String POULTRY = "POULTRY:";
    public static String poultryRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", POULTRY,year);
    }

    /**
     * 野兽
     */
    public static final String BEAST = "BEAST:";
    public static String beastRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", BEAST,year);
    }

    /**
     * 男肖
     */
    public static final String MALEXIAO = "MALEXIAO:";
    public static String maleXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", MALEXIAO,year);
    }

    /**
     * 女肖
     */
    public static final String FEMALEXIAO = "FEMALEXIAO:";
    public static String femaleXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", FEMALEXIAO,year);
    }

    /**
     * 天肖
     */
    public static final String DAYXIAO = "DAYXIAO:";
    public static String dayXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", DAYXIAO,year);
    }

    /**
     * 地肖
     */
    public static final String GROUNDXIAO = "GROUNDXIAO:";
    public static String groundRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", GROUNDXIAO,year);
    }

    /**
     * 春肖
     */
    public static final String SPRINGXIAO = "SPRINGXIAO:";
    public static String springXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", SPRINGXIAO,year);
    }

    /**
     * 夏肖
     */
    public static final String SUMMERXIAO = "SUMMERXIAO:";
    public static String summerXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", SUMMERXIAO,year);
    }

    /**
     * 秋肖
     */
    public static final String AUTUMNXIAO = "AUTUMNXIAO:";
    public static String autumnXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",AUTUMNXIAO,year);
    }

    /**
     * 冬肖
     */
    public static final String WINTERXIAO = "WINTERXIAO:";
    public static String winterXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",WINTERXIAO,year);
    }

    /**
     * 琴肖
     */
    public static final String PIANOXIAO = "PIANOXIAO:";
    public static String pianoXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", PIANOXIAO,year);
    }

    /**
     * 棋肖
     */
    public static final String CHESSXIAO = "CHESSXIAO:";
    public static String chessXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", CHESSXIAO,year);
    }

    /**
     * 书肖
     */
    public static final String BOOKXIAO = "BOOKXIAO:";
    public static String bookXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:",BOOKXIAO,year);
    }

    /**
     * 画肖
     */
    public static final String PAINTINGXIAO = "PAINTINGXIAO:";
    public static String paintingXiaoRedis(int year) {
        return String.format("%s_%s_%s", "ZODIAC_FIVE_PHASES:", PAINTINGXIAO,year);
    }
}
