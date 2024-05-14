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
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;

@Service
public class EmpInfoService {
	@Resource
	EmpInfoMapper empInfoMapper;
	@Resource
	Common common;

	// list
	public List<EmpInfo> searchEmpList() {
		List<EmpInfo> emplist = empInfoMapper.searchEmpList();
		return emplist;
	}

	// list2 get
	// public List<EmpInfo> getEmpInfo();

	public List<EmpInfo> getEmpInfo() {
		List<EmpInfo> emplist = empInfoMapper.getEmpInfo();
		return emplist;

	}

	// search by empCd
	public List<EmpInfo> search_empCd(@Param("keyword") String keyword) {
		List<EmpInfo> listemp = empInfoMapper.search_empCd(keyword);
		return listemp;
	}

	// delete by empCd
	public void delete(String empCd) {
		empInfoMapper.delete(empCd);
	}

	// add
	public void add(EmpInfo empInfo) {
		empInfoMapper.add(empInfo);

	}
	// update by empCd

	public void update(EmpInfo empInfo) {
		empInfoMapper.update(empInfo);
	}

	public EmpInfo getEmpInfoByEmpCd(String empCd) {
		EmpInfo empInfo = empInfoMapper.getEmpInfoByEmpCd(empCd);
		return empInfo;
	}

	// add empCd into the other tables
	public void addEngineerList(String empCd) {
		empInfoMapper.addEngineerList(empCd);

	}

	public void addHumanResource(String empCd) {
		empInfoMapper.addHumanResource(empCd);

	}

	public void addRetirementPayAndHealthInsurance(String empCd) {
		empInfoMapper.addRetirementPayAndHealthInsurance(empCd);

	}

	public void addWorkingStatus(String empCd) {
		empInfoMapper.addWorkingStatus(empCd);

	}

	public void addWorkInsurance(String empCd) {
		empInfoMapper.addWorkInsurance(empCd);

	}

	// details by empCd

	//excelExport
	public void excelExport(HttpServletResponse response,List<EmpInfo> empList,HttpSession session) throws IOException {
		if (empList != null) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("基本情報リスト");
			HSSFRow row = null;
			/*
			 * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where
			 * table_name='user' and table_schema='test' 第一个table_name 表名字 第二个table_name
			 * 数据库名称*
			 */
			row = sheet.createRow(0);
			row.setHeight((short) (22.50 * 20));// 设置行高
			row.createCell(0).setCellValue("社員番号");
			row.createCell(1).setCellValue("個人メールアドレス");
			row.createCell(2).setCellValue("会社メールアドレス）");
			row.createCell(3).setCellValue("名前（漢字）");
			row.createCell(4).setCellValue("名前（カナ）");
			row.createCell(5).setCellValue("性別");
			row.createCell(6).setCellValue("生年月日");
			row.createCell(7).setCellValue("国籍");
			row.createCell(8).setCellValue("郵便番号");

			row.createCell(9).setCellValue("住所");
			row.createCell(10).setCellValue("電話番号");
			row.createCell(11).setCellValue("最寄駅");
			row.createCell(12).setCellValue("入社日付");
			row.createCell(13).setCellValue("退職日付");
			row.createCell(14).setCellValue("所属会社");
			row.createCell(15).setCellValue("社員区分");
			row.createCell(16).setCellValue("備考");


			// リスト内容
			for (int i = 0; i < empList.size(); i++) {
				row = sheet.createRow(i + 1);
				CellStyle cellStyle = wb.createCellStyle();
				CreationHelper creationHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
				// 日付フォーマットを設定する、セール別で応用する
				EmpInfo empInfo = empList.get(i);
				row.createCell(0).setCellValue(empInfo.getEmpCd());
				row.createCell(1).setCellValue(empInfo.getEmail());
				row.createCell(2).setCellValue(empInfo.getCompanyEmail());
				row.createCell(3).setCellValue(empInfo.getNameKanji());
				row.createCell(4).setCellValue(empInfo.getNameKana());
				// nullのdateを除外する
				row.createCell(5).setCellValue(empInfo.getSex());
				if (empInfo.getBirthday() != null) {
					Cell cell = row.createCell(6);
					cell.setCellValue(empInfo.getBirthday());
					cell.setCellStyle(cellStyle);
				}

				row.createCell(7).setCellValue(empInfo.getCountry());
				row.createCell(8).setCellValue(empInfo.getPostcode());
				row.createCell(9).setCellValue(empInfo.getAddress());

				row.createCell(10).setCellValue(empInfo.getTelNo());
				row.createCell(11).setCellValue(empInfo.getHomeStation());

				if (empInfo.getEnteringDate() != null) {
					Cell cell = row.createCell(12);
					cell.setCellValue(empInfo.getEnteringDate());
					cell.setCellStyle(cellStyle);
				}
				if (empInfo.getDimissionDate() != null) {
					Cell cell = row.createCell(13);
					cell.setCellValue(empInfo.getDimissionDate());
					cell.setCellStyle(cellStyle);
				}

				row.createCell(14).setCellValue(empInfo.getCompany());
				row.createCell(15).setCellValue(empInfo.getEmployeeType());
				row.createCell(16).setCellValue(empInfo.getComment());

			}
			sheet.setDefaultRowHeight((short) (16.5 * 20));
			// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
			for (int i = 0; i < 10; i++) {
				sheet.setColumnWidth(i, 255 * 15);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			OutputStream os = response.getOutputStream();
			String exportValue = "attachment;filename=" + common.getDate() + "_EmpInfo" + ".xls";
			String errorValue = "attachment;filename=" + common.getDate() + "_Error_EmpInfo"
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
		}
	}
	// excelImport
	@SuppressWarnings("deprecation")
	public Map<String, List<EmpInfo>> batchImport(String fileName, MultipartFile file) throws Exception {

		List<EmpInfo> errorList = new ArrayList<>();// エラーデータリスト
		List<EmpInfo> importList = new ArrayList<>();// 導入されたデータリスト
		Map<String, List<EmpInfo>> map = new HashMap();// リターン値

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

			// RetirementPayAndHealthInsurance listUser = new
			// RetirementPayAndHealthInsurance();

			EmpInfo listUser = new EmpInfo();
			// 空白行はスキップ
			if (row == null) {
				continue;
			} else if (common.isRowEmpty(row)) {
				continue;
			}

			// 一行の九項目日付以外のセールをストリング型に設定する
			// 最初の値を取得する、一行のループを終えったら下記のロジックで処理する




			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(CellType.STRING);
				listUser.setEmpCd(row.getCell(0).getStringCellValue());

			}
			if (row.getCell(1) != null) {
				row.getCell(1).setCellType(CellType.STRING);
				listUser.setEmail(row.getCell(1).getStringCellValue());

			}
			if (row.getCell(2) != null) {
				row.getCell(2).setCellType(CellType.STRING);
				listUser.setCompanyEmail(row.getCell(2).getStringCellValue());
			}
			if (row.getCell(3) != null) {
				row.getCell(3).setCellType(CellType.STRING);
				row.getCell(3).setCellType(CellType.STRING);
				listUser.setNameKanji(row.getCell(3).getStringCellValue());
			}

