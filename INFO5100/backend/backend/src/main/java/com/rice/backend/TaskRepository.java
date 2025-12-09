package com.rice.backend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// 仓库接口 (Repository) - 负责操作数据库
// 利用了 Spring Data JPA 的“魔法”，你只需要写接口，它自动帮你生成 SQL
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 自定义查询方法。Spring 会自动解析方法名：
    // 自定义查询：根据 owner (所有者) 查找任务列表
    // findByOwner -> 生成 SQL: SELECT * FROM tasks WHERE owner = ?
    List<Task> findByOwner(String owner);
}