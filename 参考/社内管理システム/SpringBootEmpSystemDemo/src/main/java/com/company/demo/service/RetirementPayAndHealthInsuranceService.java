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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.RetirementPayAndHealthInsurance;
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;
import com.company.demo.mapper.RetirementPayAndHealthInsuranceMapper;

@Service
@ComponentScan("com.company.demp.common")
public class RetirementPayAndHealthInsuranceService{
	@Resource
	RetirementPayAndHealthInsuranceMapper retirementPayAndHealthInsuranceMapper;

	@Resource
	EmpInfoMapper empinfoMapper;

	// list
	public List<RetirementPayAndHealthInsurance> getRetirementPayAndHealthInsurance() {
		List<RetirementPayAndHealthInsurance> retaillist = retirementPayAndHealthInsuranceMapper
				.getRetirementPayAndHealthInsurance();


		return retaillist;
	}

	// search by empCd
	public List<RetirementPayAndHealthInsurance> search_empCd(@Param("keyword") String keyword) {
		List<RetirementPayAndHealthInsurance> retaillist = retirementPayAndHealthInsuranceMapper.search_empCd(keyword);
		return retaillist;
	}

	// delete by empCd
	public void delete(String empCd) {
		retirementPayAndHealthInsuranceMapper.delete(empCd);
	}

	// update by empCd

	public void update(RetirementPayAndHealthInsurance retirementPayAndHealthInsurance) {
		retirementPayAndHealthInsuranceMapper.update(retirementPayAndHealthInsurance);
	}

	// details by empCd
	public RetirementPayAndHealthInsurance getRetirementPayAndHealthInsuranceByEmpCd(String empCd) {
		RetirementPayAndHealthInsurance retaillist = retirementPayAndHealthInsuranceMapper
				.getRetirementPayAndHealthInsuranceByEmpCd(empCd);
		return retaillist;
	}

	//excelExport
	@Resource
	Common common;

