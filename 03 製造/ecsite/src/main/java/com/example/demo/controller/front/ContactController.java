package com.example.demo.controller.front;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.bean.front.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.bean.front.ContactImg;
import com.example.demo.form.front.ContactForm;
import com.example.demo.service.front.ContactServiceImpl;

@Controller
public class ContactController {

    @Autowired
    private ContactServiceImpl contactServiceImpl;
    @Autowired
    private HttpSession session;
    @Autowired
    private MessageSource messageSource;
    
    // お問い合わせページにアクセス
    @GetMapping("/contact")
    public String contactIn(){
        String errorMsg;
        User user = (User) session.getAttribute("user");
        errorMsg=messageSource.getMessage("contact_f_001", null,LocaleContextHolder.getLocale());
        session.setAttribute("general_031",messageSource.getMessage("general_031", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_032",messageSource.getMessage("general_032", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_033",messageSource.getMessage("general_033", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_034",messageSource.getMessage("general_034", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_035",messageSource.getMessage("general_035", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_035",messageSource.getMessage("general_035", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_036",messageSource.getMessage("general_036", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_037",messageSource.getMessage("general_037", null,LocaleContextHolder.getLocale()));
        session.setAttribute("error_002",messageSource.getMessage("error_002", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_038",messageSource.getMessage("general_038", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_039",messageSource.getMessage("general_039", null,LocaleContextHolder.getLocale()));
        session.setAttribute("contact_002",messageSource.getMessage("contact_002", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_040",messageSource.getMessage("general_040", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_042",messageSource.getMessage("general_042", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_041",messageSource.getMessage("general_041", null,LocaleContextHolder.getLocale()));
        session.setAttribute("general_043",messageSource.getMessage("general_043", null,LocaleContextHolder.getLocale()));
        if (user != null) {
            return "front/contact";
        }        
        session.setAttribute("message",errorMsg);
        String token = "5aXRj36XqsUcb4HdfUPfO3AhMz4Hkt2MCLr7CH6g";
        session.setAttribute("_token", token);
        return "front/login";
    }
    
    // お問い合わせの送信処理
    @PostMapping("/contact")
    public RedirectView contact(@ModelAttribute("form") @Valid ContactForm contactForm,@RequestParam("image")  MultipartFile file){
        contactForm.setCreated_at(LocalDateTime.now());
        contactForm.setUpdated_at(LocalDateTime.now());
        contactServiceImpl.contactIns(contactForm);
        String path = handleFileUpload(file);
        String filename = "./uploads/contact/"+path;
        ContactImg contactImg = new ContactImg();
        contactImg.setContact_form_id(contactForm.getId());
        contactImg.setFile_name(filename);
        contactImg.setCreated_at(LocalDateTime.now());
        contactImg.setUpdated_at(LocalDateTime.now());
        contactImg.setDelete_flg(false);
        contactImg.setVersion(1);
        contactServiceImpl.contactImgIns(contactImg);
        return new RedirectView("/index2");
    }
    
    // ファイルのアップロード処理
    public String handleFileUpload(@RequestParam("image")  MultipartFile file) {
        // ファイルを一意にする
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + fileExtension;
        String projectRootPath = System.getProperty("user.dir");
        String uploadDirectory = projectRootPath + "/src/main/resources/static/uploads/contact/";

        // ファイルパスを生成
        String filePath = Paths.get(uploadDirectory, newFileName).toString();
        File targetFile = new File(filePath);

        try {
            file.transferTo(targetFile);
            System.out.println("ファイル生成成功");
        } catch (IOException e) {
            System.out.println("ファイル生成失敗: " + e.getMessage());
        }

        return newFileName;
    }
    
    // ファイルの拡張子を取得
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }
}
