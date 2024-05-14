package com.company.demo.controllerTest;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.company.demo.SpringBootEmpSystemDemoApplicationTests;
import com.company.demo.controller.RetirementPayAndHealthInsuranceController;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RetirementPayAndHealthInsuranceControllerTest extends SpringBootEmpSystemDemoApplicationTests {
	@Resource
	@InjectMocks
	private RetirementPayAndHealthInsuranceController retirementPayAndHealthInsuranceController;
	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();

	@Autowired
    private WebApplicationContext wac;

	    private MockMvc mockMvc;

	    @Before
	    public void setup() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	    }

	    @Test
	    public void testSuccess() throws Exception {
	    	 File file = new File("C:\\2019-03-12RetirementPay.xls");
	    	  MockMultipartFile firstFile = new MockMultipartFile("file", "xxxx.xls",
	    			  "multipart/form-data", new FileInputStream(file));


	          MockHttpServletRequestBuilder builder =
	                  MockMvcRequestBuilders.fileUpload("/excelImport")
	                                        .file(firstFile);
	          mockMvc.perform(builder)
	                      .andDo(MockMvcResultHandlers.print());;
	          Assert.assertTrue(file.exists());


	    }
}


