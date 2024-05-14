package com.example.demo.service.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.front.ContactImg;
import com.example.demo.form.front.ContactForm;
import com.example.demo.mapper.front.ContactMapper;
import com.example.demo.service.front.ContactService;

@Service
public class ContactServiceImpl implements ContactService{
	@Autowired
	ContactMapper contactMapper;

	@Override
	// お問い合わせフォームのデータを挿入します。
	public void contactIns(ContactForm contactForm) {
		contactMapper.contactIns(contactForm);
	}
	@Override
	// お問い合わせフォームの添付画像データを挿入します。
	public void contactImgIns(ContactImg contactImg) {
		contactMapper.contactImgIns(contactImg);
	}
}
