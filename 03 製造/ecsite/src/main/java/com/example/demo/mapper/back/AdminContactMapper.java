package com.example.demo.mapper.back;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.bean.front.Contact;
import com.example.demo.bean.front.ContactBak;
import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminContactForm;
import com.example.demo.form.front.ContactForm;

@Mapper
public interface AdminContactMapper {
    // すべてのお問い合わせフォームを取得
    @Select("SELECT cf.id, cf.your_name, cf.title, cf.created_at FROM contact_form cf WHERE cf.delete_flg = 0")
    public List<Contact> contactAdmin();

    // 指定したIDのお問い合わせフォームを取得
    @Select("SELECT cf.id, cf.your_name, cf.title, cf.created_at, cf.email, cf.gender, cf.contact, cf.age, cf.url, cfi.file_name FROM contact_form cf LEFT JOIN contact_form_image cfi ON cf.id = cfi.contact_form_id WHERE cf.id = #{id} AND cf.delete_flg = 0")
    public Contact contactSelectById(int id);

    // 指定した名前のお問い合わせフォームを部分一致で取得
    @Select("SELECT cf.id, cf.your_name, cf.title, cf.created_at, cf.email, cf.gender, cf.contact, cf.age, cf.url, cfi.file_name FROM contact_form cf LEFT JOIN contact_form_image cfi ON cf.id = cfi.contact_form_id WHERE cf.your_name LIKE CONCAT('%', #{your_name}, '%') AND cf.delete_flg = 0")
    public List<Contact> contactSelectByName(String your_name);

    // お問い合わせフォームを更新
    @Update("UPDATE contact_form SET your_name = #{your_name}, title = #{title}, email = #{email}, gender = #{gender}, contact = #{contact}, age = #{age}, url = #{url}, version = version + 1 WHERE id = #{id} AND version = #{current_version}")
    public int updateContactForm(ContactBak contactBak);
    // お問い合わせフォームを削除
    @Update("UPDATE contact_form SET delete_flg = 1,updated_at = #{updated_at} WHERE id = #{id}")
    public int deleteContact(int id,LocalDateTime updated_at);
    // 指定したIDのversionを取得
    @Select("SELECT version FROM contact_form WHERE id = #{id}")
    public int selectVersion(int id);
    
    // お問い合わせフォームの画像を更新
    @Update("UPDATE contact_form_image SET file_name = #{filename} , updated_at = #{updated_at},version = version + 1 WHERE contact_form_id = #{id} AND version = #{current_version}")
    public int updateContactFormImage(String filename, int id, LocalDateTime updated_at,int current_version);
    // お問い合わせフォームの画像を削除
    @Update("UPDATE contact_form_image SET delete_flg = 1,updated_at = #{updated_at} WHERE contact_form_id = #{id}")
    public int deleteContactImg(int id,LocalDateTime updated_at);
   //バージョン制御による楽観ロック
    @Select("SELECT version FROM contact_form_image WHERE contact_form_id= #{id}")
    public int selectVersionContactImg(int id);
}
