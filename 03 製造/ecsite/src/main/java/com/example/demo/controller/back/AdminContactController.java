package com.example.demo.controller.back;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.service.back.AdminContactService;
import com.example.demo.util.ImgDownloadUtil;
import com.example.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bean.front.Contact;
import com.example.demo.bean.front.ContactBak;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminContactController {
	private final AdminContactService adminContactService;
	private final HttpSession session;
	private final int pageSize = 10;
    @Autowired
    private MessageSource messageSource;
	@Autowired
	public AdminContactController(AdminContactService adminContactService, HttpSession session) {
		this.adminContactService = adminContactService;
		this.session = session;
	}
	
	@GetMapping("/contact_admin")
	public String Contact(@ModelAttribute("form") @Valid Contact contactForm,@RequestParam(defaultValue = "1") int pageNumber, Model model) {
		AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
		String errorMsg;
		//ログインしていない場合ログイン画面へ遷移する
		if(adminUser!=null){
		System.out.println(contactForm.getYour_name());
		List<Contact> contacts = new ArrayList<>();
		session.setAttribute("contact_001", messageSource.getMessage("contact_001", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_006", messageSource.getMessage("general_006", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_007", messageSource.getMessage("general_007", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_008", messageSource.getMessage("general_008", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_009", messageSource.getMessage("general_009", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_010", messageSource.getMessage("general_010", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_011", messageSource.getMessage("general_011", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_012", messageSource.getMessage("general_012", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_013", messageSource.getMessage("general_013", null,LocaleContextHolder.getLocale()));
		session.setAttribute("general_014", messageSource.getMessage("general_014", null,LocaleContextHolder.getLocale()));
		//検索欄はNULLの場合にすべての問い合わせ情報を表示する
		if(contactForm.getYour_name() == null) {
			contacts = adminContactService.contactAdmin();
			//ページネーション
			int totalItems = contacts.size();
			int totalPages = (totalItems + pageSize - 1) / pageSize;

			int fromIndex = (pageNumber - 1) * pageSize;
			int toIndex = Math.min(fromIndex + pageSize, totalItems);
			List<Contact> pagedContactList = contacts.subList(fromIndex, toIndex);

			session.setAttribute("contact", pagedContactList);
			session.setAttribute("totalPages", totalPages);
			session.setAttribute("currentPage", pageNumber);
			model.addAttribute("contact", pagedContactList);
		}
		//検索欄はNULLではない場合氏名でDBから情報を取得する
		else {
			contacts = adminContactService.contactSelectByName(contactForm.getYour_name());
			List<Contact> pagedContactList = contacts;

			session.setAttribute("contact", pagedContactList);
			session.setAttribute("totalPages", 1);
			session.setAttribute("currentPage", 1);
			model.addAttribute("contact", pagedContactList);
		}
		return "admin/contact_admin";
		}else{
			errorMsg="まだ登録していません、登録してください！";
			session.setAttribute("message",errorMsg);
			return "admin/login";
		}
	}
	
	@PostMapping("/contact_admin")
	public String Contact_post(@RequestParam(defaultValue = "1") int pageNumber,@RequestParam("_token") String token,@RequestParam("image")  MultipartFile file,
			@ModelAttribute("contact") Contact contact, Model model) {
	    //tokenがXV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO4の場合は「削除」を行う
		if("XV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO4".equals(token)) {
	    	System.out.println(contact.getId());
	    	adminContactService.deleteContactImg(Integer.parseInt(contact.getId()),LocalDateTime.now());
	    	adminContactService.deleteContact(Integer.parseInt(contact.getId()),LocalDateTime.now());
	    }
		//tokenがXV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO5の場合は「更新」を行う
	    else if("XV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO5".equals(token)){
	    	System.out.println("koushin");
	    	ContactBak contactBak = new ContactBak();
	    	if(!file.isEmpty()) {
	    	String path = handleFileUpload(file);
	        String filename = "./uploads/contact/"+path;
	        contactBak.setFile_name(filename);
	    	}
			contactBak.setAge(contact.getAge());
			contactBak.setContact(contact.getContact());
			contactBak.setEmail(contact.getEmail());
			contactBak.setTitle(contact.getTitle());
			contactBak.setGender(contact.getGender());
			contactBak.setId(Integer.parseInt(contact.getId()));
			contactBak.setYour_name(contact.getYour_name());
			contactBak.setUrl(contact.getUrl());
			int current_version = adminContactService.selectVersion(Integer.parseInt(contact.getId()));
			contactBak.setCurrent_version(current_version);
		    adminContactService.updateContactForm(contactBak);
		    if(contactBak.getFile_name() != null) {
		    int current_version2 = adminContactService.selectVersionContactImg(contactBak.getId());
		    adminContactService.updateContactFormImage(contactBak.getFile_name(), contactBak.getId(),LocalDateTime.now(),current_version2);
		    }
	    }
		//ページネーション
		List<Contact> contacts = adminContactService.contactAdmin();
		int totalItems = contacts.size();
		int totalPages = (totalItems + pageSize - 1) / pageSize;

		int fromIndex = (pageNumber - 1) * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalItems);
		List<Contact> pagedContactList = contacts.subList(fromIndex, toIndex);

		session.setAttribute("contact", pagedContactList);
		session.setAttribute("totalPages", totalPages);
		session.setAttribute("currentPage", pageNumber);
		model.addAttribute("contact", pagedContactList);

		return "admin/contact_admin";
	}


	@GetMapping("/contact_detail&{id}")
	public String contact_detail(@PathVariable String id, Model model) {
		//idによる詳細情報をDBから取得してから詳細画面に出す
		Contact contact = adminContactService.contactSelectById(Integer.parseInt(id));
		contact.setId(id);
		session.setAttribute("contact", contact);
		model.addAttribute("filename", contact.getFile_name());
		return "admin/contact_detail";
	}

	@GetMapping("/contact_edit")
	public String contactEdit1(@ModelAttribute("contact") Contact contact, @RequestParam("filename") String filename, Model model) {	
		//情報変更
		model.addAttribute("contact", contact);
	    model.addAttribute("filename", filename);
		return "admin/contact_edit";
	}

	@PostMapping("/contact_delete&{id}")
	public String contact_delete(@PathVariable String id,@RequestParam(defaultValue = "1") int pageNumber, Model model){
		//IDで削除を行う
		int current_version = adminContactService.selectVersion(Integer.parseInt(id));
		adminContactService.deleteContact(Integer.parseInt(id),LocalDateTime.now());
		List<Contact> contacts = adminContactService.contactAdmin();
		List<Contact> PagedContactList = (List<Contact>) PageUtil.PageCommon(contacts, pageSize, pageNumber, session, model);
		session.setAttribute("contact",PagedContactList);
		model.addAttribute("contact",PagedContactList);
		return "admin/contact_admin";
	}

	@GetMapping("/contact_delete&{id}")
	public String contact_deleteForGet(@PathVariable String id,@RequestParam(defaultValue = "1") int pageNumber, Model model){
		List<Contact> contacts = adminContactService.contactAdmin();
		List<Contact> PagedContactList = (List<Contact>) PageUtil.PageCommon(contacts, pageSize, pageNumber, session, model);
		session.setAttribute("contact",PagedContactList);
		model.addAttribute("contact",PagedContactList);
		return "admin/contact_admin";
	}
	//画像をアップロード
    public String handleFileUpload(@RequestParam("image")  MultipartFile file) {
        //ファイルをユニークにする
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + fileExtension;
        String projectRootPath = System.getProperty("user.dir");
        String uploadDirectory = projectRootPath + "/src/main/resources/static/uploads/contact/";

        // ファイルパス生成
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
    //ファイルの拡張子を取得する
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }
    
    //画像をストリームで表示する
    @GetMapping("/imgDownloadContact")
	public void imgDownloadContact(@RequestParam("contactname") String contactname, HttpServletResponse response) throws Exception {
		String contactName = URLDecoder.decode(contactname, "UTF-8");
		ImgDownloadUtil.downloadImageForContack( contactName, response);
	}
}
