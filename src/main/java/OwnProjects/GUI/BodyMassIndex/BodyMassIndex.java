package OwnProjects.GUI.BodyMassIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class BodyMassIndex implements ActionListener {
    static JFrame frame;
    static JPanel panel;

    static JLabel height;
    static JTextField heightText;
    static JLabel unitFirst;

    static JLabel weight;
    static JTextField weightText;
    static JLabel unitSecond;

    static JButton btn;
    static JLabel resultLabel;

    BodyMassIndex() {
        panel = new JPanel();
        frame = new JFrame("BMI Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(370, 200);
        frame.add(panel);
        panel.setLayout(null);
//------------------------FIRST ROW-------------------------------------------------
        height = new JLabel("Your height:");
        height.setBounds(20, 20, 100, 25);
        panel.add(height);

        heightText = new JTextField();
        heightText.setHorizontalAlignment(SwingConstants.RIGHT);
        heightText.setBounds(100, 20, 100, 25);
        panel.add(heightText);

        unitFirst = new JLabel(" cm");
        unitFirst.setBounds(200,20,50,25);
        panel.add(unitFirst);
//-----------------------SECOND ROW---------------------------------------------------
        weight = new JLabel("Your weight:");
        weight.setBounds(20, 55, 100, 25);
        panel.add(weight);

        weightText = new JTextField();
        weightText.setHorizontalAlignment(SwingConstants.RIGHT);
        weightText.setBounds(100, 55, 100, 25);
        panel.add(weightText);

        unitSecond = new JLabel(" kg    ");
        unitSecond.setBounds(200,55,50,25);
        panel.add(unitSecond);
//------------------------THIRD ROW--------------------------------------------------
        btn = new JButton("Calculate");
        btn.setFocusable(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setBounds(100, 85, 80, 25);
        btn.addActionListener(this);
        panel.add(btn);
//-------------------------FOURTH ROW------------------------------------------------
        resultLabel = new JLabel();
        resultLabel.setBounds(20, 115, 350, 25);
        panel.add(resultLabel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BodyMassIndex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DecimalFormat df = new DecimalFormat("0.###");
        String heightStr = heightText.getText();
        String weightStr = weightText.getText();
        if (heightStr.isEmpty() && weightStr.isEmpty() ||
                !heightStr.isEmpty() && weightStr.isEmpty() ||
                heightStr.isEmpty() && !weightStr.isEmpty()) {
            resultLabel.setText("Please complete all TextFields!");
        } else {
            double heightDouble = (Double.parseDouble(heightStr)) / 100;
            double weightDouble = Double.parseDouble(weightStr);

            double resultDouble = (weightDouble / (heightDouble * heightDouble));
            String resultStr = df.format(resultDouble);
            if (resultDouble < 16) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Starved");
            } else if (resultDouble >= 16 && resultDouble <= 17) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Emaciation");
            } else if (resultDouble >= 17 && resultDouble <= 18.5) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Underweight");
            } else if (resultDouble >= 18.5 && resultDouble < 25) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Correct Weight");
            } else if (resultDouble >= 25 && resultDouble < 30) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Overweight");
            } else if (resultDouble >= 30 && resultDouble < 35) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  First degree obesity");
            } else if (resultDouble >= 35 && resultDouble < 40) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Second degree obesity");
            } else if (resultDouble >= 40) {
                resultLabel.setText("Your BMI is:  " + resultStr + "  and means  -  Third degree obesity");
            }
        }
    }
}