	public void excelExport(HttpServletResponse response,List<RetirementPayAndHealthInsurance> retailList,HttpSession session) throws IOException {
		if (retailList != null) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("年金国保リスト");
			HSSFRow row = null;
			/*
			* 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
			* 第一个table_name 表名字
			* 第二个table_name 数据库名称
			* */
			row = sheet.createRow(0);
			row.setHeight((short) (22.50 * 20));//设置行高
			row.createCell(0).setCellValue("社員番号");
			row.createCell(1).setCellValue("加入状況");
			row.createCell(2).setCellValue("加入基準");
			row.createCell(3).setCellValue("申請日");
			row.createCell(4).setCellValue("取得年月日");
			row.createCell(5).setCellValue("喪失年月日");
			row.createCell(6).setCellValue("年金手帳");
			row.createCell(7).setCellValue("健康保険");
			row.createCell(8).setCellValue("備考/理由");

			//リスト内容
			for (int i = 0; i < retailList.size(); i++) {
				row = sheet.createRow(i + 1);
				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper creationHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(
						creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				//日付フォーマットを設定する、セール別で応用する
				RetirementPayAndHealthInsurance retail = retailList.get(i);
				row.createCell(0).setCellValue(retail.getEmpCd());
				row.createCell(1).setCellValue(retail.getState());
				row.createCell(2).setCellValue(retail.getInsuranceLine());
				//nullのdateを除外する
				if (retail.getApplicationDate() != null) {
					Cell cell = row.createCell(3);
					cell.setCellValue(retail.getApplicationDate());
					cell.setCellStyle(cellStyle);
				}
				if (retail.getStartingDate() != null) {
					Cell cell = row.createCell(4);
					cell.setCellValue(retail.getStartingDate());
					cell.setCellStyle(cellStyle);
				}
				if (retail.getQuitDate() != null) {
					Cell cell = row.createCell(5);
					cell.setCellValue(retail.getQuitDate());
					cell.setCellStyle(cellStyle);
				}
				row.createCell(6).setCellValue(retail.getInturanceNote());
				row.createCell(7).setCellValue(retail.getHealthInsurance());
				row.createCell(8).setCellValue(retail.getRetirementPayAndHealthInsuranceComment());

			}
			sheet.setDefaultRowHeight((short) (16.5 * 20));
			// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
			for (int i = 0; i < 10; i++) {
				sheet.setColumnWidth(i, 255 * 15);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			OutputStream os = response.getOutputStream();
			String exportValue = "attachment;filename=" + common.getDate() + "_RetirementPayAndHealthInsurance"
					+ ".xls";
			String errorValue = "attachment;filename=" + common.getDate() + "_Error_RetirementPayAndHealthInsurance"
					+ ".xls";
			if ((boolean) session.getAttribute("imported")) {
				response.setHeader("Content-disposition", errorValue);//Excel名
			} else {
				response.setHeader("Content-disposition", exportValue);//Excel名
			}
			wb.write(os);
			wb.close();
			os.flush();
			os.close();
		}
	}


	//excelImport

	public Map<String, List<RetirementPayAndHealthInsurance>> batchImport(String fileName, MultipartFile file)
			throws Exception {

		List<RetirementPayAndHealthInsurance> errorList = new ArrayList<>();//エラーデータリスト
		List<RetirementPayAndHealthInsurance> importList = new ArrayList<>();//導入されたデータリスト
		Map<String, List<RetirementPayAndHealthInsurance>> map = new HashMap<String, List<RetirementPayAndHealthInsurance>>();//リターン値

		//ファイル名をチェック
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new ExcelException("違うファイルフォーマット");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		//Excel 2003否か　ワークブックを作成
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new HSSFWorkbook(is);
		}

		//シートチェック
		boolean notNull = false;
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = true;
		}

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {//二行目から内容スギャン
			Row row = sheet.getRow(r);
			RetirementPayAndHealthInsurance listUser = new RetirementPayAndHealthInsurance();
			//空白行はスキップ
			if (row == null) {
				continue;
			} else if (common.isRowEmpty(row)) {
				continue;
			}

			//一行の九項目日付以外のセールをストリング型に設定する
			//最初の値を取得する、一行のループを終えったら下記のロジックで処理する
			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(CellType.STRING);
				listUser.setEmpCd(row.getCell(0).getStringCellValue());
			}
			if (row.getCell(1) != null) {
				row.getCell(1).setCellType(CellType.STRING);
				listUser.setState(row.getCell(1).getStringCellValue());
			}
			if (row.getCell(2) != null) {
				row.getCell(2).setCellType(CellType.STRING);
				listUser.setInsuranceLine(row.getCell(2).getStringCellValue());
			}
			//空白でない場合日付を入れる
			if (row.getCell(3) != null) {
				listUser.setApplicationDate(row.getCell(3).getDateCellValue());
			}
			//空白でない場合日付を入れる
			if (row.getCell(4) != null) {
				listUser.setStartingDate(row.getCell(4).getDateCellValue());
			}
			//空白でない場合日付を入れる
			if (row.getCell(5) != null) {
				listUser.setQuitDate(row.getCell(5).getDateCellValue());
			}
			if (row.getCell(6) != null) {
				row.getCell(6).setCellType(CellType.STRING);
				listUser.setInturanceNote(row.getCell(6).getStringCellValue());
			}
			if (row.getCell(7) != null) {
				row.getCell(7).setCellType(CellType.STRING);
				listUser.setHealthInsurance(row.getCell(7).getStringCellValue());
			}
			if (row.getCell(8) != null) {
				row.getCell(8).setCellType(CellType.STRING);
				listUser.setRetirementPayAndHealthInsuranceComment(row.getCell(8).getStringCellValue());
			}

			String empCd = null ;//社員番号
			if (row.getCell(0) != null) {
				//上記セールタイプ　すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}
			//社員番号検索した結果
			EmpInfo searchList = empinfoMapper.getEmpInfoByEmpCd(empCd);
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {//社員番号 がempinfo表と合致した場合続行
					List<RetirementPayAndHealthInsurance> retireList = retirementPayAndHealthInsuranceMapper
							.search_empCd(empCd);
					if (retireList.size() != 0) {//年金画面のデータベースの社員番号があるかの確認
						retirementPayAndHealthInsuranceMapper.update(listUser);
					} else {
						retirementPayAndHealthInsuranceMapper.add(listUser);
					}
					importList.add(listUser);
					map.put("importList", importList);
				}
			} else {
				//error データリターンする  //存在しない社員番号を判定したらエラーリストに入れる
				String errorMessage = "社員番号エラー";
				listUser.setRetirementPayAndHealthInsuranceComment(errorMessage);
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
	public Map<String,Object> pageTurn(Integer pageNum,List<RetirementPayAndHealthInsurance> retailList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)retailList.size() / pageSize) );
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


