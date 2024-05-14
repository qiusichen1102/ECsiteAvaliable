package com.company.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.company.demo.service.UserDetailsServiceImp;


@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImp userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

public void configure(WebSecurity web) throws Exception {
    // 让页面静态资源不被拦截
    web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html",
            "/**/*.png");
}
	@Override // 重写定制请求的授权规则
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()
				.antMatchers("/","/menu").permitAll()// 所有用户均可以打开/界面
				.antMatchers("/list").hasAnyRole("0","1") //拥有1,6权限的用户可以进入details界面
				.antMatchers("/add").hasAnyRole("0","1")
				.antMatchers("/toAdd").hasAnyRole("0","1")
				.antMatchers("/search").hasAnyRole("0","1")
				.antMatchers("/delete").hasAnyRole("0","1")
				.antMatchers("/toUpdate").hasAnyRole("0","1")
				.antMatchers("/update").hasAnyRole("0","1")
				.antMatchers("/toDetails").hasAnyRole("0","1")
				.antMatchers("/engineerListList").hasAnyRole("0","2")
				.antMatchers("/engineerList_search").hasAnyRole("0","2")
				.antMatchers("/engineerList_delete").hasAnyRole("0","2")
				.antMatchers("/toEngineerListUpdate").hasAnyRole("0","2")
				.antMatchers("/engineerListUpdate").hasAnyRole("0","2")
				.antMatchers("/toEngineerListDetails").hasAnyRole("0","2")
				.antMatchers("/humanResourceList").hasAnyRole("0","1")
				.antMatchers("/HumanResource_search").hasAnyRole("0","1")
				.antMatchers("/HumanResource_delete").hasAnyRole("0","1")
				.antMatchers("/toHumanResourceUpdate").hasAnyRole("0","1")
				.antMatchers("/HumanResourceUpdate").hasAnyRole("0","1")
				.antMatchers("/toHumanResourceDetails").hasAnyRole("0","1")
				.antMatchers("/retailList").hasAnyRole("0","1")
				.antMatchers("/retailsearch").hasAnyRole("0","1")
				.antMatchers("/retaildelete").hasAnyRole("0","1")
				.antMatchers("/retailUpdate").hasAnyRole("0","1")
				.antMatchers("/toRetailDetails").hasAnyRole("0","1")
				.antMatchers("/workInsuranceList").hasAnyRole("0","1")
				.antMatchers("/WorkInsurance_search").hasAnyRole("0","1")
				.antMatchers("/WorkInsurance_delete").hasAnyRole("0","1")
				.antMatchers("/toWorkInsuranceUpdate").hasAnyRole("0","1")
				.antMatchers("/WorkInsuranceStateupdate").hasAnyRole("0","1")
				.antMatchers("/toWorkInsurancetoDetails").hasAnyRole("0","1")
				.antMatchers("/workingstatuslist").hasAnyRole("0","2")
				.antMatchers("/WorkingStatus_search").hasAnyRole("0","2")
				.antMatchers("/WorkingStatus_delete").hasAnyRole("0","2")
				.antMatchers("/toWorkingStatusUpdate").hasAnyRole("0","2")
				.antMatchers("/WorkingStatusStateupdate").hasAnyRole("0","2")
				.antMatchers("/toWorkingStatusDetails").hasAnyRole("0","2")
				.antMatchers("/loginList").hasRole("0")
				.antMatchers("/toLoginAdd").hasRole("0")
				.antMatchers("/loginAdd").hasRole("0")
				.antMatchers("/loginDelete").hasRole("0")
				.antMatchers("/toLoginUpdate").hasRole("0")
				.antMatchers("/loginUpdate").hasRole("0");
				//拥有6权限的用户可以进入list界面
			
		http.formLogin()//以表单模式，另有弹窗模式可选择
				.usernameParameter("accountId").passwordParameter("password")
				.loginPage("/userlogin") //login页面
				.failureUrl("/login-error"); //login失败重定向页面
		
		http.logout() //注销
		.logoutSuccessUrl("/login"); //注销后跳转路径
		
		/*http.rememberMe().rememberMeParameter("remember"); //
*/	}
	@Override // 重写认证规则
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		/* auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())	
		 //此处passwordEncoder(new BCryptPasswordEncoder())为SpringSecurity5新特性，老版本不需要，请注意
		 					// 1号：无权限
		 		.withUser("111@softuseing.com").password(new BCryptPasswordEncoder().encode("000001")).roles("VIP1") 
		 		.and()		// 2号：VIP1权限
		 		.withUser("222@softuseing.com").password(new BCryptPasswordEncoder().encode("000002")).roles("VIP2") 
		 		.and()		// 3号：VIP1与VIP2权限
		 		.withUser("333@softuseing.com").password(new BCryptPasswordEncoder().encode("000003")).roles("VIP1","VIP2");*/
		 		
		 		
	}

}
/*
.antMatchers(HttpMethod.GET).permitAll()
.antMatchers(HttpMethod.POST).permitAll()*/
