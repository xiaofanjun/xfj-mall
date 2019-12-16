package com.xfj.commons.tool.email;

/**
 * @Author ZQ
 * @Description 邮件发送接口
 * @Date 2019/12/5 9:04
 **/
public interface EmailSender {
    /**
     * 发送简单的文本邮件
     *
     * @throws Exception
     */
    public void sendMail(MailData mailData) throws Exception;

    /**
     * 发送待附件的邮件
     *
     * @throws Exception
     */
    public void sendMailWithAttachFile(MailData mailData) throws Exception;

    /**
     * 发送HTML内容的邮件
     *
     * @throws Exception
     */
    public void sendHtmlMail(MailData mailData) throws Exception;

    /**
     * 使用Html模板发送邮件
     *
     * @throws Exception
     */
    public void sendHtmlMailUseTemplate(MailData mailData) throws Exception;
}
