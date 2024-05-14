package com.example.demo.bean.front;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ContactImg {
	@Id
	private int id;
	private int contact_form_id;
	private String file_name;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private boolean delete_flg;
	@Version
	private int version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getContact_form_id() {
		return contact_form_id;
	}

	public void setContact_form_id(int contact_form_id) {
		this.contact_form_id = contact_form_id;
	}
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public boolean isDelete_flg() {
		return delete_flg;
	}

	public void setDelete_flg(boolean delete_flg) {
		this.delete_flg = delete_flg;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}


}
