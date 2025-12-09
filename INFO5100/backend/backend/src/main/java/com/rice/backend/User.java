package com.rice.backend;
import jakarta.persistence.*;

// USER/TASK 定义了数据在数据库中长什么样
@Entity // 告诉 JPA：这个类对应数据库中的一张表
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // 声明主键自增 (Auto Increment)，即 id 由数据库自动生成 (1, 2, 3...)
    public Long id;

    @Column(unique = true, nullable = false)
    public String username;

    public String password;

    public User() {}
    // 全参构造函数，方便我们在代码里 new User(...)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}