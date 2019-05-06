package com.jk.service;

import com.jk.pojo.ProBean;

import java.io.File;
import java.util.List;

public interface ProService {

        void addreg(ProBean proBean);


      // HashMap<String, Object> findLoginInfo(ProBean proBean, HttpServletRequest request);

        //发送普通邮件
        void sendSimpleMail(String to, String title, String content);

        //发送带有附件的邮件
        void sendAttachmentsMail(String to, String title, String content, List<File> fileList);

}
