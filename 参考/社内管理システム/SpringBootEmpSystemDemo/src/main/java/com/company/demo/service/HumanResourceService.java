package com.company.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.HumanResource;
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;
import com.company.demo.mapper.HumanResourceMapper;

@Service
public class HumanResourceService {
	@Resource
	HumanResourceMapper humanResourceMapper;
	@Resource
	EmpInfoMapper empinfoMapper;

	// list
	public List<HumanResource> searchHumanResourceList() {
		List<HumanResource> emplist = humanResourceMapper.searchHumanResourceList();
		return emplist;
	}

	// list2 get

	public List<HumanResource> getHumanResource() {
		List<HumanResource> HumanResourcelist = humanResourceMapper.getHumanResource();
		return HumanResourcelist;

	}

	// search by empCd
	public List<HumanResource> search_empCd(@Param("keyword") String keyword) {
		List<HumanResource> listemp = humanResourceMapper.search_empCd(keyword);
		return listemp;
	}

	// delete by empCd
	public void HumanResource_delete(String empCd) {
		humanResourceMapper.HumanResource_delete(empCd);
	}
	// update by empCd

	public void HumanResource_update(HumanResource HumanResource) {
		humanResourceMapper.HumanResource_update(HumanResource);
	}

	public HumanResource getHumanResourceByEmpCd(String empCd) {
		HumanResource empInfo = humanResourceMapper.getHumanResourceByEmpCd(empCd);
		return empInfo;
	}

	// excelExport
	@Resource
	Common common;

	public void excelExport(HttpServletResponse response, List<HumanResource> HumanResourceList,
			HttpSession session) throws IOException {
		if (HumanResourceList != null) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("人事基本情報リスト");
			HSSFRow row = null;
			/*
			 * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where
			 * table_name='user' and table_schema='test' 第一个table_name 表名字 第二个table_name
			 * 数据库名称
			 */
			row = sheet.createRow(0);
			row.setHeight((short) (22.50 * 20));// 设置行高
			row.createCell(0).setCellValue("社員番号");
			row.createCell(1).setCellValue("名前（漢字）");
			row.createCell(2).setCellValue("名前（カナ）");
			row.createCell(3).setCellValue("性別");
			row.createCell(4).setCellValue("生年月日");
			row.createCell(5).setCellValue("社員区分");
			row.createCell(6).setCellValue("郵便番号");
			row.createCell(7).setCellValue("住所");
			row.createCell(8).setCellValue("連絡先");

			row.createCell(9).setCellValue("個人メールアドレス");
			row.createCell(10).setCellValue("会社メールアドレス");
			row.createCell(11).setCellValue("入社年月日");
			row.createCell(12).setCellValue("退職年月日");
			row.createCell(13).setCellValue("最寄り駅");
			row.createCell(14).setCellValue("基本給");
			row.createCell(15).setCellValue("月薪年回");
			row.createCell(16).setCellValue("保険標準");
			row.createCell(17).setCellValue("健康保険");

			row.createCell(18).setCellValue("厚生年金");
			row.createCell(19).setCellValue("介護保険");
			row.createCell(20).setCellValue("備考");

			// リスト内容
			for (int i = 0; i < HumanResourceList.size(); i++) {
				row = sheet.createRow(i + 1);
				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper creationHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				// 日付フォーマットを設定する、セール別で応用する
				HumanResource HumanResource = HumanResourceList.get(i);
				row.createCell(0).setCellValue(HumanResource.getEmpCd());
				row.createCell(1).setCellValue(HumanResource.getNameKanji());
				row.createCell(2).setCellValue(HumanResource.getNameKana());
				row.createCell(3).setCellValue(HumanResource.getSex());
				// nullのdateを除外する
				if (HumanResource.getBirthday() != null) {
					Cell cell = row.createCell(4);
					cell.setCellValue(HumanResource.getBirthday());
					cell.setCellStyle(cellStyle);
				}
				row.createCell(5).setCellValue(HumanResource.getEmployeeType());
				row.createCell(6).setCellValue(HumanResource.getPostcode());
				row.createCell(7).setCellValue(HumanResource.getAddress());
				row.createCell(8).setCellValue(HumanResource.getTelNo());

				row.createCell(9).setCellValue(HumanResource.getEmail());
				row.createCell(10).setCellValue(HumanResource.getCompanyEmail());

				if (HumanResource.getEnteringDate() != null) {
					Cell cell = row.createCell(11);
					cell.setCellValue(HumanResource.getEnteringDate());
					cell.setCellStyle(cellStyle);
				}
				if (HumanResource.getDimissionDate() != null) {
					Cell cell = row.createCell(12);
					cell.setCellValue(HumanResource.getDimissionDate());
					cell.setCellStyle(cellStyle);
				}

				row.createCell(13).setCellValue(HumanResource.getHomeStation());
				row.createCell(14).setCellValue(HumanResource.getBasicSalary());
				row.createCell(15).setCellValue(HumanResource.getSalaryTimes());
				row.createCell(16).setCellValue(HumanResource.getInsuranceLine());
				row.createCell(17).setCellValue(HumanResource.getHealthInsurance());
				row.createCell(18).setCellValue(HumanResource.getRetirementPay());

				row.createCell(19).setCellValue(HumanResource.getDiseaseInturance());
				row.createCell(20).setCellValue(HumanResource.getHumanResourceComment());

			}
			sheet.setDefaultRowHeight((short) (16.5 * 20));
			// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
			for (int i = 0; i < 10; i++) {
				sheet.setColumnWidth(i, 255 * 15);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			OutputStream os = response.getOutputStream();
			String exportValue = "attachment;filename=" + common.getDate() + "_HumanResource" + ".xls";
			String errorValue = "attachment;filename=" + common.getDate() + "_Error_HumanResource"
					+ ".xls";
			if ((boolean) session.getAttribute("imported")) {
				response.setHeader("Content-disposition", errorValue);//Excel名
			} else {
				response.setHeader("Content-disposition", exportValue);//Excel名
			}
			//response.setHeader("Content-disposition", value);// Excel名

			wb.write(os);
			wb.close();
			os.flush();
			os.close();
			session.removeAttribute("errorList");// セッションのエラーリストを削除して、次の操作を影響しないように

		}

	}

