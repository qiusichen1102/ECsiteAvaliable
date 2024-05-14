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

import com.company.demo.bean.RetirementPayAndHealthInsurance;
import com.company.demo.common.Common;
import com.company.demo.form.RetirementPayAndHealthInsuranceForm;
import com.company.demo.service.RetirementPayAndHealthInsuranceService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.company.demo.service" })
@ComponentScan({ "com.company.demo.common" })
@MapperScan("com.company.demo.mapper")
public class RetirementPayAndHealthInsuranceController {
	@Resource
	RetirementPayAndHealthInsuranceService retirementPayAndHealthInsuranceService;
	@Resource
	HttpSession session;
	@Resource
	Common common;



	// list
	@SuppressWarnings("unused")
	@GetMapping("/retailList")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button,
			ModelAndView modelAndView) throws Exception {
		//	ページについてのボタンを処理する。
		session.setAttribute("imported", false);//エクセル動作の導入したの記録
		session.setAttribute("searched", false);//エクセル動作の検索したの記録
		List<RetirementPayAndHealthInsurance> retailList = retirementPayAndHealthInsuranceService
				.getRetirementPayAndHealthInsurance();

		//ページ分割の設定値を取得する
		Map<String,Object> result = retirementPayAndHealthInsuranceService.pageTurn(pageNum,retailList,button);
		//ページを分割する操作
		PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
		//pagehelper応用必要のセレクト
		List<RetirementPayAndHealthInsurance> retailListPage = retirementPayAndHealthInsuranceService
				.getRetirementPayAndHealthInsurance();
		//社員数を所得する
				int countEmployee = retailList.size();		//リストセッションに記録する
			if(retailList==null)
			{session.setAttribute("retail", false);
			}else {
				session.setAttribute("retail", true);
			}
		session.setAttribute("retailList", retailList);
		modelAndView.addObject("retailList", retailListPage);
		modelAndView.addObject("countEmployee",countEmployee);
		modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
		modelAndView.addObject("pageNum", result.get("pageNum"));
		modelAndView.addObject("pageSize", result.get("pageSize"));
		modelAndView.addObject("firstPage", result.get("firstPage"));
		modelAndView.addObject("lastPage", result.get("lastPage"));
		return modelAndView;
	}

	@GetMapping("/retailsearch")
	public ModelAndView search_List(@Param(value = "keyword") String keyword, HttpServletRequest request,
			ModelAndView modelAndView) {

		session.setAttribute("searched", false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		//検索後データ
		List<RetirementPayAndHealthInsurance> retailList = retirementPayAndHealthInsuranceService.search_empCd(keyword);
		modelAndView.addObject("retailList", retailList);
		session.setAttribute("searchedList", retailList);
		session.setAttribute("searched", true);//検索記録
		//レコード数
		int countEmployee = retailList.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("retailList");
		return modelAndView;

	}

	// delete by empCd
	@GetMapping("/retaildelete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		retirementPayAndHealthInsuranceService.delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/retailList");

		return modelAndView;
	}

	//**********************************************************
	// ↓ update by empCd
	@GetMapping("/toRetailUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd, Model model) {
		RetirementPayAndHealthInsurance retailInfo = retirementPayAndHealthInsuranceService
				.getRetirementPayAndHealthInsuranceByEmpCd(empCd);
		model.addAttribute("retailInfo", retailInfo);
		return new ModelAndView("retailUpdate");
	}

	@PostMapping("/retailUpdate")
	public ModelAndView update(RetirementPayAndHealthInsuranceForm retirementPayAndHealthInsuranceForm, Model model) {

		Common common = new Common();
		Date applicationDate = common.getDate(retirementPayAndHealthInsuranceForm.getApplicationDate());
		Date startingDate = common.getDate(retirementPayAndHealthInsuranceForm.getStartingDate());
		Date quitDate = common.getDate(retirementPayAndHealthInsuranceForm.getQuitDate());
		RetirementPayAndHealthInsurance retirementPayAndHealthInsurance = new RetirementPayAndHealthInsurance();
		retirementPayAndHealthInsurance.setEmpCd(retirementPayAndHealthInsuranceForm.getEmpCd());
		retirementPayAndHealthInsurance.setState(retirementPayAndHealthInsuranceForm.getState());
		retirementPayAndHealthInsurance.setInsuranceLine(retirementPayAndHealthInsuranceForm.getInsuranceLine());
		retirementPayAndHealthInsurance.setApplicationDate(applicationDate);
		retirementPayAndHealthInsurance.setStartingDate(startingDate);
		retirementPayAndHealthInsurance.setQuitDate(quitDate);
		retirementPayAndHealthInsurance.setInturanceNote(retirementPayAndHealthInsuranceForm.getInturanceNote());
		retirementPayAndHealthInsurance.setHealthInsurance(retirementPayAndHealthInsuranceForm.getHealthInsurance());
		retirementPayAndHealthInsurance.setRetirementPayAndHealthInsuranceComment(
				retirementPayAndHealthInsuranceForm.getRetirementPayAndHealthInsuranceComment());
		retirementPayAndHealthInsuranceService.update(retirementPayAndHealthInsurance);
		return new ModelAndView("redirect:/retailList");
	}

	//**********************************************************
	// ↓ details by empCd
	@GetMapping("/toRetailDetails")
	public ModelAndView toDetails(@RequestParam("id") String empCd, Model model) {
		System.out.print(empCd);
		RetirementPayAndHealthInsurance retailInfo = retirementPayAndHealthInsuranceService
				.getRetirementPayAndHealthInsuranceByEmpCd(empCd);
		model.addAttribute("retailInfo", retailInfo);
		return new ModelAndView("retailDetails");
	}

	//**********************************************************
	// ↓ excelExport
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/excelExport")
	public void excelExport(HttpServletResponse response) throws IOException {
		List<RetirementPayAndHealthInsurance> retailList = null;
		if ((boolean) session.getAttribute("searched")) {
			retailList = (List<RetirementPayAndHealthInsurance>) session.getAttribute("searchedList");
			session.setAttribute("searched", false);//リセット
		} else {
			retailList = (List<RetirementPayAndHealthInsurance>) session.getAttribute("retailList");
		}
		retirementPayAndHealthInsuranceService.excelExport(response, retailList, session);
	}

	//**********************************************************
	// ↓ errorExcelExport
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/errorExcelExport")
	public void errorExcelExport(HttpServletResponse response) throws IOException {
		List<RetirementPayAndHealthInsurance> retailList = (List<RetirementPayAndHealthInsurance>) session
				.getAttribute("errorList");
		retirementPayAndHealthInsuranceService.excelExport(response, retailList, session);
		session.setAttribute("imported", false);
		session.removeAttribute("errorList");//セッションのエラーリストを削除して、次の操作を影響しないように

	}

	//**********************************************************
	// ↓ excelImport

	@RequestMapping(value = "/excelImport")
	public ModelAndView excelImport(@RequestParam(value = "filename") MultipartFile file,
			ModelAndView mav ,HttpServletResponse response ) throws IOException {
		 //ファイルのサイズを判断する
		long size = file.getSize();
	     if(size > 10000000)
	     {
	    	 response.setContentType("text/html; charset=utf-8");
				PrintWriter out;

					out = response.getWriter();
					out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
					out.print("<script>location='retailList';</script>");
					out.flush();

				mav.setViewName("redirect:/retailList");
				return mav;
	       }
		session.setAttribute("imported", false);//セッションのインポート記録をリセットして、次の操作を影響しないように
		List<RetirementPayAndHealthInsurance> errorList = new ArrayList<>();//エラーデータリスト
		List<RetirementPayAndHealthInsurance> importList = new ArrayList<>();//導入されたデータリスト
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
			out.print("<script>location='retailList';</script>");
			out.flush();
			mav.setViewName("redirect:/retailList");
			return mav;
		}
		if (file.isEmpty()) { //ファイル選択なしにインポートボタンを押す場合
			mav.setViewName("redirect:/retailList");
			return mav;
		} else {
			try {
				Map<String, List<RetirementPayAndHealthInsurance>> map = retirementPayAndHealthInsuranceService
						.batchImport(fileName, file);
				errorList = map.get("errorList");
				importList = map.get("importList");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			if (errorList != null) {//エラーリストあるの場合
				wnt = errorList.size();//エラー数
				mav.addObject("errorList", errorList);
				session.setAttribute("errorList", errorList);
			}
			if (importList != null) {
				cnt = importList.size();//正常数
				mav.addObject("importList", importList);
			}
			//インポートボタン　と　導出ボタンを分けるため設定の記録
			session.setAttribute("imported", true);
			//インポートページのダウンロードボタンを隠すの条件
			mav.addObject("wnt", wnt);//エラー数
			mav.addObject("cnt", cnt);//正常数　導入されたのデータ表示の条件
			mav.setViewName("/retailExcelImport");
			return mav;
		}
	}
}