			if (row.getCell(4) != null) {
				row.getCell(4).setCellType(CellType.STRING);
				listUser.setNameKana(row.getCell(4).getStringCellValue());
			}

			if (row.getCell(5) != null) {
				row.getCell(5).setCellType(CellType.STRING);
				listUser.setSex(row.getCell(5).getStringCellValue());
			}
			// 空白でない場合日付を入れる
			if (row.getCell(6) != null) {
				listUser.setBirthday(row.getCell(6).getDateCellValue());
			}

			if (row.getCell(7) != null) {
				row.getCell(7).setCellType(CellType.STRING);
				listUser.setCountry(row.getCell(7).getStringCellValue());
			}
			if (row.getCell(8) != null) {
				row.getCell(8).setCellType(CellType.STRING);
				listUser.setPostcode(row.getCell(8).getStringCellValue());
			}
			if (row.getCell(9) != null) {
				row.getCell(9).setCellType(CellType.STRING);
				listUser.setAddress(row.getCell(9).getStringCellValue());
			}
			if (row.getCell(10) != null) {
				row.getCell(10).setCellType(CellType.STRING);
				listUser.setTelNo(row.getCell(10).getStringCellValue());
			}
			if (row.getCell(11) != null) {
				row.getCell(11).setCellType(CellType.STRING);
				listUser.setHomeStation(row.getCell(11).getStringCellValue());
			}

			// 空白でない場合日付を入れる
			if (row.getCell(12) != null) {
				listUser.setEnteringDate(row.getCell(12).getDateCellValue());
			}
			// 空白でない場合日付を入れる
			if (row.getCell(13) != null) {
				listUser.setDimissionDate(row.getCell(13).getDateCellValue());
			}

			if (row.getCell(14) != null) {
				row.getCell(14).setCellType(CellType.STRING);
				listUser.setCompany(row.getCell(14).getStringCellValue());
			}

			if (row.getCell(15) != null) {
				row.getCell(15).setCellType(CellType.STRING);
				listUser.setEmployeeType(row.getCell(15).getStringCellValue());
			}

			if (row.getCell(16) != null) {
				row.getCell(16).setCellType(CellType.STRING);
				listUser.setComment(row.getCell(16).getStringCellValue());
			}

			String empCd = null;// 社員番号
			if (row.getCell(0) != null) {
				// 上記セールタイプ すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}
			// 社員番号検索した結果

			EmpInfo searchList = empInfoMapper.getEmpInfoByEmpCd(empCd);

			/*
			 * if (searchList != null) { if (searchList.getEmpCd().equals(empCd)) {//社員番号
			 * がempinfo表と合致した場合続行 List<EmpInfo> empList = empInfoMapper.search_empCd(empCd);
			 * if (empList.size() != 0) {//年金画面のデータベースの社員番号があるかの確認
			 * empInfoMapper.update(listUser); } else { empInfoMapper.add(listUser); }
			 * importList.add(listUser); map.put("importList", importList); } } else {
			 * //error データリターンする //存在しない社員番号を判定したらエラーリストに入れる errorList.add(listUser);
			 * map.put("errorList", errorList); }
			 */

			// start by wt
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {// 社員番号 がempinfo表と合致した場合続行
					List<EmpInfo> empList = empInfoMapper.search_empCd(empCd);
					if (empList != null) {// 年金画面のデータベースの社員番号があるかの確認
						empInfoMapper.update(listUser);
					} else {
						empInfoMapper.add(listUser);
					}
					importList.add(listUser);
					map.put("importList", importList);
				}else {
					//do nothing
				}
			} else {
				// error データリターンする //存在しない社員番号を判定したらエラーリストに入れる
				errorList.add(listUser);
				map.put("errorList", errorList);
			}
			// end by wt
		}

		wb.close();
		is.close();
		return map;
	}
	/*	Page*/

	public static final Integer pageSize = 1 ;
	public Map<String,Object> pageTurn(Integer pageNum,List<EmpInfo> empList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)empList.size() / pageSize) );
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
