package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.front.Contact;
import com.example.demo.bean.front.ContactBak;
import com.example.demo.form.back.AdminContactForm;
import com.example.demo.form.front.ContactForm;
import com.example.demo.mapper.back.AdminContactMapper;

@Service
public class AdminContactServiceImpl implements AdminContactService{
	@Autowired
	private AdminContactMapper adminContactMapper;
	@Override
    // すべてのお問い合わせフォームを取得
	public List<Contact> contactAdmin() {
		return adminContactMapper.contactAdmin();
	}
	@Override
    // 指定したIDのお問い合わせフォームを取得
	public Contact contactSelectById(int id) {
		return adminContactMapper.contactSelectById(id);
	}
	@Override
	   // お問い合わせフォームを更新
	public int updateContactForm(ContactBak contactBak) {
		return adminContactMapper.updateContactForm(contactBak);
	}
	// お問い合わせフォームの画像を更新
	@Override
	public int updateContactFormImage(String filename,int id,LocalDateTime updated_at,int current_version) {
		return adminContactMapper.updateContactFormImage(filename,id,updated_at,current_version);
	}
	//バージョン制御による楽観ロック
	@Override
	public int deleteContactImg(int id,LocalDateTime updated_at) {
		return adminContactMapper.deleteContactImg(id,updated_at);
	}
	@Override
	// お問い合わせフォームを削除
	public int deleteContact(int id,LocalDateTime updated_at) {
		return adminContactMapper.deleteContact(id,updated_at);
	}
	@Override
    // 指定した名前のお問い合わせフォームを部分一致で取得
	public List<Contact> contactSelectByName(String your_name) {
		return adminContactMapper.contactSelectByName(your_name);
	}
	
	@Override
    // 指定したIDのversionを取得
	public int selectVersion(int id) {
		return adminContactMapper.selectVersion(id);
	}
	
	@Override
    // お問い合わせフォームの画像を削除
	public int selectVersionContactImg(int id) {
		return adminContactMapper.selectVersionContactImg(id);
	}

}
