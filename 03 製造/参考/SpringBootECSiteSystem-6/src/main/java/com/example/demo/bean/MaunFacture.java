package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaunFacture {
	private Integer manufacture_id;
	private String manufacture_name;
	private String about_manufacture;
	private Integer publication_status;

}
