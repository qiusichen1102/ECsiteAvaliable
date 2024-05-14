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
import com.company.demo.bean.EngineerList;
import com.company.demo.bean.WorkingStatus;
import com.company.demo.common.Common;
import com.company.demo.common.ExcelException;
import com.company.demo.mapper.EmpInfoMapper;
import com.company.demo.mapper.EngineerListMapper;

@Service
public class EngineerListService {
	@Resource
	EngineerListMapper engineerListMapper;
	@Resource
	EmpInfoMapper empinfoMapper;

	public List<EngineerList> searchEngineerListList() {
		List<EngineerList> engineerListList = engineerListMapper.searchEngineerListList();
		return engineerListList;
	}
	/*, @Param("month") String month*/
	public List<EngineerList> search_empCd(@Param("keyword")String keyword,@Param("month")String month) {
		List<EngineerList> listEngineer = engineerListMapper.search_empCd(keyword,month);
    	return listEngineer;
	}

	public void engineerList_delete(String empCd) {
		engineerListMapper.engineerList_delete(empCd);

	}

	public EngineerList getEngineerListByEmpCd(String empCd) {
		EngineerList engineerList = engineerListMapper.getEngineerListByEmpCd(empCd);
		return engineerList;
	}

	public void EngineerList_update(EngineerList engineerList) {
		engineerListMapper.engineerList_update(engineerList);
	}

	public List<EngineerList> getEngineerList() {
		return engineerListMapper.getEngineer();

	}


