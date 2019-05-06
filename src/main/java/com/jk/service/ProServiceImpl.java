package com.jk.service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Service;
import com.jk.pojo.ProBean;
import com.jk.dao.ProDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;

@Service
public class ProServiceImpl implements  ProService {
   @Autowired
    ProDao proDao;

/*

    public HashMap<String, Object> findLoginInfo(ProBean proBean, HttpServletRequest request) {
        HashMap<String,Object> result = new  HashMap<String,Object>();
        HttpSession session = request.getSession();

        ProBean userb=proDao.login(proBean.getName());
        if(userb==null) {
            result.put("code",2);
            result.put("msg","账号错误");
        }
        String password = userb.getPassword();
        */
/*  String md516 = Md5Util.getMd516(password);*//*

        if(!password.equals(proBean.getPassword())) {
            result.put("code",3);
            result.put("msg","密码错误");
            return result;
        }
        session.setAttribute(session.getId(), userb);
        result.put("code",0);
        result.put("msg","登录成功");
        return result;

    }
*/




    public void addreg(ProBean proBean) {

        proDao.addreg(proBean);
    }

    @Value("${spring.mail.name}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //发送普通邮件
    @Override
    public void sendSimpleMail(String to, String title, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        logger.info("邮件发送成功");

    }

    //发送带有附件的邮件
    @Override
    public void sendAttachmentsMail(String to, String title, String content, List<File> fileList) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);
            String fileName = null;
            for (File file:fileList) {
                fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
        logger.info("邮件发送成功");

    }

}
