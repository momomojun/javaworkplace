package com.calcaulate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 类名保持 App
public class App extends JFrame {

    // --- 数据存储 ---
    private final List<Task> tasks = new ArrayList<>();
    private final RiceService riceService = new RiceService();

    // --- UI 组件 ---
    private JTextField nameField, reachField, effortField;
    private JComboBox<Double> impactCombo;
    private JSlider confidenceSlider;
    private JLabel confidenceValueLabel;
    
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public App() {
        // 窗口标题：RICE 模型
        setTitle("RICE Prioritization System (RICE 优先级排序系统)");
        setSize(1100, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中
        setLayout(new BorderLayout(10, 10));

        // 1. 初始化面板
        initInputPanel();
        initTablePanel();
        
        // 2. 加载截图中的案例数据
        loadSampleData();
        refreshTable(); 
    }

    /**
     * 顶部输入区域 (根据 RICE 模型定制)
     */
    private void initInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("New Task / 新建任务"));

        // --- 第一行：标签 (Labels) ---
        inputPanel.add(new JLabel("Task Name (任务名称)"));
        inputPanel.add(new JLabel("Reach (影响人数)"));
        inputPanel.add(new JLabel("Impact (影响力 0.25-3)"));
        inputPanel.add(new JLabel("Confidence (信心程度 %)"));
        inputPanel.add(new JLabel("Effort (工作量/人天)"));

        // --- 第二行：输入控件 (Inputs) ---
        
        // 1. Name
        nameField = new JTextField("New Feature...");
        
        // 2. Reach (人数)
        reachField = new JTextField("1000");
        
        // 3. Impact (下拉框选择，参考 Intercom 标准 RICE 权重)
        // 3 = Massive, 2 = High, 1 = Medium, 0.5 = Low, 0.25 = Minimal
        Double[] impactOptions = {3.0, 2.0, 1.0, 0.5, 0.25};
        impactCombo = new JComboBox<>(impactOptions);
        impactCombo.setSelectedItem(1.0); // 默认 Medium
        
        // 4. Confidence (滑动条 0-100%)
        JPanel confidencePanel = new JPanel(new BorderLayout());
        confidenceSlider = new JSlider(0, 100, 80); // 默认 80%
        confidenceValueLabel = new JLabel("80%");
        confidenceSlider.addChangeListener(e -> confidenceValueLabel.setText(confidenceSlider.getValue() + "%"));
        confidencePanel.add(confidenceSlider, BorderLayout.CENTER);
        confidencePanel.add(confidenceValueLabel, BorderLayout.EAST);

        // 5. Effort (人天)
        effortField = new JTextField("10"); // 默认 10天

        // 添加到面板
        inputPanel.add(nameField);
        inputPanel.add(reachField);
        inputPanel.add(impactCombo);
        inputPanel.add(confidencePanel);
        inputPanel.add(effortField);

        // --- 按钮区域 ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton addButton = new JButton("Add Task (添加)");
        JButton calcButton = new JButton("Calculate RICE Score (计算排序)");
        
        // 按钮样式
        calcButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        calcButton.setForeground(Color.WHITE);
        calcButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        addButton.addActionListener(this::addTaskAction);
        calcButton.addActionListener(e -> refreshTable());

        buttonPanel.add(addButton);
        buttonPanel.add(calcButton);

