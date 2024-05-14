package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.bean.MakerList;
import com.example.demo.mapper.MakerListMapper;

@Service
public class MakerListService {
	public static final String Addmaker = null;
	@Resource
	MakerListMapper makerListMapper;

	public List<MakerList> searchMakerListList() {
		List<MakerList>makerListList =  makerListMapper.searchMakerListList();
     	return makerListList;
	}

	public List<MakerList> search_manufacture(@Param("keyword")String keyword) {
		List<MakerList> listMaker = makerListMapper.search_manufacture(keyword);
    	return listMaker;
	}

	public void makerList_delete(String manufacture_id) {
		makerListMapper.MakerList_delete(manufacture_id);

	}

	public MakerList getMakerListByManufacture_id(String manufacture_id) {
		MakerList engineerList = makerListMapper.getMakerListByManufacture_id(manufacture_id);
		return engineerList;
	}

	public void MakerList_update(MakerList makerList) {
		makerListMapper.makerList_update(makerList);
	}

	// add
	public void Addmaker(MakerList makerList) {
		makerListMapper.Addmaker(makerList);

	}

	    
	
	public void makerListExcel(HttpServletResponse response, List<MakerList> makerList) throws IOException{
		if (makerList != null) {
			//ワークブックを作成する
			HSSFWorkbook workBook = new HSSFWorkbook();
			
			//ワークシートを作成する
			HSSFSheet sheet = workBook .createSheet("メーカー情報一覧表");
			HSSFRow row = null;

			//excelの一行目、テーブルのタイトルを作成する
			row = sheet.createRow(0);
			
			//行の高さを設定する
			row.setHeight((short) (22.50 * 20));
			//行のセルを作成
			row.createCell(1).setCellValue("メーカー情報一覧表");
			
			//excelの二行目、テーブルのヘーダを作成する
			row = sheet.createRow(1);
			//高さを設定する
			row.setHeight((short) (22.50 * 20));

			row.createCell(0).setCellValue("メーカーID");
			row.createCell(1).setCellValue("メーカー名前");
			row.createCell(2).setCellValue("メーカー詳細");
			row.createCell(3).setCellValue("表示状態");	

			//社員情報リス内容を読み込み
			for (int i = 0; i < makerList.size(); i++) {
				//excelの三行目から書き込む
				row = sheet.createRow(i + 2);
				//社員情報リストを一つずつ取り出す
				MakerList makerData = makerList.get(i);
				//社員番号の設定
				row.createCell(0).setCellValue(makerData.getManufacture_id());
				//社員名前の設定
				row.createCell(1).setCellValue(makerData.getManufacture_name());	
				//生年月日の設定
				row.createCell(2).setCellValue(makerData.getAbout_manufacture());
				//国籍の設定
				row.createCell(3).setCellValue(makerData.getPublication_status());
				
			}
			//行のデフォルトの高さを設定
			sheet.setDefaultRowHeight((short) (16.50 * 20));
			//列の幅を設定
			for (int i = 0; i < 15; i++) {
				sheet.setColumnWidth(i, 255 * 15);
			}
			//レスポンスのコンテンツタイプの設定
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			
	        //レスポンスにバイナリデータを書き込むのに適したServletOutputStreamを返す
			OutputStream outStream = response.getOutputStream();

			//attachment (ダウンロードすべきであることを示す
			//多くのブラウザーは filename 引数の値を使い、「名前を付けて保存」ダイアログを表示します) を最初の引数して指定
			String exportValue = "attachment; filename=" + "makerList" + ".xls";
			
			//レスポンスのヘーダを設定、ダウンロードしてローカルに保存する添付ファイルとするかを示す
			response.setHeader("Content-disposition", exportValue);//Excel名
			
			//このワークブックをOutputStreamに書き出す
			workBook .write(outStream);			
			//ワークブックを閉じる
			workBook .close();
			
			//出力ストリームをフラッシュして、バッファリングされていたすべての出力バイトを強制的に書き込みます
			outStream.flush();
			//出力ストリームを閉じる
			outStream.close();
		}		
	}
}
