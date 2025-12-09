package com.rice.backend;
// 这个类不对应数据库表，而是对应 前端发过来的 JSON 数据
public class TaskRequest {
    public Long id; 
    public String name;
    public double reach;
    public double impact;
    public double confidence;
    public double strategy;
    public double effort;
    public String username; 
}