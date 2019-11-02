package cnhuanji;

import cnhuanji.domain.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;

public class TestMail {

    public static void main(String[] args) throws MessagingException {
        MailUtil mailUtil = new MailUtil();
        TemplateEngine templateEngine = new TemplateEngine();
        MailBean mailBean = new MailBean();
        mailBean.setReceiveMail("1340768350@qq.com");
        mailBean.setSuject("测试邮件");
        mailBean.setText("测试发送简单邮件");
        System.out.println("------发送简单邮件开始-------");
        mailUtil.sendMail(mailBean);
        System.out.println("------发送简单邮件结束-------");
        System.out.println("-------发送简单电子邮件-------");
        String receiveMail = "1340768350@qq.com";
        String suject = "测试邮件";
        String text = "测试文本信息";
    }
}
