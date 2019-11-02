package com.bh.live.common.utils.jxls;

import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @description: Jxls 导出
 * @date 2019/5/23 15:18
 */
@Slf4j
public class JxlsExcelUtil<T> {

    /**
     * 导出文件地址
     */
    private String fileExportUrl;
    /**
     * list
     */
    private List<T> list;
    /**
     * 其他对象参数
     */
    private Map<String, Object> maps;

    /**
     * excel 模板
     */
    private String template;

    private HttpServletResponse response;


    public JxlsExcelUtil(String fileExportUrl, List<T> list, String template, HttpServletResponse response) {
        this.fileExportUrl = fileExportUrl;
        this.list = list;
        this.template = template;
        this.response = response;
        export();
    }

    public JxlsExcelUtil(String fileExportUrl, List<T> list, String template, HttpServletResponse response, Map<String, Object> maps) {
        this.fileExportUrl = fileExportUrl;
        this.list = list;
        this.template = template;
        this.response = response;
        this.maps = maps;
        export();
    }

    private void export() {
        InputStream inputStream = null;
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("/template/" + this.template);

            String exportStore = this.fileExportUrl + "/target/";
            String fileName = System.currentTimeMillis() + ".xls";

            File storeFile = new File(exportStore);
            if (!storeFile.exists()) {
                storeFile.mkdirs();
            }

            for (Resource resource : resources) {
                InputStream is = resource.getInputStream();
                try (OutputStream os = new FileOutputStream(exportStore + fileName)) {
                    Context context = new Context();
                    if (CommonUtil.isNotEmpty(this.list)) {
                        context.putVar("records", this.list);
                    }
                    if (CommonUtil.isNotEmpty(this.maps)) {
                        this.maps.forEach((k, v) -> context.putVar(k, v));
                    }

                    JxlsHelper.getInstance().processTemplate(is, os, context);
                }
                break;
            }
            // 下载文件
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

            inputStream = new FileInputStream(exportStore + fileName);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();

            FileUtils.deleteFile(exportStore);
        } catch (Exception e) {
            log.error("export exception. cause:{} message:{}", e.getCause(), e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    inputStream = null;
                }
            }
        }
    }
}
