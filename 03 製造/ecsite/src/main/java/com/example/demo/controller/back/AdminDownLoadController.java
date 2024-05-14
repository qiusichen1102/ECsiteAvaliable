package com.example.demo.controller.back;

import com.example.demo.bean.front.Stock;
import com.example.demo.service.back.AdminStockServiceImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminDownLoadController {
    @Autowired
    private AdminStockServiceImpl adminStockServiceImpl;

    @GetMapping("/downloadCSV")
    public ResponseEntity<InputStreamResource> downloadCsv() throws UnsupportedEncodingException {
        // csvデータを生成する
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        StringBuilder csvData = new StringBuilder();
        csvData.append("ID,商品名,詳細,価格,在庫数,登録日時\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

        for (Stock stock : stocks) {
            String createdAt = stock.getCreated_at().format(formatter);
            csvData.append(""+stock.getId()+","+stock.getName()+","+stock.getDetail()+","+stock.getPrice()+","+stock.getQuantity()+","+createdAt+"\n");
        }

        // csvデータをバイトに変換する
        byte[] csvBytes = csvData.toString().getBytes(StandardCharsets.UTF_8);

        // HttpHeadersを設定する
        HttpHeaders headers = new HttpHeaders();
        String filename = "data.csv";

        // ASCIIではない文字列を処理する
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        encodedFilename = encodedFilename.replace("+", "%20"); // スペースのエンコーディングを置換する
        headers.setContentDispositionFormData("attachment", encodedFilename);
        headers.setContentType(new MediaType("text", "csv", StandardCharsets.UTF_8));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        //「InputStreamResource」オブジェクトを作成して、レスポンスエンティティを返す
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(csvBytes));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/downloadPDF")
    public ResponseEntity<byte[]> downloadPdf(HttpServletResponse response) throws IOException, DocumentException, com.itextpdf.text.DocumentException {
        // csvデータを生成する
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

        // PDFドキュメントを生成する
        Document document = new Document(new RectangleReadOnly(842F, 595F));
        document.addTitle("Pdfダウンロード");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        // 日本語のフォントを設定する
        BaseFont bfJapanese = BaseFont.createFont("fonts/ipaexg.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font japaneseFont = new Font(bfJapanese, 12);

        Font font = new Font(bfJapanese,25);
        Paragraph title = new Paragraph("在庫管理　商品一覧表",font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(Chunk.NEWLINE);  // 空白行
        // pdfテーブルを生成する
        PdfPTable table = new PdfPTable(6);
        //cell.setHorizontalAlignment(Element.ALIGN_CENTER); 

        table.addCell(new Phrase("商品ID", japaneseFont));
        table.addCell(new Phrase("商品名", japaneseFont));
        table.addCell(new Phrase("説明文", japaneseFont));
        table.addCell(new Phrase("価格", japaneseFont));
        table.addCell(new Phrase("在庫数", japaneseFont));
        table.addCell(new Phrase("登録日時", japaneseFont));

        for (Stock stock : stocks) {
            String createdAt = stock.getCreated_at().format(formatter);
            PdfPCell cell = new PdfPCell();

            cell.setHorizontalAlignment(Element.ALIGN_CENTER); // セルの内容を水平方向に中央揃える
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // セルの内容を垂直方向に中央揃えする

            cell.setPhrase(new Phrase(String.valueOf(stock.getId()), japaneseFont));
            table.addCell(cell);

            cell.setPhrase(new Phrase(stock.getName(), japaneseFont));
            table.addCell(cell);

            cell.setPhrase(new Phrase(stock.getDetail(), japaneseFont));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(stock.getPrice()), japaneseFont));
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(stock.getQuantity()), japaneseFont));
            table.addCell(cell);

            cell.setPhrase(new Phrase(createdAt, japaneseFont));
            table.addCell(cell);
        }
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // テーブルをドキュメントに追加する
        document.add(table);
        document.close();

        // 获取 PDF 字节数组
        byte[] pdfBytes = baos.toByteArray();

        // レスポンスヘッダーを設定する
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "data.pdf";

        // ASCIIではない文字列を処理する
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        encodedFilename = encodedFilename.replace("+", "%20");
        headers.setContentDispositionFormData("attachment", encodedFilename);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/downloadExcel")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
    	// データベースからデータを取得する
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        // 新しいワークブックを作成する
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Stocks");

        // 新しいワークブックを作成する
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("在庫管理　商品一覧表");

        // タイトルのスタイルを設定する
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCell.setCellStyle(titleCellStyle);

        // セルを結合する
        CellRangeAddress titleCellRange = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(titleCellRange);

        // ヘッダー行を作成する
        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("商品ID");
        headerRow.createCell(1).setCellValue("商品名");
        headerRow.createCell(2).setCellValue("説明文");
        headerRow.createCell(3).setCellValue("価格");
        headerRow.createCell(4).setCellValue("在庫数");
        headerRow.createCell(5).setCellValue("登録日時");

        // ヘッダー行を作成する
        int rowNum = 2;
        for (Stock stock : stocks) {
            String createdAt = stock.getCreated_at().format(formatter);
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stock.getId());
            row.createCell(1).setCellValue(stock.getName());
            row.createCell(2).setCellValue(stock.getDetail());
            row.createCell(3).setCellValue(stock.getPrice());
            row.createCell(4).setCellValue(stock.getQuantity());
            row.createCell(5).setCellValue(createdAt);
        }

        // ワークブックをバイト配列出力ストリームに書き込む
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        // レスポンスヘッダーを設定する
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        String filename = "data.xlsx";
        headers.setContentDispositionFormData("attachment", filename);

        // レスポンスヘッダーを設定する
        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }


}
