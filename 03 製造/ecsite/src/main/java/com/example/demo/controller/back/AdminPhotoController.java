package com.example.demo.controller.back;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.example.demo.form.back.AdminLoginFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.bean.back.Photo;
import com.example.demo.bean.front.Stock;
import com.example.demo.service.back.AdminPhotoService;
import com.example.demo.service.back.AdminStockServiceImpl;
import com.example.demo.util.ImgDownloadUtil;

@Controller
public class AdminPhotoController {
	@Autowired
	private AdminPhotoService adminPhotoService;
    @Autowired
    private AdminStockServiceImpl adminStockServiceImpl;
	@Autowired
	private HttpSession session;

	@GetMapping("photo")
	public String Photo(Model model,@RequestParam(value = "name", required = false) String name) {
		String errorMsg;
		AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
		if(adminUser!=null){
			if(name!=null && !name.equals("")){
				List<Photo> photos = adminPhotoService.photoFindByName(name);
				for (Photo photo : photos) {
					String filename = photo.getImgpath().substring(photo.getImgpath().lastIndexOf("/") + 1);
					int index = filename.indexOf("&");
					if (index != -1) {
						filename= filename.substring(index + 1);
					}
					photo.setFilename(filename);
				}
				model.addAttribute("photo", photos);
			}else{
				List<Photo> photo = adminPhotoService.selectPhoto();
				List<Photo> photoTemp = new ArrayList<>();
				System.out.println("Get");
				for (Photo p : photo) {
					//商品名を取得して商品画像リストに入れる
					int startIndex = 0;
					if (p.getImgpath().indexOf("/") > 0) {
						startIndex = p.getImgpath().lastIndexOf("/") + 1;
					}
					String extractedName = p.getImgpath().substring(startIndex, p.getImgpath().length());
					/*
					 * if (extractedName.contains("&")) { int start = extractedName.indexOf("&") +
					 * 1; int end = extractedName.lastIndexOf("."); if (start < end && start > 0 &&
					 * end > 0) { extractedName=extractedName.substring(start, end); } }
					 */
					 int index = extractedName.indexOf("&");
					 if (index != -1) {
						 extractedName= extractedName.substring(index + 1);
				        } 
					p.setFilename(extractedName);
					photoTemp.add(p);
				}
				model.addAttribute("photo", photoTemp);
			}

			return "admin/photo";
		}else{
			errorMsg="まだ登録していません、登録してください！";
			session.setAttribute("message",errorMsg);
			return "admin/login";
		}
	}

    //商品画像を削除する
	@GetMapping("/photo_delete&{id}")
	public String PhotoDelete(@PathVariable String id, Model model) {
		adminPhotoService.deletePhoto(Integer.parseInt(id), LocalDateTime.now());
		List<Photo> photo = adminPhotoService.selectPhoto();
		List<Photo> photoTemp = new ArrayList<>();
		for (Photo p : photo) {
			int startIndex = 0;
			if (p.getImgpath().indexOf("/") > 0) {
				startIndex = p.getImgpath().lastIndexOf("/") + 1;
			}
			String extractedName = p.getImgpath().substring(startIndex, p.getImgpath().length());
			 int index = extractedName.indexOf("&");
			 if (index != -1) {
				 extractedName= extractedName.substring(index + 1);
		        } 
			p.setFilename(extractedName);
			photoTemp.add(p);
		}
		model.addAttribute("photo", photoTemp);
		return "admin/photo";
	}
	
    //画像をストリームで表示する
	@GetMapping("/imgDownloadPhoto")
	public void imgDownloadPhoto(@RequestParam("photoId") String photoId, HttpServletResponse response) throws Exception {
		String decodedStockid = URLDecoder.decode(photoId, "UTF-8");
		Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(decodedStockid));
		ImgDownloadUtil.downloadImage( stock.getImgpath(), response);
	}
    
}
