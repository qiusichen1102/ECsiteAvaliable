package com.example.demo.controller.back;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bean.front.Contact;
import com.example.demo.bean.front.User;
import com.example.demo.form.back.AdminContactForm;
import com.example.demo.service.back.AdminContactService;
import com.example.demo.service.back.AdminUserService;

@Controller
public class AdminUserController {
	private final AdminUserService adminUserService;
	private final HttpSession session;
	private final int pageSize = 10;

	@Autowired
	public AdminUserController(AdminUserService adminUserService, HttpSession session) {
		this.adminUserService = adminUserService;
		this.session = session;
	}

	@GetMapping("/user")
	public String User(@ModelAttribute("form") @Valid User userForm, @RequestParam(defaultValue = "1") int pageNumber,
			Model model) {
		String errorMsg;
		AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
		if (adminUser != null) {
			if ((userForm.getEmail() != null && !userForm.getEmail().equals("")) && (userForm.getName() != null
					&& !userForm.getName().equals(""))) {
				// 名前とメールアドレスでユーザーを検索する
				List<User> users = adminUserService.selectUserByAll(userForm.getName(), userForm.getEmail());
				List<User> pagedContactList = (List<User>) PageUtil.PageCommon(users, pageSize, pageNumber, session, model);
				session.setAttribute("backUser", pagedContactList);
				model.addAttribute("backUser", pagedContactList);
			}else if((userForm.getEmail() != null && !userForm.getEmail().equals("")) || (userForm.getName() != null
					&& !userForm.getName().equals(""))){
				// メールアドレスまたは名前でユーザーを検索する
				List<User> usersByEmail = adminUserService.selectUserByEmail(userForm.getEmail());
				List<User> usersByName = adminUserService.selectUserByName(userForm.getName());
				if(!usersByName.isEmpty() && usersByEmail.isEmpty()){
					List<User> pagedContactList = (List<User>) PageUtil.PageCommon(usersByName, pageSize, pageNumber, session, model);
					session.setAttribute("backUser", pagedContactList);
					model.addAttribute("backUser", pagedContactList);
				}else if(usersByName.isEmpty() && !usersByEmail.isEmpty()){
					List<User> pagedContactList = (List<User>) PageUtil.PageCommon(usersByEmail, pageSize, pageNumber, session, model);
					session.setAttribute("backUser", pagedContactList);
					model.addAttribute("backUser", pagedContactList);
				}
			}
			else {
				// メールアドレスまたは名前でユーザーを検索する
				List<User> users = adminUserService.selectUser();
				List<User> pagedContactList = (List<User>) PageUtil.PageCommon(users, pageSize, pageNumber, session, model);
				session.setAttribute("backUser", pagedContactList);
				model.addAttribute("backUser", pagedContactList);
			}
			return "admin/user";
		} else {
			errorMsg = "まだ登録していません、登録してください！";
			session.setAttribute("message", errorMsg);
			return "admin/login";
		}
	}

	@PostMapping("/user")
	public String UserPost(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam("_token") String token,
			@ModelAttribute("user") User user, Model model) {
		if ("XV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO4".equals(token)) {
			// ユーザーを更新する
			int current_version = adminUserService.selectUpdateUserVersion(user.getId());
			adminUserService.updateUser(user.getName(), user.getEmail(), user.getId(), LocalDateTime.now(),current_version);
		} else if ("XV7tF73lP1V31unyV3Oxkj2siVujlZtLdM6snqO5".equals(token)) {
			// ユーザーを削除する
			System.out.println("postuser" + user.getId());
			adminUserService.deleteUser(user.getId(),LocalDateTime.now());
		}
		List<User> users = adminUserService.selectUser();
		List<User> pagedContactList = (List<User>) PageUtil.PageCommon(users, pageSize, pageNumber, session, model);
		session.setAttribute("backUser", pagedContactList);
		model.addAttribute("backUser", pagedContactList);
		return "admin/user";
	}

	@GetMapping("/user_detail&{id}")
	public String user_detail(@PathVariable String id, Model model) {
		// ユーザーの詳細を表示する
		User user = adminUserService.selectUserByID(Integer.parseInt(id));
		user.setId(Integer.parseInt(id));
		System.out.println("detailuser" + user.getId());
		model.addAttribute("backUser", user);
		return "admin/user_detail";
	}

	@GetMapping("/user_edit")
	public String userEdit(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("user", user);
		return "admin/user_edit";
	}

}
