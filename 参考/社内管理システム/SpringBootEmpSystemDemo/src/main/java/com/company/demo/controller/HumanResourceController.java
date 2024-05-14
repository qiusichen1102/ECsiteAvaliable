package com.company.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.company.demo.bean.HumanResource;
import com.company.demo.common.Common;
import com.company.demo.form.HumanResourceForm;
import com.company.demo.service.HumanResourceService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.company.demo.service" })
@ComponentScan({ "com.company.demo.common" })
@MapperScan("com.company.demo.mapper")
public class HumanResourceController {
	@Resource
	HumanResourceService humanResourceService;
	@Resource
	HttpSession session;

	@Resource
	Common common;

	// list
	@SuppressWarnings("unused")
	@GetMapping("/humanResourceList")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button, ModelAndView modelAndView) throws Exception {

		// ページについてのボタンを処理する。
		session.setAttribute("imported", false);// エクセル動作の導入したの記録
		session.setAttribute("searched", false);// エクセル動作の検索したの記録
		List<HumanResource> HumanResourceList1 = humanResourceService.searchHumanResourceList();
		//ページ分割の設定値を取得する
				Map<String,Object> result = humanResourceService.pageTurn(pageNum,HumanResourceList1,button);
				//ページを分割する操作
				PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
				//pagehelper応用必要のセレクト
				List<HumanResource> humanResourceListPage = humanResourceService.searchHumanResourceList();


		int countEmployee = HumanResourceList1.size();
		if( HumanResourceList1 ==null)
		{
			session.setAttribute("humanResource", false);
		}else {
			session.setAttribute("humanResource", true);
		}

		session.setAttribute("HumanResourceList", HumanResourceList1);// リスト記録
		modelAndView.addObject("HumanResourceList",humanResourceListPage );
		modelAndView.addObject("countEmployee",countEmployee);
		modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
		modelAndView.addObject("pageNum", result.get("pageNum"));
		modelAndView.addObject("pageSize", result.get("pageSize"));
		modelAndView.addObject("firstPage", result.get("firstPage"));
		modelAndView.addObject("lastPage", result.get("lastPage"));

		return modelAndView;
	}

	@GetMapping("/HumanResource_search")
	public ModelAndView search_List(@Param(value = "keyword") String keyword, HttpServletRequest request,
			ModelAndView modelAndView) {
		session.setAttribute("searched", false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		// 検索後データ
		List<HumanResource> HumanResourceList = humanResourceService.search_empCd(keyword);
		modelAndView.addObject("HumanResourceList", HumanResourceList);
		session.setAttribute("HumanResourceList", HumanResourceList);
		session.setAttribute("searched", true);// 検索記録
		// レコード数
		int countEmployee = HumanResourceList.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("humanResourceList");
		return modelAndView;

	}

	// delete by empCd
	@GetMapping("/HumanResource_delete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		humanResourceService.HumanResource_delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/humanResourceList");
		return modelAndView;
	}





	@GetMapping("/toHumanResourceUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd, Model model) {
		HumanResource HumanResource = humanResourceService.getHumanResourceByEmpCd(empCd);
		model.addAttribute("HumanResource", HumanResource);
		return new ModelAndView("humanResourceUpdate");
	}

	@PostMapping("/HumanResourceUpdate")
	public ModelAndView update(HumanResourceForm HumanResourceForm, Model model) {
		HumanResource HumanResource = new HumanResource(HumanResourceForm.getEmpCd(),
				HumanResourceForm.getBasicSalary(), HumanResourceForm.getSalaryTimes(),
				HumanResourceForm.getInsuranceLine(), HumanResourceForm.getHealthInsurance(),
				HumanResourceForm.getRetirementPay(), HumanResourceForm.getDiseaseInturance(),
				HumanResourceForm.getHumanResourceComment());
		humanResourceService.HumanResource_update(HumanResource);
		return new ModelAndView("redirect:/humanResourceList");
	}

	// details by empCd
	@GetMapping("/toHumanResourceDetails")
	public ModelAndView WorkInsuranceDetails(@RequestParam("id") String empCd, Model model) {
		HumanResource HumanResource = humanResourceService.getHumanResourceByEmpCd(empCd);
		model.addAttribute("HumanResourceDetails", HumanResource);
		return new ModelAndView("humanResourceDetails");
	}

	// **********************************************************
	// ↓ excelExport

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/humanResource_excelExport")
	public void humanResource_excelExport(HttpServletResponse response,ModelAndView mav, Object countEmployee) throws IOException {
		List<HumanResource> HumanResourceList = null;

		int export=0;
		if(countEmployee==null) {
			mav.addObject("countEmployee",countEmployee);
			mav.addObject("countEmployee", export);
			System.out.println("panduan");
	}
		else {
		System.out.println("2222");
	}

		if ((boolean) session.getAttribute("searched")) {
			HumanResourceList = (List<HumanResource>) session.getAttribute("HumanResourceList");
			session.setAttribute("searched", false);//リセット
		} else {
			HumanResourceList = (List<HumanResource>) session.getAttribute("HumanResourceList");
		}
		humanResourceService.excelExport(response, HumanResourceList, session);
	}

	//**********************************************************
			// ↓ errorExcelExport
			@SuppressWarnings("unchecked")
			@GetMapping(value = "/errorHumanResourceExport")
			public void errorExcelExport(HttpServletResponse response) throws IOException {
				List<HumanResource> HumanResourceList = (List<HumanResource>) session.getAttribute("errorList");
				session.setAttribute("searched", false);
				humanResourceService.excelExport(response, HumanResourceList, session);
				session.setAttribute("imported", false);
				session.removeAttribute("errorList");//セッションのエラーリストを削除して、次の操作を影響しないように
			}




	// **********************************************************
	// ↓ excelImport

	@RequestMapping(value = "/humanResource_excelImport")
	public ModelAndView humanResource_excelImport(@RequestParam(value = "filename") MultipartFile file, ModelAndView mav
			 ,HttpServletResponse response ) throws IOException {
		 //ファイルのサイズを判断する
		long size = file.getSize();
	     if(size > 10000000)
	     {
	    	 response.setContentType("text/html; charset=utf-8");
				PrintWriter out;

					out = response.getWriter();
					out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
					out.print("<script>location='humanResourceList';</script>");
					out.flush();

				mav.setViewName("redirect:/humanResourceList");
				return mav;
	       }
		session.setAttribute("imported", false); // セッションのインポート記録をリセットして、次の操作を影響しないように
		List<HumanResource> errorList = new ArrayList<>();// エラーデータリスト
		List<HumanResource> importList = new ArrayList<>();// 導入されたデータリスト
		int wnt = 0;// エラー数
		int cnt = 0;// 正常数
		String fileName = file.getOriginalFilename();
		String xlsxType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String xlsType="application/vnd.ms-excel";
		System.out.println(file.getContentType());
		if(!(xlsType.equals(file.getContentType())) && !(xlsxType.equals(file.getContentType()))) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.print("<script>alert('Excelファイルを選択してください');</script>");
			out.print("<script>location='humanResourceList';</script>");
			out.flush();

			mav.setViewName("redirect:/humanResourceList");
			return mav;
		}



		if (file.isEmpty()) { // ファイル選択なしにインポートボタンを押す場合

			mav.setViewName("redirect:/HumanResourceList");
			return mav;
		} else {

			try {
				Map<String, List<HumanResource>> map = humanResourceService.batchImport(fileName, file);
				errorList = map.get("errorList");
				importList = map.get("importList");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			if (errorList != null) {
				wnt = errorList.size();// エラー数
				mav.addObject("errorList", errorList);
				session.setAttribute("errorList", errorList);
			}
			if (importList != null) {
				cnt = importList.size();// 正常数
				mav.addObject("importList", importList);
			}
			// インポートボタン と 導出ボタンを分けるため設定の記録
			session.setAttribute("imported", true);
			mav.addObject("wnt", wnt);// エラー数
			mav.addObject("countEmployee", wnt);
			mav.addObject("cnt", cnt);// 正常数
			mav.setViewName("/humanResourceExcelImport");
			return mav;
		}
	}



}
