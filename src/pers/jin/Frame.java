package pers.jin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * @author DEUSJIN
 * @date 2020/5/23
 */
public class Frame extends JFrame  implements ActionListener,KeyListener{
    Solution s;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton searchButton;
    private JTextField inputField;
    private JTextArea outputArea;
    private JPanel upperPanel;
    private JPanel centerPanel;
    private JButton switchButton;
    private boolean searchWord = true;
    public Frame() throws  IOException{
        super("英语词典");
        s = new Solution();
        upperPanel = new JPanel();
        centerPanel = new JPanel();
        inputLabel = new JLabel("输入框：");
        outputLabel = new JLabel("英译汉      ");
        outputArea = new JTextArea(20,30);
        outputArea.setFont( new Font("宋体",Font.BOLD,25));
        searchButton = new JButton("查询");
        switchButton = new JButton("切换");
        inputField = new JTextField(9);
        upperPanel.add(switchButton);
        upperPanel.add(outputLabel);
        upperPanel.add(inputLabel);
        upperPanel.add(inputField);
        upperPanel.add(searchButton);
        centerPanel.add(outputArea);
        outputLabel.setForeground(Color.BLUE);
        upperPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setBackground(Color.orange);
        this.add(upperPanel,BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);
        //事件源绑定监视器
        searchButton.addActionListener(this);
        inputField.addKeyListener(this);
        switchButton.addActionListener(this);

        this.setLocation(500,300);
        this.setResizable(false);
        setSize(600,350);
        setVisible(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String[] strs = new String[2];
        strs[0] = "英译汉      ";
        strs[1] = "汉译英      ";
        if(e.getSource() == searchButton){
            if(searchWord) {
                String word = inputField.getText();
                String chinese = s.search(word);
                outputArea.setText(chinese);
            }else {
                String chinese = inputField.getText();
                outputArea.setText(s.map.get(chinese));
            }
        }else if(e.getSource() == switchButton){
            searchWord = !searchWord;
            if(searchWord){
                outputLabel.setText(strs[0]);
            }else{
                outputLabel.setText(strs[1]);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_ENTER){
            searchButton.doClick();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyChar()==KeyEvent.VK_ENTER){
            searchButton.doClick();
        }else{
            if (searchWord) {
                String word = inputField.getText();
                word = word.toLowerCase();
                List<String> list = s.preSearch(word);
                if (list == null) {
                    return;
                }
                outputArea.setText("");
                int num = 0;
                for (String str : list) {
                    num++;
                    if (num == 10) {
                        break;
                    }
                    outputArea.append(str);
                    outputArea.append("   ");
                    outputArea.append(s.search(str));
                    outputArea.append("\r\n");
                }
            }
        }
    }
}