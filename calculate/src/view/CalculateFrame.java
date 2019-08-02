package view;

import service.CalculateService;
import util.BaseFrame;
import util.MySpring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateFrame extends BaseFrame {
    public CalculateFrame(){}
    public CalculateFrame(String title){
        super(title);
        this.init();
    }
    private JPanel mainPanel = new JPanel();
    private JTextArea displayerArea = new JTextArea();
    private JScrollPane displayerScroll = new JScrollPane(displayerArea);
    private JButton[] numButton = new JButton[10];
    private JButton addButton = new JButton("+");
    private JButton subtractButton = new JButton("-");
    private JButton multiplyButton = new JButton("X");
    private JButton divideButton = new JButton("/");
    private JButton beginButton = new JButton("(");
    private JButton endButton = new JButton(")");
    private JButton delButton = new JButton("DEL");
    private JButton acButton = new JButton("AC");
    private JButton pointButton = new JButton(".");
    private JButton equalButton = new JButton("=");

    private StringBuilder expression = new StringBuilder();

    protected void setFontAndSoOn() {
        //清空布局管理方式
        mainPanel.setLayout(null);
        //设置显示区域 位置 大小 字体 滚动条 不可修改 边框
        displayerScroll.setBounds(20,20,500,76);
        displayerArea.setFont(new Font("黑体", Font.BOLD,50));
        displayerArea.setEnabled(false);
        displayerArea.setDisabledTextColor(Color.BLACK);
        //实例化数字按键
        //设置数字按键 字体 位置 大小
        for(int i = 0; i<10; i++){
            numButton[i] = new JButton(i+"");
            numButton[i].setFont(new Font("黑体",Font.BOLD,50));
            numButton[i].setFocusPainted(false);
        }
        addButton.setBounds(20,126,80,100);
        addButton.setFont(new Font("黑体",Font.BOLD,50));
        addButton.setFocusPainted(false);
        numButton[1].setBounds(125,126,80,100);
        numButton[2].setBounds(230,126,80,100);
        numButton[3].setBounds(335,126,80,100);
        delButton.setBounds(440,126,80,100);
        delButton.setFont(new Font("黑体",Font.BOLD,27));
        delButton.setFocusPainted(false);

        subtractButton.setBounds(20,256,80,100);
        subtractButton.setFont(new Font("黑体",Font.BOLD,50));
        subtractButton.setFocusPainted(false);
        numButton[4].setBounds(125,256,80,100);
        numButton[5].setBounds(230,256,80,100);
        numButton[6].setBounds(335,256,80,100);
        acButton.setBounds(440,256,80,100);
        acButton.setFont(new Font("黑体",Font.BOLD,40));
        acButton.setFocusPainted(false);

        multiplyButton.setBounds(20,386,80,100);
        multiplyButton.setFont(new Font("黑体",Font.BOLD,50));
        multiplyButton.setFocusPainted(false);
        numButton[7].setBounds(125,386,80,100);
        numButton[8].setBounds(230,386,80,100);
        numButton[9].setBounds(335,386,80,100);
        pointButton.setBounds(440,386,80,100);
        pointButton.setFont(new Font("宋体",Font.BOLD,60));
        pointButton.setFocusPainted(false);

        divideButton.setBounds(20,516,80,100);
        divideButton.setFont(new Font("黑体",Font.BOLD,50));
        divideButton.setFocusPainted(false);
        beginButton.setBounds(125,516,80,100);
        beginButton.setFont(new Font("黑体",Font.BOLD,50));
        beginButton.setFocusPainted(false);
        numButton[0].setBounds(230,516,80,100);
        endButton.setBounds(335,516,80,100);
        endButton.setFont(new Font("黑体",Font.BOLD,50));
        endButton.setFocusPainted(false);
        equalButton.setBounds(440,516,80,100);
        equalButton.setFont(new Font("黑体",Font.BOLD,50));
        equalButton.setFocusPainted(false);
        equalButton.setBackground(Color.BLACK);
        equalButton.setForeground(Color.LIGHT_GRAY);
    }

    protected void addElement() {
        mainPanel.add(displayerScroll);
        for(int i = 0; i<10; i++){
            mainPanel.add(numButton[i]);
        }
        mainPanel.add(addButton);
        mainPanel.add(delButton);
        mainPanel.add(subtractButton);
        mainPanel.add(acButton);
        mainPanel.add(multiplyButton);
        mainPanel.add(pointButton);
        mainPanel.add(divideButton);
        mainPanel.add(beginButton);
        mainPanel.add(endButton);
        mainPanel.add(equalButton);
        this.add(mainPanel);
    }

    private void setButton(boolean flag){
        for(int i = 0; i<10; i++){
            numButton[i].setEnabled(flag);
        }
        addButton.setEnabled(flag);
        subtractButton.setEnabled(flag);
        multiplyButton.setEnabled(flag);
        divideButton.setEnabled(flag);
        beginButton.setEnabled(flag);
        endButton.setEnabled(flag);
        delButton.setEnabled(flag);
        acButton.setEnabled(flag);
        pointButton.setEnabled(flag);
        equalButton.setEnabled(flag);
    }
    protected void addListener() {
        equalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CalculateFrame.this.setButton(false);
                displayerArea.setText("计算中 . . .");
                try{
                    CalculateService service = MySpring.getBean("service.CalculateService");
                    String str = service.getResult(expression.toString());
                    displayerArea.setText(str);
                    expression = new StringBuilder(str);
                    CalculateFrame.this.setButton(true);
                }catch (Exception exception){
                    String msg = exception.getMessage();
                    displayerArea.setText("error!");
                    JOptionPane.showMessageDialog(CalculateFrame.this,msg);
                    expression = new StringBuilder();
                    displayerArea.setText("");
                    CalculateFrame.this.setButton(true);
                }
            }
        });

        acButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                expression = expression.delete(0,expression.length());
                displayerArea.setText(expression.toString());
            }
        });

        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(expression.length()!=0) {
                    expression = expression.deleteCharAt(expression.length() - 1);
                    displayerArea.setText(expression.toString());
                }
            }
        });

        ActionListener expressionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton)e.getSource();
                String str = button.getText();
                expression.append(str);
                displayerArea.setText(expression.toString());
            }
        };

        for (int i = 0; i<10; i++){
            numButton[i].addActionListener(expressionListener);
        }
        addButton.addActionListener(expressionListener);
        subtractButton.addActionListener(expressionListener);
        multiplyButton.addActionListener(expressionListener);
        divideButton.addActionListener(expressionListener);
        beginButton.addActionListener(expressionListener);
        endButton.addActionListener(expressionListener);
        pointButton.addActionListener(expressionListener);

    }

    protected void setFrameSelf() {
        //设置窗体大小位置
        this.setBounds(800,200,540,680);
        //设置窗体大小不可被修改
        this.setResizable(false);
        //设置点击关闭按钮退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体可见
        this.setVisible(true);
    }

}
