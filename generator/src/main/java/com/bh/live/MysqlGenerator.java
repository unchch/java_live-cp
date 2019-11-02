package com.bh.live;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgs
 * @title: MysqlGenerator
 * @projectName java_live-cp
 * @description: TODO
 * @date 2019/7/16  20:46
 */
public class MysqlGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //这里是你需要生成的文件的根目录 如：你要生成lotto-user,这里就写你lotto-user工程在你电脑的
        //工程目录

        String projectPath = "E:\\workspace\\java_live-cp\\generator";
        gc.setOutputDir(projectPath + "/src/main/java");
        //生成文件的名称
        gc.setAuthor("WuLong");
        gc.setOpen(false);
        gc.setMapperName("%sDao");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://dev-db1.yzkj.net:3306/db_live?useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                //tinyint转换成Boolean
                if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.INTEGER;
                }
                //将数据库中datetime转换成date
                if (fieldType.toLowerCase().contains("datetime")||fieldType.toLowerCase().contains("timestamp")) {
                    return DbColumnType.DATE;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });

        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.bh.live.award");
        pc.setMapper("dao");
        pc.setEntity("model");

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/com/bh/live/award/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        //这里是你要生成的数据库表，多张表以逗号分隔
        strategy.setInclude("lotto_order","lotto_order_info","lotto_order_info_detail","lotto_issue","lotto_play_bit","lotto_play_item","lotto_play","lotto_play_bet","lotto_seed"
        ,"lotto_seed_category","lotto_trade_trans_user","lotto_trade_trans_user_log","lotto_wallet");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("lotto_");
        mpg.setStrategy(strategy);

        mpg.execute();
    }

}
