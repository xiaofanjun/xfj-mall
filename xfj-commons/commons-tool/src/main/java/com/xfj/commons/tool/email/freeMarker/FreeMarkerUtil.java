package com.xfj.commons.tool.email.freeMarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * @Author ZQ
 * @Description //通过FreeMarker 获取模板数据，用于获取邮件模板
 * @Date 2019/11/24 19:24
 **/
public class FreeMarkerUtil {

    public static String getMailTextForTemplate(String templatePath, String filename, Map datas) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        //获取class下面的模板文件
        configuration.setDirectoryForTemplateLoading(new File(FreeMarkerUtil.class.getClass().getResource(
                "/" + templatePath).getPath()));
        Template template = configuration.getTemplate(filename, "utf-8");
        StringWriter out = new StringWriter();
        template.process(datas, out);
        return out.toString();
    }
}
