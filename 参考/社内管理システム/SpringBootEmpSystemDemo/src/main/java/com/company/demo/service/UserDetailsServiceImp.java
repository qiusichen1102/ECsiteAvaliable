package com.company.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.demo.bean.LoginUser;
import com.company.demo.mapper.LoginMapper;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	LoginMapper loginMapper;
	
	
	
	@Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {

         LoginUser user = loginMapper.getInfoByAccount_id(accountId);

        if (user == null) {
            throw new UsernameNotFoundException("User" + accountId + "was not found in the database");
        }
      //権限のリスト
        //AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
        //権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
        String roleId= "ROLE_"+(user.getRole_id());
        
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleId);
        grantList.add(authority);

        //rawDataのパスワードは渡すことができないので、暗号化
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //UserDetailsはインタフェースなのでUserクラスのコンストラクタで生成したユーザオブジェクトをキャスト
        UserDetails userDetails = (UserDetails)new User(user.getAccount_id(), encoder.encode(user.getPassword()),grantList);

        return userDetails;
}
	public static String intToString(int value){
	    Integer integer = new Integer(value);
	    return integer.toString();
	  }
}

