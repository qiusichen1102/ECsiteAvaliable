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

import com.company.demo.bean.WorkInsurance;
import com.company.demo.common.Common;
import com.company.demo.form.WorkInsuranceForm;
import com.company.demo.service.WorkInsuranceService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.company.demo.service" })
@MapperScan("com.company.demo.mapper")
public class WorkInsuranceController {
	@Resource
	WorkInsuranceService workInsuranceService;
	@Resource
	HttpSession session;


	// list
	@SuppressWarnings("unused")
	@GetMapping("/workInsuranceList")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button,
			ModelAndView modelAndView) throws Exception {
		//ページについてのボタンを処理する。
		session.setAttribute("imported", false);//エクセル動作の導入したの記録
		session.setAttribute("searched", false);//エクセル動作の検索したの記録
		List<WorkInsurance> WorkInsuranceList1 = workInsuranceService.searchWorkInsuranceList();
		//ページ分割の設定値を取得する
	Map<String,Object> result = workInsuranceService.pageTurn(pageNum,WorkInsuranceList1 ,button);
				//ページを分割する操作
	PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
	//pagehelper応用必要のセレクト
	List<WorkInsurance> workingStatusListPage =  workInsuranceService.searchWorkInsuranceList();
		//社員数を所得する

		int countEmployee = WorkInsuranceList1.size();

		if(WorkInsuranceList1==null)
		{
			session.setAttribute("workInsurance", false);
		}else {
			session.setAttribute("workInsurance", true);
		}


		session.setAttribute("WorkInsuranceList", WorkInsuranceList1);//リスト記録
		modelAndView.addObject("WorkInsuranceList", workingStatusListPage );
		modelAndView.addObject("countEmployee",countEmployee);
		modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
		modelAndView.addObject("pageNum", result.get("pageNum"));
		modelAndView.addObject("pageSize", result.get("pageSize"));
		modelAndView.addObject("firstPage", result.get("firstPage"));
		modelAndView.addObject("lastPage", result.get("lastPage"));

		return modelAndView;
	}

	@GetMapping("/WorkInsurance_search")
	public ModelAndView search_List(@Param(value = "keyword") String keyword, HttpServletRequest request,
			ModelAndView modelAndView) {
		session.setAttribute("searched", false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		//検索後データ
		List<WorkInsurance> WorkInsuranceList = workInsuranceService.search_empCd(keyword);
		modelAndView.addObject("WorkInsuranceList", WorkInsuranceList);
		session.setAttribute("WorkInsuranceList", WorkInsuranceList);
		session.setAttribute("searched", true);//検索記録
		//レコード数
		int countEmployee = WorkInsuranceList.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("workInsurancelist");
		return modelAndView;

	}

	// delete by empCd
	@GetMapping("/WorkInsurance_delete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		workInsuranceService.WorkInsurance_delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/workInsuranceList");
		return modelAndView;
	}

	// update by empCd
	@GetMapping("/toWorkInsuranceUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd, Model model) {
		WorkInsurance WorkInsurance = workInsuranceService.getWorkInsuranceByEmpCd(empCd);
		model.addAttribute("WorkInsurance", WorkInsurance);
		return new ModelAndView("workInsuranceUpdate");
	}

	@PostMapping("/WorkInsuranceStateupdate")
	public ModelAndView update(WorkInsuranceForm WorkInsuranceForm, Model model) {

		Common common = new Common();
		Date ApplicationDate = common.getDate(WorkInsuranceForm.getApplicationDate());
		Date StartingDate = common.getDate(WorkInsuranceForm.getStartingDate());
		Date QuitDate = common.getDate(WorkInsuranceForm.getQuitDate());

		WorkInsurance workInsurance = new WorkInsurance(WorkInsuranceForm.getEmpCd(),
				WorkInsuranceForm.getWorkInsuranceState(), WorkInsuranceForm.getSalary(), ApplicationDate, StartingDate,
				QuitDate, WorkInsuranceForm.getWorkInsuranceNo(), WorkInsuranceForm.getReason(),
				WorkInsuranceForm.getWorkInsuranceComment());
		workInsuranceService.WorkInsurance_update(workInsurance);
		return new ModelAndView("redirect:/workInsuranceList");
	}

	// details by empCd
	@GetMapping("/toWorkInsurancetoDetails")
	public ModelAndView WorkInsuranceDetails(@RequestParam("id") String empCd, Model model) {
		WorkInsurance WorkInsuranceDetails = workInsuranceService.getWorkInsuranceByEmpCd(empCd);
		model.addAttribute("WorkInsuranceDetails", WorkInsuranceDetails);
		return new ModelAndView("workInsuranceDetails");
	}


