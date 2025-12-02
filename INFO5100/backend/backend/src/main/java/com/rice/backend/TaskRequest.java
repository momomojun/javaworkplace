package com.rice.backend;

public class TaskRequest {
    // 这些字段必须和 React 前端发送的 JSON key 一模一样
    public String name;
    public double reach;
    public double impact;
    public double confidence;
    public double strategy;
    public double effort;
}