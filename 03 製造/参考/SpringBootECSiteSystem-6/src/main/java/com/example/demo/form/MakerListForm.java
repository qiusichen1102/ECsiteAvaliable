package com.example.demo.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MakerListForm {

	@NotNull(message = "{manufacture.error.manufacture_id.notEmpty}")
	private Integer manufacture_id;

	@NotEmpty(message = "{manufacture.error.manufacture_name.notEmpty}")
	private String manufacture_name;

	@NotEmpty(message = "{manufacture.error.about_manufacture.notEmpty}")
	private String about_manufacture;

	@NotNull(message = "{manufacture.error.publication_status.notEmpty}")
	private Integer publication_status;

	public Integer getManufacture_id() {
		return manufacture_id;
	}
	public void setManufacture_id(Integer manufacture_id) {
		this.manufacture_id = manufacture_id;
	}
	public String getManufacture_name() {
		return manufacture_name;
	}
	public void setManufacture_name(String manufacture_name) {
		this.manufacture_name = manufacture_name;
	}
	public String getAbout_manufacture() {
		return about_manufacture;
	}
	public void setAbout_manufacture(String about_manufacture) {
		this.about_manufacture = about_manufacture;
	}
	public Integer getPublication_status() {
		return publication_status;
	}
	public void setPublication_status(Integer publication_status) {
		this.publication_status = publication_status;
	}

	
	
/*		private String newMakerList;
	private String makerListComment;

	public String getNewMakerList() {
		return newMakerList;
	}
	public void setNewMakerList(String newMakerList) {
		this.newMakerList = newMakerList;
	}
	public String getMakerListComment() {
		return makerListComment;
	}
	public void setMakerListComment(String makerListComment) {
		this.makerListComment = makerListComment;
	}

	public  MakerListForm() {}*/

}
