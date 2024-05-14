package com.company.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.RetirementPayAndHealthInsurance;
import com.company.demo.bean.WorkingStatus;
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;
import com.company.demo.mapper.WorkingStatusMapper;
import com.github.pagehelper.Page;

@Service
public class WorkingStatusService {
	@Resource

 WorkingStatusMapper workingStatusmapper;
	@Resource
	EmpInfoMapper empinfoMapper;
	@Resource
	HttpSession session;
	// list
	public List<WorkingStatus> searchWorkingStatusList(){
		List<WorkingStatus> listworkingstatus =  workingStatusmapper.searchWorkingStatusList();
    	return listworkingstatus;
	}
	// search by empCd
    public List<WorkingStatus> search_empCd(@Param("keyword")String keyword){
   	List<WorkingStatus> listemp =  workingStatusmapper.search_empCd(keyword);
   	return listemp;
    }
	// delete by empCd
	public void WorkingStatus_delete(String empCd) {
		workingStatusmapper.WorkingStatus_delete(empCd);
	}
	// update by empCd

	public void WorkingStatus_update(WorkingStatus WorkingStatus) {
		workingStatusmapper.WorkingStatus_update(WorkingStatus);
	}

	public WorkingStatus getWorkingStatusByEmpCd(String empCd) {
		WorkingStatus empInfo = workingStatusmapper.getWorkingStatusByEmpCd(empCd);
		return empInfo;
	}
	//excelExport
	@Resource
	Common common;

