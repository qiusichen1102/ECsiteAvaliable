package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.bean.MakerList;
import com.example.demo.form.MakerListForm;
import com.example.demo.service.MakerListService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.example.demo.service" })
@MapperScan("com.example.demo.mapper")

public class MakerListController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpSession session;
	@Resource
	MakerListService makerListService;
	// list
		@GetMapping("/MakerList")
		public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
				@Param(value = "buttom") String buttom,
				ModelAndView modelAndView) throws Exception {

			//ページについてのボタンを処理する。
			if (buttom != null) {
				if (buttom.equals("次へ")) {
					pageNum++;
				}
				if (buttom.equals("前へ")) {
					pageNum--;
				}
			}
			//nullの場合、初期値を設置する。
			if (pageNum == null) {
				pageNum = 1;
			}

			if (pageSize == null) {
				pageSize = 5;

			}
			//メーカー数を所得する
			List<MakerList> makerListList1 = makerListService.searchMakerListList();
			int countMakerlist = makerListList1.size();
			modelAndView.addObject("countMakerlist", countMakerlist);
			modelAndView.addObject("makerListList1", makerListList1);
			//ページ数を所得する

			int pageTotalNum;
			   if (makerListList1.size() % pageSize == 0) {
			     pageTotalNum = makerListList1.size() / pageSize;
			   } else {
			     pageTotalNum = makerListList1.size() / pageSize + 1;
			   }
			//最大値と最小値の設定。
			if (pageNum < 1) {
				pageNum = 1;
			} else if(pageNum > pageTotalNum) {
				pageNum = pageTotalNum;
			}

			//ページを分割する
			PageHelper.startPage(pageNum, pageSize);
			List<MakerList> engineerListList1 = makerListService.searchMakerListList();
			modelAndView.addObject("engineerListList", engineerListList1);

			// オブジェクトの追加
			modelAndView.addObject("pageTotalNum", pageTotalNum);

			modelAndView.addObject("pageNum", pageNum);
			modelAndView.addObject("pageSize", pageSize);
            modelAndView.setViewName("makerlist");
          
            session.setAttribute("makerlist",makerListList1);

			return modelAndView;
		}

		//検索する
		@GetMapping("/makerList_search")
		public ModelAndView search_makerList(@Param(value = "keyword") String keyword,HttpServletRequest request,
				@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
				@Param(value = "buttom") String buttom,HttpSession session,
				ModelAndView modelAndView) {
			if (keyword != null) {
				request.setAttribute("keyword", keyword);
			}
			
			
			
			//ページについてのボタンを処理する。
			if (buttom != null) {
				if (buttom.equals("次へ")) {
					pageNum++;
				}
				if (buttom.equals("前へ")) {
					pageNum--;
				}
			}
			//nullの場合、初期値を設置する。
			if (pageNum == null) {
				pageNum = 1;
			}

			if (pageSize == null) {
				pageSize = 5;

			}
			//メーカー数を所得する
			List<MakerList> makerListList1 = makerListService.searchMakerListList();
			int countMakerlist = makerListList1.size();
			modelAndView.addObject("countMakerlist", countMakerlist);
			modelAndView.addObject("makerListList1", makerListList1);
			//ページ数を所得する

			int pageTotalNum;
			   if (makerListList1.size() % pageSize == 0) {
			     pageTotalNum = makerListList1.size() / pageSize;
			   } else {
			     pageTotalNum = makerListList1.size() / pageSize + 1;
			   }
			//最大値と最小値の設定。
			if (pageNum < 1) {
				pageNum = 1;
			} else if(pageNum > pageTotalNum) {
				pageNum = pageTotalNum;
			}

			//ページを分割する
			PageHelper.startPage(pageNum, pageSize);
			List<MakerList> makerListList = makerListService.search_manufacture(keyword);
			modelAndView.addObject("engineerListList1", makerListList);

			// オブジェクトの追加
			modelAndView.addObject("pageTotalNum", pageTotalNum);

			modelAndView.addObject("pageNum", pageNum);
			modelAndView.addObject("pageSize", pageSize);
            modelAndView.setViewName("makerlist");
			//----------------------------------
			
			//検索後データ
			
			modelAndView.addObject("engineerListList",makerListList);
			//レコード数
			int countMakerlist1 = makerListList.size();
			modelAndView.addObject("countMakerlist", countMakerlist1);
			modelAndView.setViewName("makerlist");
			

			session.setAttribute("makerlist",makerListList);

			return modelAndView;
			

		}

		// details by manufacture_id
		@GetMapping("/toMakerListDetails")
		public ModelAndView MakerListDetails(@RequestParam("id") String manufacture_id,Model model) {
			MakerList makerListDetails = makerListService.getMakerListByManufacture_id(manufacture_id);
			model.addAttribute("makerListDetails", makerListDetails);
			return new ModelAndView("makerListDetails");
		}


		// delete by manufacture_id
		@GetMapping("/makerList_delete")
		public ModelAndView MakerList_delete(@RequestParam(value = "id", required = false) String manufacture_id, ModelAndView modelAndView) {
			makerListService.makerList_delete(manufacture_id);
			System.out.println(manufacture_id + ":" + "削除しました");
			modelAndView.setViewName("forward:/MakerList");
			return modelAndView;
		}


		 //add
		@GetMapping("/add")
		  public String Addmaker(@ModelAttribute("form") MakerListForm makerListForm, Model model) {
		
			return ("makernew");
		}
		
		@PostMapping("/Toadd")
		public ModelAndView Addmaker(@ModelAttribute("form") @Valid MakerListForm makerListForm, BindingResult result, ModelAndView model) {
			// Nullチェック
			if(result.hasErrors()) {
				List<ObjectError> errorList = result.getAllErrors();
				// エラーメッセージを画面返却情報に入れる
				model.addObject("errorList",errorList);
				// 画面遷移の指定
				model.setViewName("makernew");
				return model;
			}
		
			// 主キー重複チェック
			if(makerListService.getMakerListByManufacture_id(makerListForm.getManufacture_id().toString()) != null) {
				// エラーメッセージを画面返却情報に入れる
				String errorName = "IDエラー";
				String errorMessage = messageSource.getMessage("manufacture.error.manufacture_id.duplication", new String[]{""}, Locale.JAPANESE);
		
				ObjectError objectError = new ObjectError(errorName, errorMessage);
				List<ObjectError> errorList = new ArrayList<ObjectError>();
				errorList.add(0, objectError);
				model.addObject("errorList",errorList);
				// 画面遷移の指定
				model.setViewName("makernew");
				return model;
			}
		
			MakerList makerList = new MakerList(makerListForm.getManufacture_id(), makerListForm.getManufacture_name(),
					makerListForm.getAbout_manufacture(),makerListForm.getPublication_status());
		
			makerListService.Addmaker(makerList);
			return new ModelAndView("redirect:/MakerList");
		
		}
		

		// update by Manufacture_id
		@GetMapping("/toMakerUpdate")
		public ModelAndView tomakerlist(@RequestParam("id") String manufacture_id, Model model) {
			MakerList makerUpdate=makerListService.getMakerListByManufacture_id(manufacture_id);
			model.addAttribute("makerListDetails", makerUpdate);
			return new ModelAndView("makerchange");
		}

		@PostMapping("/makerchange")
		public ModelAndView change(@ModelAttribute("form") @Valid MakerListForm makerListForm, BindingResult result, ModelAndView model) {
			// Nullチェック
			if(result.hasErrors()) {
				List<ObjectError> errorList = result.getAllErrors();
				// エラーメッセージを画面返却情報に入れる
				model.addObject("errorList",errorList);
				model.addObject("makerListDetails",makerListForm );
				// 画面遷移の指定
				model.setViewName("makerchange");
				return model;
			}

			MakerList makerList = new MakerList(makerListForm.getManufacture_id(), makerListForm.getManufacture_name(),
					makerListForm.getAbout_manufacture(),makerListForm.getPublication_status());
			makerListService.MakerList_update(makerList);
			return new ModelAndView("redirect:/MakerList");
		}


			@SuppressWarnings("unchecked")
			@GetMapping(value = "/makerListExcel")
			// ページから渡したのリスポンス
			public void makerListExcel(HttpServletResponse response) throws IOException {
				List<MakerList> makerList1 = (List<MakerList>) 
				session.getAttribute("makerlist");
		
				// エクセルを生成する
				makerListService.makerListExcel(response, makerList1);
			}
		
}
