package com.rice.backend;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
// 允许来自 React端口(5173) 的访问，这步至关重要！
@CrossOrigin(origins = "http://localhost:5173") 
public class RiceController {

    @PostMapping("/calculate")
    public Map<String, Object> calculate(@RequestBody TaskRequest task) {
        System.out.println("收到前端请求: " + task.name); // 打印日志方便调试

        // 1. Java 执行核心计算逻辑
        double c = task.confidence / 100.0;
        double score = (task.reach * task.impact * c * task.strategy) / task.effort;

        // 2. 封装返回数据
        Map<String, Object> response = new HashMap<>();
        response.put("finalScore", score);
        response.put("message", "Calculated by Java Spring Boot!");

        return response;
    }
}