package com.rice.backend;
import org.springframework.data.jpa.repository.JpaRepository;
// 继承 JpaRepository<User, Long>：
// 1. 泛型 <User, Long> 表示管理 User 表，主键是 Long 类型
// 2. 自动拥有了 save(), findAll(), findById(), delete() 等基本方法
public interface UserRepository extends JpaRepository<User, Long> {
    // 自定义查询方法。Spring 会自动解析方法名：
    // findByUsername -> 生成 SQL: SELECT * FROM users WHERE username = ?
    User findByUsername(String username);
}