//export機能

@SuppressWarnings("unchecked")
@RequestMapping(value="/workInsuranceExport")
public void export(HttpServletResponse response) throws IOException {
	List<WorkInsurance> workInsuranceList = null;
	if((boolean)session.getAttribute("searched")){
		workInsuranceList= (List<WorkInsurance>) session.getAttribute("searchedList");
		session.setAttribute("searched", false);//リセット
	}else{

		workInsuranceList = (List<WorkInsurance>) session.getAttribute("WorkInsuranceList");
	}
	workInsuranceService.excelExport(response,workInsuranceList, session);
	}

// ↓ errorExcelExport
@SuppressWarnings("unchecked")
@GetMapping(value="/errorWorkInsuranceExport")
public void errorListExport(HttpServletResponse response) throws IOException {
	List<WorkInsurance> workInsuranceList =(List<WorkInsurance>)session.getAttribute("errorList");

	workInsuranceService.excelExport(response,workInsuranceList,session);
	session.setAttribute("imported", false);
	session.removeAttribute("errorList");

}
//↓ excelImport
@RequestMapping(value="/workInsuranceExcelImport")
public ModelAndView exImport(@RequestParam(value = "filename")
MultipartFile file,ModelAndView mav ,HttpServletResponse response ) throws IOException {
	 //ファイルのサイズを判断する
	long size = file.getSize();
     if(size > 10000000)
     {
    	 response.setContentType("text/html; charset=utf-8");
			PrintWriter out;

				out = response.getWriter();
				out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
				out.print("<script>location='workInsuranceList';</script>");
				out.flush();

			mav.setViewName("redirect:/workInsuranceList");
			return mav;
       }


	session.setAttribute("imported", false);//セッションのインポート記録をリセットして、次の操作を影響しないように
	   List<WorkInsurance> errorList= new ArrayList<>();//エラーデータリスト
		List<WorkInsurance> importList = new ArrayList<>();//導入されたデータリスト
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
			out.print("<script>location='workInsuranceList';</script>");
			out.flush();

			mav.setViewName("redirect:/workInsuranceList");
			return mav;
		}
		if(file.isEmpty()) {	//ファイル選択なしにインポートボタンを押す場合
			mav.setViewName("redirect:/workInsuranceList");
			return mav;
		}else {

			try {
				Map<String,List<WorkInsurance>> map =workInsuranceService.batchImport(fileName, file);
				errorList = map.get("errorList");
				importList = map.get("importList");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			if(errorList!=null) {//エラーリストあるの場合
				wnt = errorList.size();//エラー数
				mav.addObject("errorList",errorList);
				session.setAttribute("errorList", errorList);
			}
			if(importList!=null) {
				cnt = importList.size();
				//正常数
					mav.addObject("importList", importList);
			}

			//インポートボタン　と　導出ボタンを分けるため設定の記録
			session.setAttribute("imported",true);
			mav.addObject("wnt",wnt);//エラー数
			mav.addObject("countEmployee", wnt);//インポートページのダウンロードボタンを隠すの条件
			mav.addObject("cnt",cnt);//正常数
			mav.setViewName("/workInsuranceImport");
			return mav;
		}
}
}
