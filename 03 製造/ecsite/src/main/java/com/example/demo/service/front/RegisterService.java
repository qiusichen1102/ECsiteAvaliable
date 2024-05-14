package com.example.demo.service.front;

import com.example.demo.form.front.RegisterForm;

import java.util.List;

public interface RegisterService {
    public void register(RegisterForm registerForm);

    public List<String> AllAdmin();
}