	public void excelExport(HttpServletResponse response,List<WorkingStatus> workingstatuslist ,HttpSession session)
			throws IOException {
	if(workingstatuslist != null) {
		HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet("获取excel表格");
	HSSFRow row = null;
	row = sheet.createRow(0);
	row.setHeight((short) (22.50 * 20));//设置行高

	row.createCell(0).setCellValue("社員番号");//为第一个单元格设值
	row.createCell(1).setCellValue("名前");//为第二个单元格设值
	row.createCell(2).setCellValue("性別");//为第三个单元格设值
	row.createCell(3).setCellValue("所属");
	row.createCell(4).setCellValue("連絡先");
	row.createCell(5).setCellValue("案件名");
	row.createCell(6).setCellValue("勤務地");
	row.createCell(7).setCellValue("最寄り駅");
	row.createCell(8).setCellValue("交通費");
	row.createCell(9).setCellValue("開始日");
	row.createCell(10).setCellValue("終了予定日");
	row.createCell(11).setCellValue("上限作業時間");
	row.createCell(12).setCellValue("下限作業時間");
	row.createCell(13).setCellValue("作業時間");
	row.createCell(14).setCellValue("精算有無");
	row.createCell(15).setCellValue("請求金額");
	row.createCell(16).setCellValue("契約先");
	row.createCell(17).setCellValue("契約会社名");
	row.createCell(18).setCellValue("業務担当部署");
	row.createCell(19).setCellValue("連絡先担当者");
	row.createCell(20).setCellValue("連絡先番号");
	row.createCell(21).setCellValue("連絡先メールアドレス");
	row.createCell(22).setCellValue("契約条件");
	row.createCell(23).setCellValue("支払条件");
	row.createCell(24).setCellValue("本社担当者");
	row.createCell(25).setCellValue("備考");
	//リスト内容
	for (int i = 0; i < workingstatuslist.size(); i++) {
		row = sheet.createRow(i + 1);
		CellStyle cellStyle = wb.createCellStyle();
		CreationHelper creationHelper = wb.getCreationHelper();
		cellStyle.setDataFormat(
				creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
				);
		//日付フォーマットを設定する、セール別で応用する
		WorkingStatus user =workingstatuslist.get(i);
		row.createCell(0).setCellValue(user.getEmpCd());
		row.createCell(1).setCellValue(user.getNameKanji());
		row.createCell(2).setCellValue(user.getSex());
		row.createCell(3).setCellValue(user.getCompany());
		row.createCell(4).setCellValue(user.getTelNo());
		row.createCell(5).setCellValue(user.getProjectName());
		row.createCell(6).setCellValue(user.getProjectAddress());

		row.createCell(7).setCellValue(user.getProjectStation());
		row.createCell(8).setCellValue(user.getTransportationFee());
		//nullのdateを除外する
		if (user.getStartingDay()!=null) {
			Cell cell = row.createCell(9);
			cell.setCellValue(user.getStartingDay());
			cell.setCellStyle(cellStyle);
		}

		if (user.getEndingDay()!=null) {
			Cell cell = row.createCell(10);
			cell.setCellValue(user.getEndingDay());
			cell.setCellStyle(cellStyle);
		}

		row.createCell(11).setCellValue(user.getMaxWorkTime());
		row.createCell(12).setCellValue(user.getMinWorkTime());
		row.createCell(13).setCellValue(user.getWorkTime());
		row.createCell(14).setCellValue(user.getActuarial());
		row.createCell(15).setCellValue(user.getRequestAmount());
		row.createCell(16).setCellValue(user.getContractCompanyAddress());
		row.createCell(17).setCellValue(user.getContractCompanyName());
		row.createCell(18).setCellValue(user.getProjectDepartment());
		row.createCell(19).setCellValue(user.getProjecetLeader());
		row.createCell(20).setCellValue(user.getProjectTelNo());
		row.createCell(21).setCellValue(user.getProjectEmail());
		row.createCell(22).setCellValue(user.getContractType());
		row.createCell(23).setCellValue(user.getPaymentTerm());
		row.createCell(24).setCellValue(user.getCompanyLeader());
		row.createCell(25).setCellValue(user.getWorkingStatusComment());
	}
	sheet.setDefaultRowHeight((short) (16.5 * 20));
	// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
	for (int i = 0; i <= 13; i++) {
		sheet.setColumnWidth(i,255*15);
	}

	response.setContentType("application/vnd.ms-excel;charset=utf-8");
	OutputStream os = response.getOutputStream();
	String exportValue = "attachment;filename=" + common.getDate() + "_Workingstatus" + ".xls";
	String errorValue = "attachment;filename=" + common.getDate() + "_Error_Workingstatus"
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
	@SuppressWarnings({ "deprecation", "unused" })
	public Map<String, List<WorkingStatus>> batchImport(String fileName, MultipartFile file)
			throws Exception{

		Map<String,List<WorkingStatus>> map = new HashMap();
		List<WorkingStatus> importList  = new ArrayList<>();
		List<WorkingStatus> errorList  = new ArrayList<>();

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
			WorkingStatus listUser = new WorkingStatus();
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
	/*
			String empCd = row.getCell(0).getStringCellValue();//社員番号取得
			WorkingStatus listUser = new WorkingStatus();

			List<WorkingStatus> searchList =workingStatusmapper.search_empCd(empCd);
			if(empCd == null || empCd.isEmpty()){//社員番号空白判断
				//ループでセール 値空白かどうか判断する
				errorList.add(listUser);
				//listUserに入れたら、errorlist か retailListか入れる
			}else {
				//一行の25項目をストリング型に設定する

				listUser.setEmpCd(empCd);
			}
*/
			if(row.getCell(1)!=null) {
				row.getCell(1).setCellType(CellType.STRING);
				listUser.setNameKanji(row.getCell(1).getStringCellValue());
			}
			if(row.getCell(2)!=null) {
				row.getCell(2).setCellType(CellType.STRING);
				listUser.setSex(row.getCell(2).getStringCellValue());
			}
			if(row.getCell(3)!=null) {
				row.getCell(3).setCellType(CellType.STRING);
				listUser.setCompany(row.getCell(3).getStringCellValue());
			}
			if(row.getCell(4)!=null) {
				row.getCell(4).setCellType(CellType.STRING);
				listUser.setTelNo(row.getCell(4).getStringCellValue());
			}
			if(row.getCell(5)!=null) {
				row.getCell(5).setCellType(CellType.STRING);
				listUser.setProjectName(row.getCell(5).getStringCellValue());
			}
			if(row.getCell(6)!=null) {
				row.getCell(6).setCellType(CellType.STRING);
				listUser.setProjectAddress(row.getCell(6).getStringCellValue());
			}
			if(row.getCell(7)!=null) {
				row.getCell(7).setCellType(CellType.STRING);
				listUser.setProjectStation(row.getCell(7).getStringCellValue());
			}
			if(row.getCell(8)!=null) {
				row.getCell(8).setCellType(CellType.STRING);
				listUser.setTransportationFee(row.getCell(8).getStringCellValue());
			}
			//空白でない場合日付を入れる
			if(row.getCell(9)!=null) {
				listUser.setStartingDay(row.getCell(9).getDateCellValue());
			}
			if(row.getCell(10)!=null) {
				listUser.setEndingDay(row.getCell(10).getDateCellValue());
			}
			if(row.getCell(11)!=null) {
				row.getCell(11).setCellType(CellType.STRING);
				listUser.setMaxWorkTime(row.getCell(11).getStringCellValue());
			}

			if(row.getCell(12)!=null) {
				row.getCell(12).setCellType(CellType.STRING);
				listUser.setMinWorkTime(row.getCell(12).getStringCellValue());
			}
			if(row.getCell(13)!=null) {
				row.getCell(13).setCellType(CellType.STRING);
				listUser.setWorkTime(row.getCell(13).getStringCellValue());
			}
			if(row.getCell(14)!=null) {
				row.getCell(14).setCellType(CellType.STRING);
				listUser.setActuarial(row.getCell(14).getStringCellValue());
			}
			if(row.getCell(15)!=null) {
				row.getCell(15).setCellType(CellType.STRING);
				listUser.setRequestAmount(row.getCell(15).getStringCellValue());
			}
			if(row.getCell(16)!=null) {
				row.getCell(16).setCellType(CellType.STRING);
				listUser.setContractCompanyAddress(row.getCell(16).getStringCellValue());
			}
			if(row.getCell(17)!=null) {
				row.getCell(17).setCellType(CellType.STRING);
				listUser.setContractCompanyName(row.getCell(17).getStringCellValue());
			}
			if(row.getCell(18)!=null) {
				row.getCell(18).setCellType(CellType.STRING);
				listUser.setProjectDepartment(row.getCell(18).getStringCellValue());
			}
			if(row.getCell(19)!=null) {
				row.getCell(19).setCellType(CellType.STRING);
				listUser.setProjecetLeader(row.getCell(19).getStringCellValue());
			}
			if(row.getCell(20)!=null) {
				row.getCell(20).setCellType(CellType.STRING);
				listUser.setProjectTelNo(row.getCell(20).getStringCellValue());
			}
			if(row.getCell(21)!=null) {
				row.getCell(21).setCellType(CellType.STRING);
				listUser.setProjectEmail(row.getCell(21).getStringCellValue());
			}
			if(row.getCell(22)!=null) {
				row.getCell(22).setCellType(CellType.STRING);
				listUser.setContractType(row.getCell(22).getStringCellValue());
			}
			if(row.getCell(23)!=null) {
				row.getCell(23).setCellType(CellType.STRING);
				listUser.setPaymentTerm(row.getCell(23).getStringCellValue());
			}
			if(row.getCell(24)!=null) {
				row.getCell(24).setCellType(CellType.STRING);
				listUser.setCompanyLeader(row.getCell(24).getStringCellValue());
			}
			if(row.getCell(25)!=null) {
				row.getCell(25).setCellType(CellType.STRING);
				listUser.setWorkingStatusComment(row.getCell(25).getStringCellValue());
			}

			String empCd = null ;
			if (row.getCell(0) != null) {
				//上記セールタイプ　すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}
			EmpInfo searchList = empinfoMapper.getEmpInfoByEmpCd(empCd);
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {//社員番号 がempinfo表と合致した場合続行
					List<WorkingStatus> workingStatusList =workingStatusmapper.search_empCd(empCd);
					if (workingStatusList.size() != 0) {//データベースの社員番号があるかの確認
						workingStatusmapper.WorkingStatus_update(listUser);
					} else {
						workingStatusmapper.add(listUser);
					}
					importList.add(listUser);
					map.put("importList", importList);
				}
			} else {
				//error データリターンする  //存在しない社員番号を判定したらエラーリストに入れる
				String errorMessage = "社員番号エラー";
				listUser.setWorkingStatusComment(errorMessage);
				errorList.add(listUser);
				map.put("errorList", errorList);

			}
		}
			/*if(searchList.size()==0) {//存在しない社員番号を判定したらエラーリストに入れる
				errorList.add(listUser);
				map.put("errorList", errorList);
			}else {
				//社員番号合致した場合更新する
				if(searchList.get(0).getEmpCd().equals(empCd)) {
					workingStatusmapper.WorkingStatus_update(listUser);
					importList.add(listUser);
					map.put("importList",importList);
				}else {
					//error データリターンする
					errorList.add(listUser);
					map.put("errorList",errorList);
				}
			}
	}*/
	wb.close();
	is.close();
	return map;
}

/*	Page*/

	public static final Integer pageSize = 1 ;
	public Map<String,Object> pageTurn(Integer pageNum,List<WorkingStatus> workingStatusList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)workingStatusList.size() / pageSize) );
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
