package com.calcaulate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App extends JFrame {

    // 数据存储
    private final List<Task> taskList = new ArrayList<>();
    
    // UI 组件
    private JTextField tName, tReach, tImpact, tConfidence, tStrategy, tEffort;
    private DefaultTableModel rawModel;    // 左边：原始数据
    private DefaultTableModel sortedModel; // 右边：排序数据

    public App() {
        setTitle("RICE+S Task Manager");
        setSize(1200, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); 

        // --- 1. 顶部：输入区域 + 四个操作按钮 ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Input Panel"));
        
        // 输入框
        tName = addInput(topPanel, "Task:", "New Feature", 100);
        tReach = addInput(topPanel, "Reach:", "1000", 50);
        tImpact = addInput(topPanel, "Impact:", "2.0", 35);
        tConfidence = addInput(topPanel, "Confidence%:", "80", 35);
        tStrategy = addInput(topPanel, "Strategy:", "1.0", 35);
        tEffort = addInput(topPanel, "Effort:", "10", 35);

        // 按钮 1: Add (灰色)
        JButton btnAdd = new JButton(" Add ");
        
        // 按钮 2: Analyze (红色)
        JButton btnSort = new JButton(" Analyze ");
        styleButton(btnSort, new Color(220, 53, 69)); 
        
        // 按钮 3: Chart (橙色)
        JButton btnChart = new JButton(" Chart ");
        styleButton(btnChart, new Color(255, 140, 0)); 

        // 按钮 4: About (蓝色)
        JButton btnInfo = new JButton(" About ");
        styleButton(btnInfo, new Color(23, 162, 184)); 

        btnAdd.addActionListener(e -> addTaskOnly());
        btnSort.addActionListener(e -> sortAndDisplay());
        btnChart.addActionListener(e -> showChartDialog());
        btnInfo.addActionListener(e -> showInfoDialog()); 

        topPanel.add(btnAdd);
        topPanel.add(btnSort);
        topPanel.add(btnChart);
        topPanel.add(btnInfo);
        
        add(topPanel, BorderLayout.NORTH);

        // --- 2. 中间：双表格区域 ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0)); 
        
        // === 左边：任务池 ===
        String[] rawHeaders = {"ID", "Task Name", "R", "I", "C%", "S", "E"};
        rawModel = new DefaultTableModel(rawHeaders, 0);
        JTable rawTable = new JTable(rawModel);
        JScrollPane leftScroll = new JScrollPane(rawTable);
        leftScroll.setBorder(BorderFactory.createTitledBorder("1. Raw Task List (Unsorted)"));
        
        // === 右边：排名结果 ===
        String[] sortedHeaders = {"Rank", "Score", "Task Name", "Strategy Impact"};
        sortedModel = new DefaultTableModel(sortedHeaders, 0);
        JTable sortedTable = new JTable(sortedModel);
        sortedTable.setShowHorizontalLines(true);
        sortedTable.setRowHeight(25);
        
        JScrollPane rightScroll = new JScrollPane(sortedTable);
        rightScroll.setBorder(BorderFactory.createTitledBorder("2. Prioritized Results (Ranked)"));

        centerPanel.add(leftScroll);
        centerPanel.add(rightScroll);

        add(centerPanel, BorderLayout.CENTER);
    }

    // 辅助方法：样式化按钮
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false); 
        btn.setFocusPainted(false);
        btn.setOpaque(true);
    }

    // 辅助方法：添加输入框
    private JTextField addInput(JPanel p, String label, String defaultVal, int width) {
        p.add(new JLabel(label));
        JTextField tf = new JTextField(defaultVal);
        tf.setPreferredSize(new Dimension(width, 25));
        p.add(tf);
        return tf;
    }

    // --- 功能 1: 添加 ---
    private void addTaskOnly() {
        try {
            String name = tName.getText();
            double r = Double.parseDouble(tReach.getText());
            double i = Double.parseDouble(tImpact.getText());
            double c = Double.parseDouble(tConfidence.getText()) / 100.0;
            double s = Double.parseDouble(tStrategy.getText());
            double e = Double.parseDouble(tEffort.getText());

            if (e <= 0) { JOptionPane.showMessageDialog(this, "Effort > 0 please!"); return; }

            Task newTask = new Task(name, r, i, c, s, e);
            taskList.add(newTask);

            rawModel.addRow(new Object[]{
                taskList.size(), newTask.name, newTask.r, newTask.i, (int)(newTask.c*100), newTask.s, newTask.e
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Inputs!");
        }
    }

    // --- 功能 2: 排序 ---
    private void sortAndDisplay() {
        if (taskList.isEmpty()) return;
        updateScoresAndSort();
        
        sortedModel.setRowCount(0);
        int rank = 1;
        List<Task> sortedList = new ArrayList<>(taskList);
        Collections.sort(sortedList, (a, b) -> Double.compare(b.score, a.score));
        
        for (Task t : sortedList) {
            sortedModel.addRow(new Object[]{
                rank++, String.format("%.1f", t.score), t.name, "x" + t.s 
            });
        }
    }
    
    // --- 功能 3: 图表 ---
    private void showChartDialog() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to visualize!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        updateScoresAndSort();
        List<Task> sortedData = new ArrayList<>(taskList);
        Collections.sort(sortedData, (a, b) -> Double.compare(b.score, a.score));
        
        JDialog dialog = new JDialog(this, "RICE Score Visualization", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.add(new ChartPanel(sortedData)); 
        dialog.setVisible(true);
    }

    // --- 功能 4: 关于信息 (已修改：去除图标) ---
    private void showInfoDialog() {
        String message = "<html><body style='width: 350px; font-family: Segoe UI;'>" +
            "<h2 style='color: #3246a0; margin-bottom: 5px;'>RICE + Strategy Model</h2>" +
            "<p style='margin-bottom: 10px;'><b>Formula:</b> (Reach * Impact * Confidence * Strategy) / Effort</p>" +
            "<hr>" +
            "<p><b>(R) Reach:</b> Users affected (e.g., 1000)</p>" +
            "<p><b>(I) Impact:</b> Effect size (3=Massive, 2=High, 1=Medium, 0.5=Low)</p>" +
            "<p><b>(C) Confidence:</b> Certainty percentage (e.g., 80%)</p>" +
            "<p><b>(S) Strategy:</b> Fit Multiplier (2.0=Core, 1.0=Normal, 0.5=Low)</p>" +
            "<p><b>(E) Effort:</b> Time cost (Person-Days)</p>" +
            "</body></html>";
        
        // 修改点：这里改成了 PLAIN_MESSAGE，就不会显示图标了
        JOptionPane.showMessageDialog(this, message, "About RICE Model", JOptionPane.PLAIN_MESSAGE);
    }

    private void updateScoresAndSort() {
        for (Task t : taskList) {
            t.score = (t.r * t.i * t.c * t.s) / t.e;
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }

    // 图表类
    static class ChartPanel extends JPanel {
        private final List<Task> data;
        public ChartPanel(List<Task> data) {
            this.data = data;
            setBackground(Color.WHITE);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int w = getWidth();
            int padding = 50; int barHeight = 35; int gap = 20;
            double maxScore = data.stream().mapToDouble(t -> t.score).max().orElse(1.0);
            
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            g2.drawString("RICE Score Comparison", padding, 30);
            
            int startY = 60;
            int maxBarWidth = w - (padding * 2) - 150; 

            for (int i = 0; i < data.size(); i++) {
                Task t = data.get(i);
                int y = startY + i * (barHeight + gap);
                
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                g2.drawString((i+1) + ". " + t.name, padding, y - 5);
                
                int barWidth = (int) ((t.score / maxScore) * maxBarWidth);
                barWidth = Math.max(barWidth, 5);

                if (i == 0) g2.setColor(new Color(40, 167, 69)); 
                else g2.setColor(new Color(0, 123, 255)); 
                
                g2.fillRoundRect(padding, y, barWidth, barHeight, 10, 10);
                
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                g2.drawString(String.format("%.1f", t.score), padding + barWidth + 10, y + barHeight / 2 + 5);
            }
        }
    }

    static class Task {
        String name;
        double r, i, c, s, e; 
        double score;

        public Task(String name, double r, double i, double c, double s, double e) {
            this.name = name;
            this.r = r; this.i = i; this.c = c; this.s = s; this.e = e;
        }
    }
}