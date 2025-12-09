package com.rice.backend;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public double reach;
    public double impact;
    public double confidence;
    public double strategy;
    public double effort;
    public double score;

    public String owner;

    public Task() {}
    // 方便创建任务对象的构造函数
    public Task(String name, double reach, double impact, double confidence, double strategy, double effort, double score, String owner) {
        this.name = name;
        this.reach = reach;
        this.impact = impact;
        this.confidence = confidence;
        this.strategy = strategy;
        this.effort = effort;
        this.score = score;
        this.owner = owner;
    }
}