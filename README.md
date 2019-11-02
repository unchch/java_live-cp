# java_live-cp

代码规范:使用阿里巴巴java开发代码规范 下载地址:https://files-cdn.cnblogs.com/files/han-1034683568/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8Cv1.2.0.pdf

IDEA可以下载插件Alibaba Java Coding Guidelines plugin 对代码进行检查

IDEA 开发工具忽略提交类型文件设置，Settings=》FileTypes 在窗口最下方“Ignore files and folders”一栏中添加如下忽略：*.idea;*.gitignore;*.sh;*.classpath;*.project;*.settings;target;*.mvn;mvnw;mvnw.cmd; 

lotto-common 公共包;存放util类，pojo类常量枚举等.  

lotto-entity 数据库实体、pojo对象

lotto-rpc-service feign，rpc内部服务调用

lotto-award 开奖派奖服务  端口：9310

lotto-trade 订单交易中心服务  端口：9320

lotto-pay 支付服务 端口：9330

lotto-task 定时任务服务 端口：9340

lotto-user 用户服务 端口：9350

lotto-inform 资讯、banner、广告、营销等边缘类服务 端口:9360

lotto-cms  后台管理系统 端口：9370

lotto-zuul-gateway  网关配置，端口：9300

lotto-eureka-server 注册中心，端口：9380

基础组件：lotto-entity、lotto-common、lotto-rpc-service

服务端互相调用  参考  lotto-award ，lotto-pay