	//excelExport
		@Resource
		Common common;
		public void excelExport(HttpServletResponse response,List<EngineerList> engineerList
				,HttpSession session) throws IOException {
			if(engineerList != null) {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("リスト");
				HSSFRow row = null;
				row = sheet.createRow(0);
				row.setHeight((short) (22.50 * 20));//设置行高
				row.createCell(0).setCellValue("社員番号");
				row.createCell(1).setCellValue("名前");
				row.createCell(2).setCellValue("性別");
				row.createCell(3).setCellValue("最寄駅");
				row.createCell(4).setCellValue("稼働開始日");

				row.createCell(5).setCellValue("実務年数");
				row.createCell(6).setCellValue("日本語");
				row.createCell(7).setCellValue("メイン言語");
				row.createCell(8).setCellValue("基本設計");
				row.createCell(9).setCellValue("詳細設計");
				row.createCell(10).setCellValue("製造");
				row.createCell(11).setCellValue("テスト");
				row.createCell(12).setCellValue("現在現場");
				row.createCell(13).setCellValue("レベル");
				row.createCell(14).setCellValue("新人");
				row.createCell(15).setCellValue("備考");
				//リスト内容
						for (int i = 0; i < engineerList.size(); i++) {
							row = sheet.createRow(i + 1);
							CellStyle cellStyle = wb.createCellStyle();
							CreationHelper creationHelper = wb.getCreationHelper();
							cellStyle.setDataFormat(
									creationHelper.createDataFormat().getFormat("yyyy-MM-dd")
									);
							//日付フォーマットを設定する、セール別で応用する
							EngineerList engineerList1 = engineerList.get(i);

							row.createCell(0).setCellValue(engineerList1.getEmpCd());
							row.createCell(1).setCellValue(engineerList1.getNameKanji());
							row.createCell(2).setCellValue(engineerList1.getSex());
							row.createCell(3).setCellValue(engineerList1.getHomeStation());


				          /*  SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");*/
							if(engineerList1.getWorkingDate()!=null)
							{
								/*row.createCell(4).setCellValue("");
							}
							else {
								row.createCell(4).setCellValue(date.format(engineerList1.getWorkingDate()));
							}*/
								Cell cell = row.createCell(4);
								cell.setCellValue(engineerList1.getWorkingDate());
								cell.setCellStyle(cellStyle);
							}
							row.createCell(5).setCellValue(engineerList1.getExperience());
							row.createCell(6).setCellValue(engineerList1.getJapanese());
							row.createCell(7).setCellValue(engineerList1.getTechlanguage());
							row.createCell(8).setCellValue(engineerList1.getBasicDesign());
							row.createCell(9).setCellValue(engineerList1.getDetailDesign());
							row.createCell(10).setCellValue(engineerList1.getProduce());
							row.createCell(11).setCellValue(engineerList1.getTest());
							row.createCell(12).setCellValue(engineerList1.getProject());
							row.createCell(13).setCellValue(engineerList1.getLevel());
							row.createCell(14).setCellValue(engineerList1.getNewEmployee());
							row.createCell(15).setCellValue(engineerList1.getEngineerListComment());
						}
						sheet.setDefaultRowHeight((short) (16.5 * 20));
						// 列幅を調整する。ここでは最初(0番目)の列からの幅を調整する。
						for(int i=0;i<20;i++) {
							sheet.setColumnWidth(i,255*15);
						}
						response.setContentType("application/vnd.ms-excel;charset=utf-8");
						OutputStream os = response.getOutputStream();
						String exportValue = "attachment;filename=" + common.getDate() + "_EngineerList"
								+ ".xls";
						String errorValue = "attachment;filename=" + common.getDate() + "_Error_EngineerList"
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



	// excelImport


	@SuppressWarnings("rawtypes")
	public Map<String, List<EngineerList>> batchImport(String fileName, MultipartFile file) throws IOException {

		List<EngineerList> errorList = new ArrayList<>();// エラーデータリスト
		List<EngineerList> importList = new ArrayList<>();// 導入されたデータリスト
		@SuppressWarnings("unchecked")
		Map<String, List<EngineerList>> map = new HashMap();// リターン値

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

		@SuppressWarnings("unused")
		boolean notNull = false;// シートチェック
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = true;
		}
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {// 二行目から内容スギャン
			Row row = sheet.getRow(r);
			EngineerList engineerList = new EngineerList();
			// 社員番号検索した結果
			if (row == null) {
				continue;
			} else if (common.isRowEmpty(row)) {
				continue;
			}

			if (row.getCell(0) != null) {
				row.getCell(0).setCellType(CellType.STRING);
				engineerList .setEmpCd(row.getCell(0).getStringCellValue());
			}
			if (row.getCell(1) != null) {
				row.getCell(1).setCellType(CellType.STRING);
				engineerList.setNameKanji(row.getCell(1).getStringCellValue());
			}
			if (row.getCell(2) != null) {
				row.getCell(2).setCellType(CellType.STRING);
				engineerList.setSex(row.getCell(2).getStringCellValue());
			}
			if (row.getCell(3) != null) {
				row.getCell(3).setCellType(CellType.STRING);
				engineerList.setHomeStation(row.getCell(3).getStringCellValue());
			}
			if (row.getCell(4) != null) {
				engineerList.setWorkingDate(row.getCell(4).getDateCellValue());
			}
			if (row.getCell(5) != null) {
				row.getCell(5).setCellType(CellType.STRING);
				engineerList.setExperience(row.getCell(5).getStringCellValue());
			}
			if (row.getCell(6) != null) {
				row.getCell(6).setCellType(CellType.STRING);
				engineerList.setJapanese(row.getCell(6).getStringCellValue());
			}
			if (row.getCell(7) != null) {
				row.getCell(7).setCellType(CellType.STRING);
				engineerList.setTechlanguage(row.getCell(7).getStringCellValue());
			}
			if (row.getCell(8) != null) {
				row.getCell(8).setCellType(CellType.STRING);
				engineerList.setBasicDesign(row.getCell(8).getStringCellValue());
			}
			if (row.getCell(9) != null) {
				row.getCell(9).setCellType(CellType.STRING);
				engineerList.setDetailDesign(row.getCell(9).getStringCellValue());
			}
			if (row.getCell(10) != null) {
				row.getCell(10).setCellType(CellType.STRING);
				engineerList.setProduce(row.getCell(10).getStringCellValue());
			}
			if (row.getCell(11) != null) {
				row.getCell(11).setCellType(CellType.STRING);
				engineerList.setTest(row.getCell(11).getStringCellValue());
			}
			if (row.getCell(12) != null) {
				row.getCell(12).setCellType(CellType.STRING);
				engineerList.setProject(row.getCell(12).getStringCellValue());
			}
			if (row.getCell(13) != null) {
				row.getCell(13).setCellType(CellType.STRING);
				engineerList.setLevel(row.getCell(13).getStringCellValue());
			}
			if (row.getCell(14) != null) {
				row.getCell(14).setCellType(CellType.STRING);
				engineerList.setNewEmployee(row.getCell(14).getStringCellValue());
			}
			if (row.getCell(15) != null) {
				row.getCell(15).setCellType(CellType.STRING);
				engineerList.setEngineerListComment(row.getCell(15).getStringCellValue());
			}

			String empCd = null ;//社員番号
			if (row.getCell(0) != null) {
				//上記セールタイプ　すでに設定した
				empCd = row.getCell(0).getStringCellValue();
			}



			EmpInfo searchList = empinfoMapper.getEmpInfoByEmpCd(empCd);
			if (searchList != null) {
				if (searchList.getEmpCd().equals(empCd)) {//社員番号 がempinfo表と合致した場合続行
					List<EngineerList> engineerList1 = engineerListMapper
							.search_empCd(empCd,null);
					if (engineerList1.size() != 0) {//年金画面のデータベースの社員番号があるかの確認
						engineerListMapper.engineerList_update(engineerList);
					} else {
						engineerListMapper.Engineer_import(engineerList);
					}
					importList.add(engineerList);
					map.put("importList", importList);
				}
			} else {
				//error データリターンする  //存在しない社員番号を判定したらエラーリストに入れる
				String errorMessage = "社員番号エラー";
				engineerList.setEngineerListComment(errorMessage);
				errorList.add(engineerList);
				map.put("errorList", errorList);
			}
		}




		wb.close();
		is.close();
		return map;
	}

	public static final Integer pageSize = 1 ;
	public Map<String,Object> pageTurn(Integer pageNum,List<EngineerList> engineerListList,String button) {
		Map<String,Object> result = new HashMap<String,Object>();

		//総ページ数を所得する
		int pageTotalNum = (int) (Math.ceil((double)engineerListList.size() / pageSize) );
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
