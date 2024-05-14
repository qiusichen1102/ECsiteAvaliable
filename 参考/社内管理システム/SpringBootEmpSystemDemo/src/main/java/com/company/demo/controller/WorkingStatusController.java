package com.company.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.demo.bean.RetirementPayAndHealthInsurance;
import com.company.demo.bean.WorkingStatus;
import com.company.demo.common.Common;
import com.company.demo.form.WorkingStatusForm;
import com.company.demo.service.WorkingStatusService;
import com.github.pagehelper.PageHelper;

import ch.qos.logback.core.status.WarnStatus;

@Controller
@ComponentScan({ "com.company.demo.service" })
@ComponentScan({ "com.company.demo.common" })
@MapperScan("com.company.demo.mapper")
public class WorkingStatusController {
	@Resource
	WorkingStatusService workingStatusService;
	@Resource
	HttpSession session;
	@Resource
	Common common;
	// list

	@SuppressWarnings("unused")
	@GetMapping("/workingstatuslist")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button,
			ModelAndView modelAndView) throws Exception {
		//ページについてのボタンを処理する。
		session.setAttribute("imported", false);//エクセル動作の導入したの記録
		session.setAttribute("searched", false);//エクセル動作の検索したの記録
		List<WorkingStatus> WorkingStatusList1 = workingStatusService.searchWorkingStatusList();
		//ページ分割の設定値を取得する
		Map<String,Object> result = workingStatusService.pageTurn(pageNum,WorkingStatusList1,button);
		//ページを分割する操作
				PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
		//pagehelper応用必要のセレクト
				List<WorkingStatus> workingStatusListPage = workingStatusService.searchWorkingStatusList();


/*		List<WorkingStatus> WorkingStatusList1 = workingStatusService.searchWorkingStatusList();*/
		int countEmployee = WorkingStatusList1.size();
/*		modelAndView.addObject("countEmployee", countEmployee);*/
		if (WorkingStatusList1==null) {
			session.setAttribute("workingStatus", false);
		}else {
			session.setAttribute("workingStatus", true);
		}
		//ページを分割する
/*		PageHelper.startPage(pageNum, pageSize);
		List<WorkingStatus> WorkingStatusList = workingStatusService.searchWorkingStatusList();
		modelAndView.addObject("WorkingStatusList",WorkingStatusList);*/
		session.setAttribute("WorkingStatusList", WorkingStatusList1);//リスト記録
		modelAndView.addObject("WorkingStatusList",workingStatusListPage );
		modelAndView.addObject("countEmployee",countEmployee);
		modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
		modelAndView.addObject("pageNum", result.get("pageNum"));
		modelAndView.addObject("pageSize", result.get("pageSize"));
		modelAndView.addObject("firstPage", result.get("firstPage"));
		modelAndView.addObject("lastPage", result.get("lastPage"));

		return modelAndView;
	}
	@GetMapping("/WorkingStatus_search")
	public ModelAndView search_List(@Param(value = "keyword") String keyword,HttpServletRequest request,
			ModelAndView modelAndView) {
		session.setAttribute("searched", false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		//検索後データ
		List<WorkingStatus> WorkingStatusList = workingStatusService.search_empCd(keyword);
		modelAndView.addObject("WorkingStatusList",WorkingStatusList);
		session.setAttribute("WorkingStatusList", WorkingStatusList);
		session.setAttribute("searched", true);//検索記録
		//レコード数
		int countEmployee = WorkingStatusList.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("workingstatuslist");
		return modelAndView;

	}
	// delete by empCd
	@GetMapping("/WorkingStatus_delete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		workingStatusService.WorkingStatus_delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/workingstatuslist");
		return modelAndView;
	}
	// update by empCd
	@GetMapping("/toWorkingStatusUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd,Model model) {
		WorkingStatus WorkingStatus = workingStatusService.getWorkingStatusByEmpCd(empCd);
		model.addAttribute("WorkingStatus", WorkingStatus);
		return new ModelAndView("workingstatusUpdate");
	}

	@PostMapping("/WorkingStatusStateupdate")
	public ModelAndView update(WorkingStatusForm WorkingStatusForm,Model model) {

		Common common = new Common();
		Date StartingDay = common.getDate(WorkingStatusForm.getStartingDay());
		Date EndingDay = common.getDate(WorkingStatusForm.getEndingDay());

		WorkingStatus WorkingStatus = new WorkingStatus(WorkingStatusForm.getEmpCd(),WorkingStatusForm.getProjectName()
				,WorkingStatusForm.getProjectAddress(),WorkingStatusForm.getProjectStation(),WorkingStatusForm.getTransportationFee()
				,StartingDay,EndingDay,WorkingStatusForm.getMaxWorkTime(),WorkingStatusForm.getMinWorkTime()
				,WorkingStatusForm.getWorkTime(),WorkingStatusForm.getActuarial(),WorkingStatusForm.getRequestAmount()
				,WorkingStatusForm.getContractCompanyAddress(),WorkingStatusForm.getContractCompanyName(),WorkingStatusForm.getProjectDepartment()
				,WorkingStatusForm.getProjecetLeader(),WorkingStatusForm.getProjectTelNo()
				,WorkingStatusForm.getProjectEmail(),WorkingStatusForm.getContractType(),WorkingStatusForm.getPaymentTerm()
				,WorkingStatusForm.getCompanyLeader(),WorkingStatusForm.getWorkingStatusComment());
		workingStatusService.WorkingStatus_update(WorkingStatus);
		return new ModelAndView("redirect:/workingstatuslist");
	}
	// details by empCd
	@GetMapping("/toWorkingStatusDetails")
	public ModelAndView WorkingStatusDetails(@RequestParam("id") String empCd,Model model) {
		WorkingStatus WorkingStatusDetails = workingStatusService.getWorkingStatusByEmpCd(empCd);
		model.addAttribute("WorkingStatusDetails", WorkingStatusDetails);
		return new ModelAndView("workingstatusDetails");
	}

	// ↓ excelExport

