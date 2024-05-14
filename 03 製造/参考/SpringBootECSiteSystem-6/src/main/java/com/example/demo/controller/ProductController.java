package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.ProductRepository;
import com.example.demo.bean.Product;
import com.example.demo.form.ProductForm;
import com.example.demo.service.ProductService;
import com.github.pagehelper.PageHelper;

@Controller
@ComponentScan({ "com.example.demo.service" })
@MapperScan("com.example.demo.mapper")

public class ProductController {
	@Autowired
	MessageSource messageSource;

	@Resource
	ProductService productService;

	@Autowired
	private ProductRepository prod;

	// list products
	@GetMapping("/product")
	public ModelAndView init1(@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "buttom") String buttom, ModelAndView modelAndView) throws Exception {
		// ページについてのボタンを処理する。
		if (buttom != null) {
			if (buttom.equals("次へ")) {
				pageNum++;
			}
			if (buttom.equals("前へ")) {
				pageNum--;
			}
		}
		// nullの場合、初期値を設置する。
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 5;

		}
		// 商品数を所得する
		List<Product> productList1 = productService.searchProductList();
		int countProduct = productList1.size();
		modelAndView.addObject("countProduct", countProduct);

		modelAndView.addObject("productList1", productList1);

		// ページ数を所得する
		int pageTotalNum;
		if (productList1.size() % pageSize == 0) {
			pageTotalNum = productList1.size() / pageSize;
		} else {
			pageTotalNum = productList1.size() / pageSize + 1;
		}

		// 最大値と最小値の設定。
		if (pageNum < 1) {
			pageNum = 1;
		} else if (pageNum > pageTotalNum) {
			pageNum = pageTotalNum;
		}
		// ページを分割する
		PageHelper.startPage(pageNum, pageSize);
		List<Product> productList11 = productService.searchProductList();
		modelAndView.addObject("productList1", productList11);

		modelAndView.addObject("pageTotalNum", pageTotalNum);

		modelAndView.addObject("pageNum", pageNum);
		modelAndView.addObject("pageSize", pageSize);
		modelAndView.setViewName("listproduct");

		return modelAndView;
	}

	// search product
	@GetMapping("searchproduct")
	public ModelAndView searchList(@Param(value = "buttom") String buttom, @Param(value = "keyword") String keyword,
			@Param(value = "pageNum") Integer pageNum, @Param(value = "pageSize") Integer pageSize,
			HttpServletRequest request, ModelAndView modelAndView) {
		if (keyword != null) {
			request.setAttribute("keyword", keyword);
		}
		/*
		 * else { return new ModelAndView("productList1"); }
		 */

		// ページについてのボタンを処理する。
		if (buttom != null) {
			if (buttom.equals("次へ")) {
				pageNum++;
			}
			if (buttom.equals("前へ")) {
				pageNum--;
			}
		}
		// nullの場合、初期値を設置する。
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 5;

		}
		// 商品数を所得する
		List<Product> productList1 = productService.searchProductList();
		int countProduct = productList1.size();
		modelAndView.addObject("countProduct", countProduct);

		modelAndView.addObject("productList1", productList1);

		// ページ数を所得する
		int pageTotalNum;
		if (productList1.size() % pageSize == 0) {
			pageTotalNum = productList1.size() / pageSize;
		} else {
			pageTotalNum = productList1.size() / pageSize + 1;
		}

		// 最大値と最小値の設定。
		if (pageNum < 1) {
			pageNum = 1;
		} else if (pageNum > pageTotalNum) {
			pageNum = pageTotalNum;
		}
		// ページを分割する
		PageHelper.startPage(pageNum, pageSize);
		List<Product> productList11 = productService.searchProduct(keyword);
		modelAndView.addObject("productList1", productList11);

		modelAndView.addObject("pageTotalNum", pageTotalNum);
		modelAndView.addObject("pageNum", pageNum);
		modelAndView.addObject("pageSize", pageSize);

		int countProduct1 = productList11.size();
		modelAndView.addObject("countProduct", countProduct1);
		modelAndView.setViewName("listproduct");

		return modelAndView;

	}

	// delete by productID
	@GetMapping("/deleteproduct")
	public ModelAndView deleteproduct(@RequestParam(value = "id", required = false) String product_id,
			ModelAndView modelAndView) {
		productService.deleteProduct(product_id);
		;
		System.out.println(product_id + ":" + "削除しました");
		modelAndView.setViewName("forward:/product");

		return modelAndView;
	}

	// ↓ details by productID
	@GetMapping("/detailsproduct")
	public ModelAndView detailsproduct(@RequestParam("id") String product_id, Model model) {
		System.out.print(product_id);
		Product productDetails = productService.searchProductInfoByID(product_id);
		model.addAttribute("product", productDetails);
		return new ModelAndView("detailproduct");
	}

	// update by product_id
	@GetMapping("toProductUpdate")
	public ModelAndView toproductlist(@RequestParam("id") String product_id, Model model) {
		// System.out.print(product_id);
		Product productUpdate = productService.searchProductInfoByID(product_id);
		model.addAttribute("product", productUpdate);
		return new ModelAndView("updateproduct");
	}

	@PostMapping("/updateproduct")
	public ModelAndView productupdate(@ModelAttribute("ProductForm") @Valid ProductForm productForm,
			BindingResult result,@RequestParam("product_imagefile") MultipartFile multipartFile,ModelAndView model) throws IOException {
		// Nullチェック
		if (result.hasErrors()) {
			List<ObjectError> errorList = result.getAllErrors();
			model.addObject("errorList", errorList);
			model.addObject("product", productForm);
			model.setViewName("updateproduct");
			return model;
		}

		Product product = new Product(productForm.getProduct_id(), productForm.getProduct_name(),
				productForm.getProduct_code(), productForm.getCategory_id(), productForm.getManufacture_id(),
				productForm.getProduct_price(), productForm.getProduct_quantity(), productForm.getProduct_description(),
				productForm.getPublication_status());

		 String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		 
		  product.setProduct_imagefile("img/"+productForm.getProduct_id()+"/"+fileName);
		  
		  
		  Product savedUser = prod.save(product);
		  
		  String uploadDir = "src/main/resources/static/img/" + savedUser.getProduct_id();
		  
		  ProductService.saveFile(uploadDir, fileName, multipartFile);
		productService.ProductList_update(product);

		return new ModelAndView("redirect:/product");

	}

	// insert product
	@GetMapping("/toinsertproduct")
	public ModelAndView toinsert(@ModelAttribute("ProductForm") ProductForm productForm, Model model) {

		return new ModelAndView("insertproduct");

	}

	@PostMapping("/insertproduct")
	public ModelAndView insert(@ModelAttribute("ProductForm") @Valid ProductForm productForm, BindingResult result,
			ModelAndView model, Map<String, Object> map,@RequestParam("product_imagefile") MultipartFile multipartFile) throws IOException {

		// Nullチェック
		if (result.hasErrors()) {
			List<ObjectError> errorList = result.getAllErrors();
			// エラーメッセージを画面返却情報に入れる
			model.addObject("errorList", errorList);
			// 画面遷移の指定
			model.setViewName("insertproduct");
			return model;
		}

		// 主キー重複チェック
		if (productService.searchProductInfoByID(productForm.getProduct_id().toString()) != null) {
			// エラーメッセージを画面返却情報に入れる
			String errorName = "IDエラー";
			String errorMessage = messageSource.getMessage("product.error.product_id.duplication", new String[] { "" },
					Locale.JAPANESE);

			ObjectError objectError = new ObjectError(errorName, errorMessage);
			List<ObjectError> errorList = new ArrayList<ObjectError>();
			errorList.add(0, objectError);
			model.addObject("errorList", errorList);
			// 画面遷移の指定
			model.setViewName("insertproduct");
			return model;
		}

		Product product = new Product(productForm.getProduct_id(), productForm.getProduct_name(),
				productForm.getProduct_code(), productForm.getCategory_id(), productForm.getManufacture_id(),
				productForm.getProduct_price(), productForm.getProduct_quantity(), productForm.getProduct_description(),
				productForm.getPublication_status());

		
		  String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		  product.setProduct_imagefile("img/"+productForm.getProduct_id()+"/"+fileName);
		  
		  Product savedUser = prod.save(product);
		  
		  String uploadDir = "src/main/resources/static/img/" + savedUser.getProduct_id();
		  
		  ProductService.saveFile(uploadDir, fileName, multipartFile);
		 
		 
		productService.insertProduct(product);

		return new ModelAndView("redirect:/product");

	}

	/*
	 * @PostMapping("insertproduct") public RedirectView insertproduct(Product
	 * product,
	 * 
	 * @RequestParam("image") MultipartFile multipartFile) throws IOException {
	 * 
	 * String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	 * product.setProduct_imagefile(fileName);
	 * 
	 * Product savedUser = prod.save(product);
	 * 
	 * String uploadDir = "./user-photos/" + savedUser.getProduct_id();
	 * 
	 * ProductService.saveFile(uploadDir, fileName, multipartFile);
	 * 
	 * return new RedirectView("/empsystem", true); }
	 */
}
