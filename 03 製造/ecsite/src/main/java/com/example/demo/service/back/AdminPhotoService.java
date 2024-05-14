package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.bean.back.Photo;

public interface AdminPhotoService {
	public List<Photo> selectPhoto();
	public int deletePhoto(int id, LocalDateTime updateTime);
	public List<Photo> photoFindByName(String name);
}
