package cnhuanji.domain;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

@Data
public class MailBean {
    /*邮件主题*/
    private String suject;
    /*邮件正文*/
    private String text;
    /*附件*/
    private FileSystemResource file;
    /*附件名称*/
    private String attachmentname;
    /*内容ID,用于发送静态资源邮件使用*/
    private String contentId;
    /*收件人邮箱*/
    private String receiveMail;
    /*收件人名称*/
    private String receiverName;

    public static MailBean getMaileBean(){
        return new MailBean();
    }
}
