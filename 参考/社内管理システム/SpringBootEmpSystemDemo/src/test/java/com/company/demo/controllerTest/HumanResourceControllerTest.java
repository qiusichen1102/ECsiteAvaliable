package com.company.demo.controllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.demo.SpringBootEmpSystemDemoApplicationTests;

import com.company.demo.controller.HumanResourceController;

import com.company.demo.bean.HumanResource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HumanResourceControllerTest extends SpringBootEmpSystemDemoApplicationTests {
	@Resource

	private HumanResourceControllerTest humanResourceControllerTest;

	@Autowired
	private HumanResourceController humanResourceController;

	private MockMvc mockMvc;

	@Before
    public void setUp() throws Exception {
    	mockMvc = MockMvcBuilders.standaloneSetup(new HumanResourceController()).build();
    }

	@After
	public void tearDown() throws Exception {
	}




	public void updateSuccess() throws Exception {

		MockHttpServletRequestBuilder getRequest = MockMvcRequestBuilders.post("/humanResourceUpdate/auth")
				.param("empCd", "111118").param("basicSalary", "").param("salaryTimes", "").param("insuranceLine", "")
				.param("healthInsurance", "").param("retirementPay", "").param("diseaseInturance", "")
				.param("humanResourceComment", "");

		ResultActions results;
		results = mockMvc.perform(getRequest);

		results.andDo(print());
		results.andExpect(redirectedUrl("/humanResourceList"));
		results.andExpect(model().errorCount(0));

//	    	HumanResourceForm humanResourceForm =new HumanResourceForm();
//	    	humanResourceForm.setEmpCd("100002");
//	    	humanResourceForm.setSalaryTimes("14");
//			List<String> error =null;
//			Locale locale = LocaleContextHolder.getLocale();
//			error = humanResourceController.update(humanResourceForm,Locale);
//			Assert.assertEquals(0, error.size());

	}
}