	// excelImport


	@SuppressWarnings("deprecation")
	public Map<String, List<HumanResource>> batchImport(String fileName, MultipartFile file) throws Exception {

		List<HumanResource> errorList = new ArrayList<>();// エラーデータリスト
		List<HumanResource> importList = new ArrayList<>();// 導入されたデータリスト
		Map<String, List<HumanResource>> map = new HashMap();// リターン値

		// ファイル名をチェック
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new ExcelException("違うファイルフォーマット");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		// Excel 2003否か ワークブックを作成
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new HSSFWorkbook(is);
		}

		boolean notNull = false;// シートチェック
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = true;
		}

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {// 二行目から内容スギャン
			Row row = sheet.getRow(r);
			HumanResource listUser = new HumanResource();
			if (row == null) {
				continue;
			} else if (common.isRowEmpty(row)) {
				continue;
			}
			/*
			 * row.getCell(0).setCellType(CellType.STRING); String empCd =
			 * row.getCell(0).getStringCellValue();// 社員番号取得
			 */
			/*
			 * if (empCd == null || empCd.isEmpty()) {// 社員番号空白判断 // ループでセール 値空白かどうか判断する
			 * errorList.add(listUser); // listUserに入れたら、errorlist か retailListか入れる } else {
			 * // 一行の九項目日付以外のセールをストリング型に設定する
			 *
			 * listUser.setEmpCd(empCd); }
			 */
			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(CellType.STRING);
				listUser.setEmpCd(row.getCell(0).getStringCellValue());

			}
			if (row.getCell(1) != null) {
				row.getCell(1).setCellType(CellType.STRING);
				listUser.setNameKanji(row.getCell(1).getStringCellValue());

			}
			if (row.getCell(2) != null) {
				row.getCell(2).setCellType(CellType.STRING);
				listUser.setNameKana(row.getCell(2).getStringCellValue());
			}
			if (row.getCell(3) != null) {
				row.getCell(3).setCellType(CellType.STRING);
				listUser.setSex(row.getCell(3).getStringCellValue());
			}

			// 空白でない場合日付を入れる
			if (row.getCell(4) != null) {
				listUser.setBirthday(row.getCell(4).getDateCellValue());
			}

			if (row.getCell(5) != null) {
				row.getCell(5).setCellType(CellType.STRING);
				listUser.setEmployeeType(row.getCell(5).getStringCellValue());
			}

			if (row.getCell(6) != null) {
				row.getCell(6).setCellType(CellType.STRING);
				listUser.setPostcode(row.getCell(6).getStringCellValue());
			}
			if (row.getCell(7) != null) {
				row.getCell(7).setCellType(CellType.STRING);
				listUser.setAddress(row.getCell(7).getStringCellValue());
			}
			if (row.getCell(8) != null) {
				row.getCell(8).setCellType(CellType.STRING);
				listUser.setTelNo(row.getCell(8).getStringCellValue());
			}
			if (row.getCell(9) != null) {
				row.getCell(9).setCellType(CellType.STRING);
				listUser.setEmail(row.getCell(9).getStringCellValue());
			}
			if (row.getCell(10) != null) {
				row.getCell(10).setCellType(CellType.STRING);
				listUser.setCompanyEmail(row.getCell(10).getStringCellValue());
			}

			// 空白でない場合日付を入れる
			if (row.getCell(11) != null) {
				listUser.setEnteringDate(row.getCell(11).getDateCellValue());
			}
			// 空白でない場合日付を入れる
			if (row.getCell(12) != null) {
				listUser.setDimissionDate(row.getCell(12).getDateCellValue());
			}

			if (row.getCell(13) != null) {
				row.getCell(13).setCellType(CellType.STRING);
				listUser.setHomeStation(row.getCell(13).getStringCellValue());
			}
			if (row.getCell(14) != null) {
				row.getCell(14).setCellType(CellType.STRING);
				listUser.setBasicSalary(row.getCell(14).getStringCellValue());
			}
			if (row.getCell(15) != null) {
				row.getCell(15).setCellType(CellType.STRING);
				listUser.setSalaryTimes(row.getCell(15).getStringCellValue());
			}
			if (row.getCell(16) != null) {
				row.getCell(16).setCellType(CellType.STRING);
				listUser.setInsuranceLine(row.getCell(16).getStringCellValue());
			}
			if (row.getCell(17) != null) {
				row.getCell(17).setCellType(CellType.STRING);
				listUser.setHealthInsurance(row.getCell(17).getStringCellValue());
			}
			if (row.getCell(18) != null) {
				row.getCell(18).setCellType(CellType.STRING);
				listUser.setRetirementPay(row.getCell(18).getStringCellValue());
			}
			if (row.getCell(19) != null) {
				row.getCell(19).setCellType(CellType.STRING);
				listUser.setDiseaseInturance(row.getCell(19).getStringCellValue());
			}
			if (row.getCell(20) != null) {
				row.getCell(20).setCellType(CellType.STRING);
				listUser.setHumanResourceComment(row.getCell(20).getStringCellValue());
			}
			String empCd = null;// 社員番号
			if (row.getCell(0) != null) {
				// 上記セールタイプ すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}
			// 社員番号検索した結果
			EmpInfo searchList = empinfoMapper.getEmpInfoByEmpCd(empCd);
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {// 社員番号 がempinfo表と合致した場合続行
					List<HumanResource>  HumanResourceList1= humanResourceMapper.search_empCd(empCd);
					if ( HumanResourceList1.size() != 0) {// 年金画面のデータベースの社員番号があるかの確認
						humanResourceMapper.HumanResource_update(listUser);
					} else {
						humanResourceMapper.add(listUser);
					}
					importList.add(listUser);
					map.put("importList", importList);
				}

			} else {
				// error データリターンする //存在しない社員番号を判定したらエラーリストに入れる
				String errorMessage = "社員番号エラー";
				listUser.setHumanResourceComment(errorMessage);
				//listUser.setHumanResource(errorMessage);
				errorList.add(listUser);
				map.put("errorList", errorList);
			}
		}
		wb.close();
		is.close();
		return map;
	}
/*page*/
	public static final Integer pageSize = 1 ;
	public Map<String,Object> pageTurn(Integer pageNum,List<HumanResource> humanResourceList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)humanResourceList.size() / pageSize) );
		if (button != null ) {
			if (button.equals("次へ")) {
					pageNum++;
			}else if (button.equals("前へ")) {
				pageNum--;
			}
		}
		//最初のページか最後のページかの判断して、値を設定する
		if(pageNum==null || pageNum <1) {
			pageNum = 1;
			result.put("firstPage",1);
		}else if(pageNum>=pageTotalNum) {
			pageNum=pageTotalNum;
			result.put("lastPage",1);
		}
		result.put("pageNum",pageNum);
		result.put("pageSize",pageSize);
		result.put("pageTotalNum",pageTotalNum);
		return result;
	}
}
