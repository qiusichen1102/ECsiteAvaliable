package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.bean.front.Contact;
import com.example.demo.bean.front.ContactBak;
import com.example.demo.form.back.AdminContactForm;
import com.example.demo.form.front.ContactForm;

public interface AdminContactService {
	public List<Contact> contactAdmin();
	public Contact contactSelectById(int id);
	public int updateContactForm(ContactBak contactBak);
	public int updateContactFormImage(String filename,int id,LocalDateTime updated_at,int current_version);
	public int deleteContactImg(int id,LocalDateTime updated_at);
	public int deleteContact(int id,LocalDateTime updated_at);
	public List<Contact> contactSelectByName(String your_name);
    public int selectVersion(int id);
    public int selectVersionContactImg(int id);
}
