import parsers.Parser;
import parsers.Tokenizer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame {
    Form(){
        super("LexAndSyntaxAnalyzer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //
        //СОЗДАНИЕ ОБЪЕКТОВ
        JPanel mainPanel=new JPanel();
        Starter starter=new Starter();
        JPanel ioPanel=new JPanel();
        ioPanel.setLayout(new FlowLayout());
        JPanel infoPanel=new JPanel(new GridLayout(4,1));

        JTextArea inputArea=new JTextArea();
        inputArea.setPreferredSize(new Dimension(300,300));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JTextArea otputArea=new JTextArea();
        otputArea.setPreferredSize(new Dimension(200,300));
        otputArea.setLineWrap(true);
        otputArea.setWrapStyleWord(true);
        JTextArea LexArea=new JTextArea();
        LexArea.setPreferredSize(new Dimension(120,300));
        LexArea.setLineWrap(true);
        LexArea.setWrapStyleWord(true);
        JTextField varField=new JTextField();
        varField.setPreferredSize(new Dimension(750,20));
        JTextField valueField=new JTextField();
        valueField.setPreferredSize(new Dimension(750,20));
        JButton startButton=new JButton("Start");
        JTextArea wordArea=new JTextArea();
        wordArea.setPreferredSize(new Dimension(750,80));
        wordArea.setWrapStyleWord(true);
        wordArea.setLineWrap(true);
        startButton.setPreferredSize(new Dimension(120,100));
        startButton.setBorder(new RoundedBorder(10));//КРАСОТА
        inputArea.setBorder((BorderFactory.createCompoundBorder(new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))));
        otputArea.setBorder((BorderFactory.createCompoundBorder(new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))));;
        LexArea.setBorder((BorderFactory.createCompoundBorder(new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))));;
        wordArea.setBorder((BorderFactory.createCompoundBorder(new RoundedBorder(10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))));;
        //ъ
        //ПРИСВОЕНИЕ ОБЪЕКТОВ
        //

        ioPanel.add(inputArea);
        ioPanel.add(startButton);
        ioPanel.add(otputArea);
        ioPanel.add(LexArea);
        mainPanel.add(ioPanel);
        infoPanel.add(new Label("Переменные"));
        infoPanel.add(varField);
        infoPanel.add(new Label("Значения"));
        infoPanel.add(valueField);
       /* infoPanel.add(new Label("Слова"));
        infoPanel.add(wordArea);
       */ mainPanel.add(infoPanel);
        setContentPane(mainPanel);
        setSize(800,500);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starter.start(inputArea.getText());
                LexArea.setText(starter.getLexString());
                valueField.setText(starter.getVarString());
                varField.setText(starter.getIdString());
                otputArea.setText(starter.getOutputString());
            }
        });
    }
    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

}