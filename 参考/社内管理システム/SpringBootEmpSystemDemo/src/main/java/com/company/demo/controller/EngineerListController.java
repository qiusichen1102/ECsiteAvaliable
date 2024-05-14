package com.company.demo.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.company.demo.bean.EngineerList;
import com.company.demo.common.Common;
import com.company.demo.form.EngineerListForm;
import com.company.demo.service.EngineerListService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.company.demo.service" })
@ComponentScan({ "com.company.demo.common" })
@MapperScan("com.company.demo.mapper")
public class EngineerListController {
	@Resource
	EngineerListService engineerListService;
	@Resource
	HttpSession session;
	@Resource
	Common common;
	// list
	@SuppressWarnings("unused")
	@GetMapping("/engineerListList")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button,
			ModelAndView modelAndView) throws IOException {
		session.setAttribute("imported",false);//エクセル動作の導入したの記録
		session.setAttribute("searched", false);//エクセル動作の検索したの記録
		List<EngineerList> engineerListList1 = engineerListService.searchEngineerListList();
		//ページ分割の設定値を取得する
				Map<String,Object> result = engineerListService.pageTurn(pageNum,engineerListList1,button);
				//ページを分割する操作
						PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
				//pagehelper応用必要のセレクト
						List<EngineerList> engineerListListPage = engineerListService.searchEngineerListList();

				int countEmployee = engineerListList1.size();

				if(engineerListList1==null)
				{session.setAttribute("engineer", false);
				}else{
					session.setAttribute("engineer", true);
					}
				session.setAttribute("engineerListList", engineerListList1);//リスト記録
				modelAndView.addObject("engineerListList",engineerListListPage );
				modelAndView.addObject("countEmployee",countEmployee);
				modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
				modelAndView.addObject("pageNum", result.get("pageNum"));
				modelAndView.addObject("pageSize", result.get("pageSize"));
				modelAndView.addObject("firstPage", result.get("firstPage"));
				modelAndView.addObject("lastPage", result.get("lastPage"));

		return modelAndView;
	}

	@GetMapping("/engineerList_search")
	public ModelAndView search_List(@Param(value = "month")String month,@Param(value = "keyword") String keyword,HttpServletRequest request,ModelAndView modelAndView) {
		session.setAttribute("searched",false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		//検索後データ
		List<EngineerList> engineerListList = engineerListService.search_empCd(keyword, month);
		modelAndView.addObject("engineerListList",engineerListList);
		session.setAttribute("engineerListList", engineerListList);
		session.setAttribute("searched",true);//検索記録
		//レコード数
		int countEmployee = engineerListList.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("engineerListList");
		return modelAndView;

	}
	// delete by empCd
	@GetMapping("/engineerList_delete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		engineerListService.engineerList_delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/engineerListList");
		return modelAndView;
	}
	// update by empCd
	@GetMapping("/toEngineerListUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd,Model model) {
		EngineerList engineerListUpdate = engineerListService.getEngineerListByEmpCd(empCd);
		model.addAttribute("engineerListUpdate", engineerListUpdate);
		return new ModelAndView("engineerListUpdate");
	}

	@PostMapping("/engineerListUpdate")
	public ModelAndView update(EngineerListForm engineerListForm,Model model) {
		Common common = new Common();
		Date workingDate = common.getDate(engineerListForm.getWorkingDate());

		EngineerList engineerList = new EngineerList(engineerListForm.getEmpCd(),workingDate,
				engineerListForm.getExperience(),engineerListForm.getJapanese(),engineerListForm.getTechlanguage(),engineerListForm.getBasicDesign(),engineerListForm.getDetailDesign(),
				engineerListForm.getProduce(),engineerListForm.getTest(),engineerListForm.getProject(),engineerListForm.getLevel(),engineerListForm.getNewEmployee(),engineerListForm.getEngineerListComment());
		engineerListService.EngineerList_update(engineerList);
		return new ModelAndView("redirect:/engineerListList");
	}
	// details by empCd
	@GetMapping("/toEngineerListDetails")
	public ModelAndView EngineerListDetails(@RequestParam("id") String empCd,Model model) {
		EngineerList engineerListDetails = engineerListService.getEngineerListByEmpCd(empCd);
		model.addAttribute("engineerListDetails", engineerListDetails);
		return new ModelAndView("engineerListDetails");
	}
	// ↓ excelExport
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/eexcelExport")
	public void excelExport(HttpServletResponse response) throws IOException {
		List<EngineerList> engineerList = null;
		if ((boolean) session.getAttribute("searched")) {
			engineerList = (List<EngineerList>) session.getAttribute("searchedList");
			session.setAttribute("searched", false);//リセット
		} else {
			engineerList = (List<EngineerList>) session.getAttribute("engineerListList");
		}
		engineerListService.excelExport(response, engineerList, session);
	}

	// ↓ errorExcelExport
		@SuppressWarnings("unchecked")
		@GetMapping(value = "/eerrorExcelExport")
		public void errorExcelExport(HttpServletResponse response) throws IOException {
			 List<EngineerList>	engineerList = (List<EngineerList>) session.getAttribute("errorList");

			 engineerListService.excelExport(response, engineerList, session);
			session.setAttribute("imported", false);
			session.removeAttribute("errorList");//セッションのエラーリストを削除して、次の操作を影響しないように

		}


	// ↓ excelImport

	@RequestMapping(value = "/eexcelImport")
	public ModelAndView excelImport(@RequestParam(value = "filename")MultipartFile file, HttpSession session,
			ModelAndView mav,HttpServletResponse response ) throws IOException {
		 //ファイルのサイズを判断する
		long size = file.getSize();
	     if(size > 10000000)
	     {
	    	 response.setContentType("text/html; charset=utf-8");
				PrintWriter out;

					out = response.getWriter();
					out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
					out.print("<script>location='engineerListList';</script>");
					out.flush();

				mav.setViewName("redirect:/engineerListList");
				return mav;
	       }

		session.setAttribute("imported",false);
		List<EngineerList> errorList= new ArrayList<>();//エラーデータリスト
		List<EngineerList> importList = new ArrayList<>();//導入されたデータリスト
		int wnt = 0;//エラー数
		int cnt = 0;//正常数
		String fileName = file.getOriginalFilename();
		String xlsxType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String xlsType="application/vnd.ms-excel";
		System.out.println(file.getContentType());
		if(!(xlsType.equals(file.getContentType())) && !(xlsxType.equals(file.getContentType()))) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.print("<script>alert('Excelファイルを選択してください');</script>");
			out.print("<script>location='engineerListList';</script>");
			out.flush();
			mav.setViewName("redirect:/engineerListList");
			return mav;
		}
		if(file.isEmpty()) {	//ファイル選択なしにインポートボタンを押す場合
			mav.setViewName("redirect:/engineerListList");
			return mav;
		}else {

			try {
				Map<String,List<EngineerList>> map = engineerListService.batchImport(fileName, file);
				errorList = map.get("errorList");
				importList = map.get("importList");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			if(errorList!=null) {
				wnt = errorList.size();//エラー数
				mav.addObject("errorList",errorList);
				session.setAttribute("errorList", errorList);
			}
			if(importList!=null) {
				cnt = importList.size();//正常数
				mav.addObject("importList", importList);
			}
			session.setAttribute("imported",true);
			mav.addObject("wnt",wnt);//エラー数
			mav.addObject("countEmployee", wnt);//インポートページのダウンロードボタンを隠すの条件
			mav.addObject("cnt",cnt);//正常数
			mav.setViewName("/engineerImport");
			return mav;
		}
	}

}

