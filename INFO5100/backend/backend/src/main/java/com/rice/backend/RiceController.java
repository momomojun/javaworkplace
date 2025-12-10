package com.rice.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

// 1. 声明这是一个控制器 2. 所有方法的返回值都会自动转成 JSON 格式
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RiceController {

    // 告诉 Spring：请帮我把 UserRepository 的实例注入进来，我要用它查数据库
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;

    // --- 1. 用户注册 ---
    @PostMapping("/register")
    // @RequestBody 把前端传的 JSON 转成 User 对象
    public Map<String, Object> register(@RequestBody User user) {
        // 创建一个 Map 用来存返回结果
        Map<String, Object> res = new HashMap<>();
        if (userRepo.findByUsername(user.username) != null) {
            res.put("success", false);
            res.put("message", "Username already exists!");
        } else {
            userRepo.save(user); // 保存到 MySQL
            res.put("success", true);
            res.put("message", "Registration successful!");
        }
        return res;
    }

    // --- 2. 用户登录 ---
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();
        User foundUser = userRepo.findByUsername(user.username);
        
        // 逻辑：用户存在 且 密码匹配 (这里是明文比较，生产环境应加密)
        if (foundUser != null && foundUser.password.equals(user.password)) {
            res.put("success", true);
            res.put("username", foundUser.username); // 返回用户名给前端存着
        } else {
            res.put("success", false);
            res.put("message", "Invalid username or password");
        }
        return res;
    }

    // --- 3. 获取我的任务 ---
    @GetMapping("/tasks")
    public List<Task> getMyTasks(@RequestParam String username) {
        return taskRepo.findByOwner(username); // 只查这个人的
    }

    // --- 4. 计算并保存任务 (修复了重复添加的问题) ---
    @PostMapping("/calculate")
    public Task calculateAndSave(@RequestBody TaskRequest req) {
        // 1. 计算 RICE 分数
        double c = req.confidence / 100.0;
        double score = (req.reach * req.impact * c * req.strategy) / req.effort;

        Task taskToSave;

        // 2. 判断是“更新”还是“新建”
        // 如果前端传了 ID，并且数据库里真的有这个 ID，那就是更新
        if (req.id != null && taskRepo.existsById(req.id)) {
            // 查出旧任务
            taskToSave = taskRepo.findById(req.id).get();
            // 更新它的值
            taskToSave.name = req.name;
            taskToSave.reach = req.reach;
            taskToSave.impact = req.impact;
            taskToSave.confidence = req.confidence;
            taskToSave.strategy = req.strategy;
            taskToSave.effort = req.effort;
            taskToSave.score = score;
            // owner 不变
        } else {
            // 3. 如果没 ID (或者是前端生成的临时ID)，就新建
            taskToSave = new Task(req.name, req.reach, req.impact, req.confidence, req.strategy, req.effort, score, req.username);
        }

        // 4. 保存 (JPA 会自动识别是 update 还是 insert)
        return taskRepo.save(taskToSave);
    }

    // --- 5. 删除任务 ---
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id);
        Map<String, Boolean> res = new HashMap<>();
        res.put("success", true);
        return res;
    }
}