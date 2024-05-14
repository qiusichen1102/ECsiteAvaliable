package com.example.demo.service.front;

import com.example.demo.form.front.RegisterForm;
import com.example.demo.mapper.front.RegisterMapper;
import com.example.demo.service.front.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterMapper registerMapper;
    //新規登録
    @Override
    public void register(RegisterForm registerForm) {
        registerMapper.register(registerForm.getName(),registerForm.getEmail(),registerForm.getPassword(),registerForm.getCreated_at(),registerForm.getUpdated_at(),0,1);
    }
    //重複アカウント検索
    @Override
    public List<String> AllAdmin() {
        return registerMapper.AllAdmin();
        
    }
}
