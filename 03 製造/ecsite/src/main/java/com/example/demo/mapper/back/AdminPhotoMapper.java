package com.example.demo.mapper.back;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.bean.back.Photo;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminPhotoMapper {
	// すべての写真の名称、画像パス、IDを取得します
	@Select("SELECT name,imgpath,id FROM stock WHERE delete_flg = 0")
	public List<Photo> selectPhoto();

	// IDに基づいてdelete_flgの値が変更する
	@Update("UPDATE stock SET delete_flg = 1 ,updated_at=#{updated_at} WHERE id = #{id}")
	public int deletePhoto(int id, LocalDateTime updated_at);

	// 指定した写真名に一致する写真を検索します。
	@Select("Select * from stock where imgpath LIKE CONCAT('%', #{name}, '%')")
	public List<Photo> photoFindByName(String name);
}
