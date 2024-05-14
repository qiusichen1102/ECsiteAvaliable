package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.back.Photo;
import com.example.demo.mapper.back.AdminPhotoMapper;

@Service
public class AdminPhotoServiceImpl implements AdminPhotoService{
	@Autowired
	private AdminPhotoMapper adminPhotoMapper;
	
	// すべての写真の名称、画像パス、IDを取得します。
	@Override
	public List<Photo> selectPhoto() {
		return adminPhotoMapper.selectPhoto();
	}
	
	//  IDに基づいてdelete_flgの値が変更する
	@Override
	public int deletePhoto(int id , LocalDateTime updateTime) {
		return adminPhotoMapper.deletePhoto(id,updateTime);
	}

	// 指定した写真名に一致する写真を検索します。
	@Override
	public List<Photo> photoFindByName(String name) {
		return adminPhotoMapper.photoFindByName(name);
	}

}
