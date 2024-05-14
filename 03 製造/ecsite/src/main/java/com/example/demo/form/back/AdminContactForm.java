package com.example.demo.form.back;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdminContactForm {
	private int id;
	private String your_name;
	private String title;
	private LocalDateTime created_at;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public String getYour_name() {
		return your_name;
	}
	public void setYour_name(String your_name) {
		this.your_name = your_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
