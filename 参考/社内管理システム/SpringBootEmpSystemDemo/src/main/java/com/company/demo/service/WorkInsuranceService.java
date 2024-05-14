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
import com.company.demo.bean.WorkInsurance;
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;
import com.company.demo.mapper.WorkInsuranceMapper;

@Service
public class WorkInsuranceService {
	@Resource
	WorkInsuranceMapper workInsuranceMapper;
	@Resource
	EmpInfoMapper empinfoMapper;

	// list
	 public List<WorkInsurance> searchWorkInsuranceList() {
     	List<WorkInsurance> emplist =  workInsuranceMapper.searchWorkInsuranceList();
     	return emplist;
	 }
	// search by empCd
	    public List<WorkInsurance> search_empCd(@Param("keyword")String keyword){
	    	List<WorkInsurance> listemp =  workInsuranceMapper.search_empCd(keyword);
	    	return listemp;
	    }
		// delete by empCd
		public void WorkInsurance_delete(String empCd) {
			workInsuranceMapper.WorkInsurance_delete(empCd);
		}
	// update by empCd

	public void WorkInsurance_update(WorkInsurance WorkInsurance) {
		workInsuranceMapper.WorkInsurance_update(WorkInsurance);
	}

	public WorkInsurance getWorkInsuranceByEmpCd(String empCd) {
		WorkInsurance empInfo = workInsuranceMapper.getWorkInsuranceByEmpCd(empCd);
		return empInfo;
	}
	//excelExport
		@Resource
		Common common;
	public void excelExport(HttpServletResponse response,List<WorkInsurance> workInsuranceList ,HttpSession session)
			throws IOException {
		if (workInsuranceList!=null) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("获取excel表格");
			HSSFRow row = null;
			row = sheet.createRow(0);
			row.setHeight((short) (22.50 * 20));//设置行高

				row.createCell(0).setCellValue("社員番号");//为第一个单元格设值
				row.createCell(1).setCellValue("名前（漢字）");//为第二个单元格设值
				row.createCell(2).setCellValue("名前（カナ）");//为第三个单元格设值
				row.createCell(3).setCellValue("加入状況");
				row.createCell(4).setCellValue("給与");
				row.createCell(5).setCellValue("申請日");
				row.createCell(6).setCellValue("取得年月日");
				row.createCell(7).setCellValue("喪失年月日");
				row.createCell(8).setCellValue("被保険番号");
				row.createCell(9).setCellValue("理由");
				row.createCell(10).setCellValue("備考");
				//リスト内容
				for (int i = 0; i < workInsuranceList.size(); i++) {
					row = sheet.createRow(i + 1);
					CellStyle cellStyle = wb.createCellStyle();
					CreationHelper creationHelper = wb.getCreationHelper();
					cellStyle.setDataFormat(
							creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
							);
					//日付フォーマットを設定する、セール別で応用する
					WorkInsurance user = workInsuranceList.get(i);
					row.createCell(0).setCellValue(user.getEmpCd());
					row.createCell(1).setCellValue(user.getNameKanji());
					row.createCell(2).setCellValue(user.getNameKana());
					row.createCell(3).setCellValue(user.getWorkInsuranceState());
					row.createCell(4).setCellValue(user.getSalary());
					if (user.getApplicationDate()!=null) {
						Cell cell = row.createCell(5);
						cell.setCellValue(user.getApplicationDate());
						cell.setCellStyle(cellStyle);
					}
					if (user.getStartingDate()!=null) {
						Cell cell = row.createCell(6);
						cell.setCellValue(user.getStartingDate());
						cell.setCellStyle(cellStyle);
					}
					if (user.getQuitDate()!=null) {
						Cell cell = row.createCell(7);
						cell.setCellValue(user.getQuitDate());
						cell.setCellStyle(cellStyle);
					}
					row.createCell(8).setCellValue(user.getWorkInsuranceNo());
					row.createCell(9).setCellValue(user.getReason());
					row.createCell(10).setCellValue(user.getWorkInsuranceComment());
					}
				sheet.setDefaultRowHeight((short) (16.5 * 20));
				// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
				for (int i = 0; i <= 13; i++) {
					sheet.setColumnWidth(i,255*15);
				}
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				OutputStream os = response.getOutputStream();
				String exportValue = "attachment;filename=" + common.getDate() + "_WorkInsurance" + ".xls";
				String errorValue = "attachment;filename=" + common.getDate() + "_Error_WorkInsurance"
						+ ".xls";

				if ((boolean) session.getAttribute("imported")) {
				response.setHeader("Content-disposition",errorValue);
				}//Excel名
				else {
					response.setHeader("Content-disposition", exportValue);//Excel名
				}

				wb.write(os);
				wb.close();
				os.flush();
				os.close();
					}
			}
	//excelImport
	@SuppressWarnings("deprecation")
	public Map<String, List<WorkInsurance>> batchImport(String fileName, MultipartFile file)
			throws Exception{


		List<WorkInsurance> importList  = new ArrayList<>();
		List<WorkInsurance> errorList  = new ArrayList<>();
		Map<String,List<WorkInsurance>> map = new HashMap<String,List<WorkInsurance>>();
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

		boolean notNull = false;//シートチェック
		Sheet sheet = wb.getSheetAt(0);
		if(sheet!=null){
			notNull = true;
		}

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {//二行目から内容スギャン
			Row row = sheet.getRow(r);
			WorkInsurance listUser = new WorkInsurance();
			//空白行はスキップ
			if (row == null){
				continue;
			}else if(common.isRowEmpty(row)) {
				continue;
			}
			//一行の九項目日付以外のセールをストリング型に設定する
			//最初の値を取得する、一行のループを終えったら下記のロジックで処理する
			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(CellType.STRING);
				listUser.setEmpCd(row.getCell(0).getStringCellValue());
			}
			if(row.getCell(1)!=null) {
				row.getCell(1).setCellType(CellType.STRING);
				listUser.setNameKanji(row.getCell(1).getStringCellValue());
			}
			if(row.getCell(2)!=null) {
				row.getCell(2).setCellType(CellType.STRING);
				listUser.setNameKana(row.getCell(2).getStringCellValue());
			}
			if(row.getCell(3)!=null) {
				row.getCell(3).setCellType(CellType.STRING);
				listUser.setWorkInsuranceState(row.getCell(3).getStringCellValue());
			}
			if(row.getCell(4)!=null) {
				row.getCell(4).setCellType(CellType.STRING);
				listUser.setSalary(row.getCell(4).getStringCellValue());
			}
			if(row.getCell(5)!=null) {
				listUser.setApplicationDate(row.getCell(5).getDateCellValue());
			}
			if(row.getCell(6)!=null) {
				listUser.setStartingDate(row.getCell(6).getDateCellValue());
			}
			if(row.getCell(7)!=null) {
				row.getCell(7).setCellType(CellType.STRING);
				listUser.setQuitDate(row.getCell(7).getDateCellValue());
			}
			if(row.getCell(8)!=null) {
				row.getCell(8).setCellType(CellType.STRING);
				listUser.setWorkInsuranceNo(row.getCell(8).getStringCellValue());
			}
			if(row.getCell(9)!=null) {
				row.getCell(9).setCellType(CellType.STRING);
				listUser.setReason(row.getCell(9).getStringCellValue());
			}
			if(row.getCell(10)!=null) {
				row.getCell(10).setCellType(CellType.STRING);
				listUser.setWorkInsuranceComment(row.getCell(10).getStringCellValue());
			}

			String empCd = null ;
			if (row.getCell(0) != null) {
				//上記セールタイプ　すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}
			EmpInfo searchList = empinfoMapper.getEmpInfoByEmpCd(empCd);
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {//社員番号 がempinfo表と合致した場合続行
					List<WorkInsurance> workInsuranceList =workInsuranceMapper.search_empCd(empCd);
					if (workInsuranceList.size() != 0) {//データベースの社員番号があるかの確認
						workInsuranceMapper.WorkInsurance_update(listUser);
					} else {
						workInsuranceMapper.add(listUser);
					}
					importList.add(listUser);
					map.put("importList", importList);
				}
			} else {
				//error データリターンする  //存在しない社員番号を判定したらエラーリストに入れる
				String errorMessage = "社員番号エラー";
				listUser.setWorkInsuranceComment(errorMessage);
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
	public Map<String,Object> pageTurn(Integer pageNum,List<WorkInsurance> workInsuranceList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)workInsuranceList.size() / pageSize) );
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
