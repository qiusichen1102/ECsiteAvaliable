package com.example.demo.bean.front;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cart {
    private int id;
    private int stock_id;
    private int user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int deleted_flg;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getDeleted_flg() {
        return deleted_flg;
    }

    public void setDeleted_flg(int deleted_flg) {
        this.deleted_flg = deleted_flg;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