        // 组合顶部容器
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(inputPanel, BorderLayout.CENTER);
        topContainer.add(buttonPanel, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);
    }

    /**
     * 中间表格区域
     */
    private void initTablePanel() {
        // 表头：完全对应 RICE 变量
        String[] columnNames = {
            "Rank (排名)", 
            "Task Name (任务名称)", 
            "RICE Score (得分)", 
            "Reach (人数)", 
            "Impact (结果)", 
            "Confidence (信心)", 
            "Effort (工作量/天)"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        
        // 表格样式优化
        resultTable.setRowHeight(30);
        resultTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Prioritized List (优先级列表)"));
        
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 动作：添加任务
     */
    private void addTaskAction(ActionEvent e) {
        try {
            String name = nameField.getText();
            double reach = Double.parseDouble(reachField.getText());
            double impact = (Double) impactCombo.getSelectedItem();
            double confidence = confidenceSlider.getValue() / 100.0; // 转换 80 -> 0.8
            double effort = Double.parseDouble(effortField.getText());

            if (effort <= 0) {
                JOptionPane.showMessageDialog(this, "Effort must be > 0 (工作量必须大于0)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 构建 Task 对象
            Task newTask = new Task(name, reach, impact, confidence, effort);

            tasks.add(newTask);
            
            JOptionPane.showMessageDialog(this, 
                "Added! Click Calculate to sort.\n添加成功！请点击计算进行排序。", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid Number! Check inputs.\n数字格式错误！请检查输入。", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 动作：刷新表格 (核心 RICE 计算)
     */
    private void refreshTable() {
        // 1. 计算并排序
        List<TaskWithScore> results = riceService.calculateAndSort(tasks);

        // 2. 清空表格
        tableModel.setRowCount(0);

        // 3. 填充数据
        int rank = 1;
        for (TaskWithScore item : results) {
            Task t = item.task();
            
            Object[] rowData = {
                rank++,
                t.name(),
                String.format("%.1f", item.riceScore()), // RICE 分数保留1位小数
                String.format("%.0f", t.reach()),        // 人数不带小数
                t.impact(),                              // 影响力 (3.0, 2.0...)
                String.format("%.0f%%", t.confidence() * 100), // 显示百分比
                String.format("%.1f Days", t.effort())   // 天数
            };
            tableModel.addRow(rowData);
        }
    }
    
    /**
     * 加载截图中的案例数据
     */
    private void loadSampleData() {
        // 案例 A: 提升搜索速度 (Reach=1000, Impact=3, Conf=90%, Effort=10)
        tasks.add(new Task("A. Improve Search Speed (提升搜索速度)", 1000, 3.0, 0.9, 10));
        
        // 案例 B: 新增推荐功能 (Reach=500, Impact=4?? 截图写4, 但通常最大是3, 这里按截图来, Conf=70%, Effort=30)
        // 注意：截图里的 RICE = 46, 算一下: (500*4*0.7)/30 = 46.6. 
        tasks.add(new Task("B. Add Recommendation (新增推荐功能)", 500, 4.0, 0.7, 30));

        // 案例 C: 修复支付 BUG (Reach=5000, Impact=2, Conf=90%, Effort=2)
        // RICE = (5000*2*0.9)/2 = 4500
        tasks.add(new Task("C. Fix Payment Bug (修复支付BUG)", 5000, 2.0, 0.9, 2));
    }

    // --- Main 入口 ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }

    // ==========================================
    //        RICE 数据模型和计算引擎
    // ==========================================

    /**
     * RICE 基础实体
     * @param name 任务名
     * @param reach (Reach) 覆盖人数
     * @param impact (Impact) 影响力 (3=High, etc.)
     * @param confidence (Confidence) 信心 (0.0 - 1.0)
     * @param effort (Effort) 工作量 (人天)
     */
    public record Task(
        String name, 
        double reach, 
        double impact, 
        double confidence, 
        double effort
    ) {}

    /**
     * 带有分数的包装类，用于排序
     */
    public record TaskWithScore(Task task, double riceScore) implements Comparable<TaskWithScore> {
        @Override
        public int compareTo(TaskWithScore other) {
            // 降序排列 (分数高的在前面)
            return Double.compare(other.riceScore, this.riceScore);
        }
    }

    /**
     * RICE 计算服务
     */
    public static class RiceService {
        public List<TaskWithScore> calculateAndSort(List<Task> tasks) {
            List<TaskWithScore> scoredTasks = new ArrayList<>();

            for (Task t : tasks) {
                // RICE 公式 = (Reach * Impact * Confidence) / Effort
                double score = 0;
                if (t.effort() > 0) {
                    score = (t.reach() * t.impact() * t.confidence()) / t.effort();
                }
                scoredTasks.add(new TaskWithScore(t, score));
            }

            // 排序
            Collections.sort(scoredTasks);
            return scoredTasks;
        }
    }
}