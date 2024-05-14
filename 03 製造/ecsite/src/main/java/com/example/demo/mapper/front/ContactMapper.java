package com.example.demo.mapper.front;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.example.demo.bean.front.ContactImg;
import com.example.demo.form.front.ContactForm;

@Mapper
public interface ContactMapper {
	// お問い合わせフォームのデータを挿入します。
	@Insert("insert into contact_form (your_name,title,email,url,gender,age,contact,created_at,updated_at,delete_flg,version)VALUES(#{your_name},#{title},#{email},#{url},#{gender},#{age},#{contact},#{created_at},#{updated_at},#{delete_flg},#{version})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void contactIns(ContactForm contactForm);

	// お問い合わせフォームの添付画像データを挿入します。
	@Insert("insert into contact_form_image (contact_form_id,file_name,created_at,updated_at,delete_flg,version)VALUES(#{contact_form_id},#{file_name},#{created_at},#{updated_at},#{delete_flg},#{version})")
	public void contactImgIns(ContactImg contactImg);
}
