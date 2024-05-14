package com.example.demo.controller.back;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.form.front.StockForm;
import com.example.demo.service.back.AdminStockServiceImpl;
import com.example.demo.util.ImgDownloadUtil;
import com.example.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Controller
public class AdminStockController {
    @Autowired
    private AdminStockServiceImpl adminStockServiceImpl;
    @Autowired
    private HttpSession session;
    private final int pageSize = 10;

    @GetMapping("/stock")
    public String stockIn(Model model,@ModelAttribute("form") @Valid StockForm stockForm,@RequestParam(defaultValue = "1") int pageNumber){
        String errorMsg;
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        if (adminUser != null) {
            //検索欄はNULLだと すべての商品を表示する
            if(stockForm.getName()==null){
                List<Stock> stocks = adminStockServiceImpl.stockFindAll();
                List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
                model.addAttribute("stocks",PagedContactList);
                session.setAttribute("stock",PagedContactList);

            }else{
                //キーワードをDBから情報を抽出する
                List<Stock> stocks = adminStockServiceImpl.stockFindByName(stockForm.getName());
                if(stocks!=null){
                    List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
                    session.setAttribute("stock",PagedContactList);
                    model.addAttribute("stocks",PagedContactList);
                }
            }
            return "admin/stock";
        }else{
            errorMsg="まだ登録していません、登録してください！";
            session.setAttribute("message",errorMsg);
            return "admin/login";
        }
    }

    //商品の新規登録
    @PostMapping("/stock")
    public String stockUpLoad(Model model,@RequestParam("file") MultipartFile file,@ModelAttribute("form") @Valid StockForm stockForm,@RequestParam(defaultValue = "1") int pageNumber){
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        if (adminUser != null) {
            String path = handleFileUpload(file);
            String filename = "./uploads/stock/"+path;
            adminStockServiceImpl.addStock(stockForm.getId(),stockForm.getName(),stockForm.getDetail(),stockForm.getPrice(),filename,stockForm.getQuantity(),
                    LocalDateTime.now(),LocalDateTime.now(),0,1);
            System.out.println(filename);
            List<Stock> stocks = adminStockServiceImpl.stockFindAll();
            List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
            session.setAttribute("stock",PagedContactList);
            model.addAttribute("stocks",PagedContactList);
            return "admin/stock";
        }else{
            return "admin/login";
        }
    }


    //商品のidによる詳細画面を遷移する
    @GetMapping("/stock_detail&{id}")
    public String stock_detail( @PathVariable String id,Model model){
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        if (adminUser != null) {
            Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(id));
            model.addAttribute("stock",stock);
            session.setAttribute("stock",stock);
            return "admin/stock_detail";
        }else{
            return "admin/login";
        }
    }

    //新規登録画面
    @GetMapping("/create")
    public String stock_Create(){
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        if (adminUser != null) {
            return "admin/create";
        }else{
            return "admin/login";
        }
    }

    //イメージファイルのアップロード機能
    public String handleFileUpload(@RequestParam("file")  MultipartFile file){
        try {
            // ユニークなファイル名を生成する、uplodasフォルダに新規ファイル作成する時に衝突しないのため
            String originalFileName = file.getOriginalFilename();
            String newFileName = UUID.randomUUID().toString() +"&"+originalFileName;
            String projectRootPath = System.getProperty("user.dir");
            String uploadDirectory = projectRootPath + "/src/main/resources/static/uploads/stock/";
            // ファイルの保存先のパスを構築する
            String filePath = Paths.get(uploadDirectory, newFileName).toString();
            File dest = new File(filePath);
            file.transferTo(dest);
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "ファイルのアップロードは失敗しました！";
        }
    }
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

  //商品のidによる変更画面を遷移する
    @GetMapping("/stock_edit&{id}")
    public String stockEdit(@PathVariable String id,Model model){
        Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(id));
        model.addAttribute("stock",stock);
        return "admin/stock_edit";
    }
    
  //商品のidによる削除する
    @PostMapping("/stock_delete&{id}")
    public String stockDelete(@PathVariable String id,Model model,@RequestParam(defaultValue = "1") int pageNumber){
        adminStockServiceImpl.stockDeleteById(Integer.parseInt(id),LocalDateTime.now());
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
        session.setAttribute("stock",PagedContactList);
        model.addAttribute("stocks",PagedContactList);
        return "admin/stock";
    }

  //商品のidによる削除して、GETで一覧画面を遷移する
    @GetMapping("/stock_delete&{id}")
    public String stockDeleteForGet(@RequestParam(defaultValue = "1") int pageNumber,Model model){
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
        session.setAttribute("stock",PagedContactList);
        model.addAttribute("stocks",PagedContactList);
        return "admin/stock";
    }
    //商品のidによる更新して、GETで一覧画面を遷移する
    @GetMapping("/stock_update&{id}")
    public String stockUpdateForGet(@RequestParam(defaultValue = "1") int pageNumber,Model model){
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
        session.setAttribute("stock",PagedContactList);
        model.addAttribute("stocks",PagedContactList);
        return "admin/stock";
    }

    //商品のidによる更新する
    @PostMapping("/stock_update&{id}")
    public String stockUpdateById(@PathVariable String id,Model model,@ModelAttribute("form") @Valid StockForm stockForm,@RequestParam("file") MultipartFile file,@RequestParam(defaultValue = "1") int pageNumber) {
        String filename ="";
        if(!file.isEmpty()){
            String path = handleFileUpload(file);
            filename = "./uploads/stock/"+path;
        }else{
            Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(id));
            filename= stock.getImgpath();
        }
        int current_version = adminStockServiceImpl.selectStockUpdateVersion(Integer.parseInt(id));
        adminStockServiceImpl.stockUpdateById(stockForm.getName(),stockForm.getDetail(),stockForm.getPrice(),filename,stockForm.getQuantity(),LocalDateTime.now(),Integer.parseInt(id),current_version);
        List<Stock> stocks = adminStockServiceImpl.stockFindAll();
        List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(stocks, pageSize, pageNumber, session, model);
        session.setAttribute("stock",PagedContactList);
        model.addAttribute("stocks",PagedContactList);
        return "admin/stock";
    }
    //画像をストリームで表示する
    @GetMapping("/imgDownload")
    public void imgDownload(@RequestParam("stockid") String stockid, HttpServletResponse response) throws Exception{
    	String decodedStockid = URLDecoder.decode(stockid, "UTF-8");
    	Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(decodedStockid));
    	ImgDownloadUtil.downloadImage( stock.getImgpath(), response);
    }
}