@SuppressWarnings("unchecked")
@RequestMapping(value = "/workingStatusExcelExport")
public void export(HttpServletResponse response,ModelAndView mav, Object countEmployee) throws IOException {
	List<WorkingStatus> workingstatuslist = null;


	if((boolean)session.getAttribute("searched")){
		workingstatuslist = (List<WorkingStatus>) session.getAttribute("searchedList");
		session.setAttribute("searched", false);//リセット
	}else{
		workingstatuslist = (List<WorkingStatus>) session.getAttribute("WorkingStatusList");
	}
		workingStatusService.excelExport(response, workingstatuslist, session);
	}

//**********************************************************
	// ↓ errorExcelExport
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/errorWorkingStatusExcelExport")
	public void errorExcelExport(HttpServletResponse response) throws IOException {
		List<WorkingStatus> workingstatuslist = (List<WorkingStatus>) session.getAttribute("errorList");
		session.setAttribute("searched", false);
		workingStatusService.excelExport(response, workingstatuslist, session);
		session.setAttribute("imported", false);
		session.removeAttribute("errorList");//セッションのエラーリストを削除して、次の操作を影響しないように

	}

//↓ excelImport
@RequestMapping(value ="/workingStatusExcelImport")
public ModelAndView exImport(@RequestParam(value = "filename")
MultipartFile file,ModelAndView mav,RedirectAttributes redirectAttributes,HttpServletResponse response) throws IOException {
	 //ファイルのサイズを判断する
	long size = file.getSize();
     if(size > 10000000)
     {
    	 response.setContentType("text/html; charset=utf-8");
			PrintWriter out;

				out = response.getWriter();
				out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
				out.print("<script>location='workingstatuslist';</script>");
				out.flush();

			mav.setViewName("redirect:/workingstatuslist");
			return mav;
       }
	session.setAttribute("imported", false);//セッションのインポート記録をリセットして、次の操作を影響しないように
	   List<WorkingStatus> errorList= new ArrayList<>();//エラーデータリスト
		List<WorkingStatus> importList = new ArrayList<>();//導入されたデータリスト
		int wnt = 0;//エラー数
		int cnt = 0;//正常数
		String fileName = file.getOriginalFilename();
		String xlsxType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		String xlsType="application/vnd.ms-excel";
		//ファイルタイプを判断し、エラーを出します
		if(!(xlsType.equals(file.getContentType())) && !(xlsxType.equals(file.getContentType()))) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.print("<script>alert('Excelファイルを選択してください');</script>");
			out.print("<script>location='workingstatuslist';</script>");
			out.flush();
			mav.setViewName("redirect:/workingstatuslist");
			return mav;
		}
		if(file.isEmpty()) {	//ファイル選択なしにインポートボタンを押す場合
			mav.setViewName("redirect:/workingstatuslist");
			return mav;
		}else {

			try {
				Map<String,List<WorkingStatus>> map =workingStatusService.batchImport(fileName, file);
				errorList = map.get("errorList");
				importList = map.get("importList");
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				/*mav.addObject("message","データベースインポートエラー");
				mav.setViewName("/workingstatusImport");
				return mav;*/
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
			mav.setViewName("/workingstatusImport");
			/* redirectAttributes.addFlashAttribute("message",
	        		 file.getOriginalFilename() +" アップロード完了しました。");
			 mav.addObject("message",file.getOriginalFilename() +" アップロード完了しました。");*/
			return mav;
		}
}
}
