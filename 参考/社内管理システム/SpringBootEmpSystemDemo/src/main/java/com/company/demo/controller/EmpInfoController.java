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

import com.company.demo.bean.EmpInfo;
import com.company.demo.common.Common;
import com.company.demo.form.EmpInfoForm;
import com.company.demo.service.EmpInfoService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.company.demo.service" })
@ComponentScan({ "com.company.demo.common" })
@MapperScan("com.company.demo.mapper")
public class EmpInfoController {
	@Resource
	EmpInfoService empInfoService;
	@Resource
	HttpSession session;

	@Resource
	Common common;

	// list
	@SuppressWarnings("unused")
	@GetMapping("/list")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "button") String button,
			ModelAndView modelAndView) throws Exception {
		//ページについてのボタンを処理する。
		session.setAttribute("imported", false);// エクセル動作の導入したの記録
		session.setAttribute("searched", false);// エクセル動作の検索したの記録
		List<EmpInfo> empList1 = empInfoService.searchEmpList();
		//ページ分割の設定値を取得する
				Map<String,Object> result = empInfoService.pageTurn(pageNum,empList1,button);
				//ページを分割する操作
						PageHelper.startPage((int)result.get("pageNum"), (int)result.get("pageSize"));
				//pagehelper応用必要のセレクト
						List<EmpInfo> empListPage =  empInfoService.searchEmpList();

		int countEmployee = empList1.size();

			if (empList1==null) {
				session.setAttribute("emp", false);
			}else{

				session.setAttribute("emp", true);
			}
		//ページを分割する
			session.setAttribute("empList",  empList1);//リスト記録
			modelAndView.addObject("empList", empListPage );
			modelAndView.addObject("countEmployee",countEmployee);
			modelAndView.addObject("pageTotalNum", result.get("pageTotalNum"));
			modelAndView.addObject("pageNum", result.get("pageNum"));
			modelAndView.addObject("pageSize", result.get("pageSize"));
			modelAndView.addObject("firstPage", result.get("firstPage"));
			modelAndView.addObject("lastPage", result.get("lastPage"));

		return modelAndView;
	}

	@GetMapping("/search")
	public ModelAndView search_List(@Param(value = "keyword") String keyword, HttpServletRequest request,
			ModelAndView modelAndView) {
		session.setAttribute("searched", false);//セッションの検索記録をリセットして、次の操作を影響しないように
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		//検索後データ
		List<EmpInfo> emplist = empInfoService.search_empCd(keyword);
		modelAndView.addObject("empList", emplist);
		session.setAttribute("empList", emplist);
		session.setAttribute("searched", true);//検索記録
		//レコード数
		int countEmployee = emplist.size();
		modelAndView.addObject("countEmployee", countEmployee);
		modelAndView.setViewName("list");
		//modelAndView.setViewName("list");
		return modelAndView;

	}

	// delete by empCd
	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam(value = "id", required = false) String empCd, ModelAndView modelAndView) {
		empInfoService.delete(empCd);
		System.out.println(empCd + ":" + "削除しました");
		modelAndView.setViewName("forward:/list");

		return modelAndView;
	}

	//**********************************************************
	// ↓ add
	@GetMapping("/toAdd")
	public ModelAndView toAdd(Model model) {

		return new ModelAndView("add");
	}

	@PostMapping("/add")
	public ModelAndView add(EmpInfoForm empInfoForm, Model model, Map<String, Object> map) {

		Common common = new Common();
		Date birthday = common.getDate(empInfoForm.getBirthday());
		Date enteringDate = common.getDate(empInfoForm.getEnteringDate());
		Date dimissionDate = common.getDate(empInfoForm.getDimissionDate());
		EmpInfo empInfoCheck = empInfoService.getEmpInfoByEmpCd(empInfoForm.getEmpCd());
		if (empInfoCheck == null) {
			EmpInfo empInfo = new EmpInfo(empInfoForm.getEmpCd(), empInfoForm.getEmail(), empInfoForm.getCompanyEmail(),
					empInfoForm.getNameKanji(), empInfoForm.getNameKana(),
					empInfoForm.getSex(), birthday, empInfoForm.getCountry(), empInfoForm.getPostcode(),
					empInfoForm.getAddress(),
					empInfoForm.getTelNo(), empInfoForm.getHomeStation(), enteringDate, dimissionDate,
					empInfoForm.getCompany(),
					empInfoForm.getEmployeeType(), empInfoForm.getComment());
			empInfoService.add(empInfo);
			empInfoService.addEngineerList(empInfoForm.getEmpCd());
			empInfoService.addHumanResource(empInfoForm.getEmpCd());
			empInfoService.addRetirementPayAndHealthInsurance(empInfoForm.getEmpCd());
			empInfoService.addWorkingStatus(empInfoForm.getEmpCd());
			empInfoService.addWorkInsurance(empInfoForm.getEmpCd());

			return new ModelAndView("redirect:/list");
		} else {
			map.put("msg", "社員番号はすでに存在します！");

			return new ModelAndView("add");
		}
	}

	//**********************************************************
	// ↓ update by empCd
	@GetMapping("/toUpdate")
	public ModelAndView toupdate(@RequestParam("id") String empCd, Model model) {
		System.out.print(empCd);
		EmpInfo empInfo = empInfoService.getEmpInfoByEmpCd(empCd);
		model.addAttribute("empInfo", empInfo);
		return new ModelAndView("update");
	}

	@PostMapping("/update")
	public ModelAndView update(EmpInfoForm empInfoForm, Model model) {

		Common common = new Common();
		Date birthday = common.getDate(empInfoForm.getBirthday());
		Date enteringDate = common.getDate(empInfoForm.getEnteringDate());
		Date dimissionDate = common.getDate(empInfoForm.getDimissionDate());

		EmpInfo empInfo = new EmpInfo(empInfoForm.getEmpCd(), empInfoForm.getEmail(), empInfoForm.getCompanyEmail(),
				empInfoForm.getNameKanji(), empInfoForm.getNameKana(),
				empInfoForm.getSex(), birthday, empInfoForm.getCountry(), empInfoForm.getPostcode(),
				empInfoForm.getAddress(),
				empInfoForm.getTelNo(), empInfoForm.getHomeStation(), enteringDate, dimissionDate,
				empInfoForm.getCompany(),
				empInfoForm.getEmployeeType(), empInfoForm.getComment());
		empInfoService.update(empInfo);
		return new ModelAndView("redirect:/list");
	}

	//**********************************************************
	// ↓ details by empCd
	@GetMapping("/toDetails")
	public ModelAndView toDetails(@RequestParam("id") String empCd, Model model) {
		System.out.print(empCd);
		EmpInfo empInfoDetails = empInfoService.getEmpInfoByEmpCd(empCd);
		model.addAttribute("empInfoDetails", empInfoDetails);
		return new ModelAndView("details");
	}


	// **********************************************************
		// ↓ excelExport


		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/_Export")
		public void _Export(HttpServletResponse response) throws IOException {
			List<EmpInfo> empList = null;
			if ((boolean) session.getAttribute("searched")) {
				empList = (List<EmpInfo>) session.getAttribute("empList");
				session.setAttribute("searched", false);//リセット
			} else {
				empList = (List<EmpInfo>) session.getAttribute("empList");
			}
				empInfoService.excelExport(response, empList, session);
		}

		//**********************************************************
		// ↓ errorExcelExport
		@SuppressWarnings("unchecked")
		@GetMapping(value = "/errorExport")
		public void errorExcelExport(HttpServletResponse response) throws IOException {
			List<EmpInfo> empList = (List<EmpInfo>) session.getAttribute("errorList");
			empInfoService.excelExport(response, empList, session);
			session.setAttribute("imported", false);
			session.removeAttribute("errorList");//セッションのエラーリストを削除して、次の操作を影響しないように
		}

		// **********************************************************
		// ↓ excelImport

		@RequestMapping(value = "/_Import")
		public ModelAndView _Import(@RequestParam(value = "filename") MultipartFile file, ModelAndView mav
				 ,HttpServletResponse response) throws IOException {
			 //ファイルのサイズを判断する
			long size = file.getSize();
		     if(size > 10000000)
		     {
		    	 response.setContentType("text/html; charset=utf-8");
					PrintWriter out;

						out = response.getWriter();
						out.print("<script>alert('ファイルサイズが大きすぎです');</script>");
						out.print("<script>location='list';</script>");
						out.flush();

					mav.setViewName("redirect:/list");
					return mav;
		       }

			session.setAttribute("imported", false); // セッションのインポート記録をリセットして、次の操作を影響しないように
			List<EmpInfo> errorList = new ArrayList<>();// エラーデータリスト
			List<EmpInfo> importList = new ArrayList<>();// 導入されたデータリスト
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
				out.print("<script>location='list';</script>");
				out.flush();
				mav.setViewName("redirect:/list");
				return mav;
			}
			if (file.isEmpty()) { // ファイル選択なしにインポートボタンを押す場合
				mav.setViewName("redirect:/list");
				return mav;
			} else {

				try {
					Map<String, List<EmpInfo>> map = empInfoService.batchImport(fileName, file);
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
				mav.addObject("countEmployee", wnt);//インポートページのダウンロードボタンを隠すの条件
				mav.addObject("cnt", cnt);// 正常数

				mav.setViewName("/import");

				return mav;
			}
		}

}
