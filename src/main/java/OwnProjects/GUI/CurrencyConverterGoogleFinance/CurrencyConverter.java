package OwnProjects.GUI.CurrencyConverterGoogleFinance;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Date;

public class CurrencyConverter implements ActionListener {
    private static WebDriver driver;
    private static Date date;
    private static double twoToOneDouble;
    private static double oneToTwoDouble;
    private static String currOne;
    private static String currTwo;
    private static JTextField currTextOne;
    private static JTextField currTextTwo;
    private static JLabel resultOne;
    private static JLabel resultTwo;
    private static JLabel dateLabel;

    public static void main(String[] args) {
        environment();
        swing();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DecimalFormat df = new DecimalFormat("0.########");
        currOne = currTextOne.getText().toUpperCase();
        currTwo = currTextTwo.getText().toUpperCase();
        if (currOne.equals(currTwo)) {
            resultOne.setText("Please enter two");
            resultTwo.setText("different currencies");

        } else if ((currOne.isEmpty() || currTwo.isEmpty()) ||
                currOne.isEmpty() && currTwo.isEmpty()) {
                resultOne.setText("Please complete");
                resultTwo.setText("all text fields");
        } else {
            oneToTwoCurrencyOrKrypto();
            dateLabel.setText(date.toString());
            resultOne.setText("1 " + currOne + "  =  " + df.format(oneToTwoDouble).replace(',', '.').replace(" ", "") + "  " + currTwo);
            resultTwo.setText("1 " + currTwo + "  =  " + df.format(twoToOneDouble).replace(',', '.').replace(" ", "") + "  " + currOne);
        }
    }
    public static void oneToTwoCurrencyOrKrypto() {
        WebElement input = driver.findElement(By.xpath("/html/body/c-wiz/div/div[3]/div[3]/div/div/div/div[1]/input[2]"));
        input.sendKeys(currOne + "/" + currTwo);
        input.sendKeys(Keys.ENTER);
        WebElement firstCurrency = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        oneToTwoDouble = Double.parseDouble(firstCurrency.getText().replace(',', '.').replace(" ", ""));
        driver.navigate().back();
        input.click();
        input.click();
        input.clear();
        input.sendKeys(currTwo + "/" + currOne);
        input.sendKeys(Keys.ENTER);
        WebElement secondCurrency = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        twoToOneDouble = Double.parseDouble(secondCurrency.getText().replace(',', '.').replace(" ", ""));
        driver.navigate().back();
        input.click();
        input.sendKeys(Keys.BACK_SPACE);
    }
    public static void environment() {
        date = new Date();
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String url = "https://www.google.com/finance/";
        driver.get(url);
        WebElement coockiesAccept = driver.findElement(By.xpath("/html/body/c-wiz/div/div/div/div[2]/div[1]/div[3]/div[1]/div[1]/form[2]/div/div/button/span"));
        coockiesAccept.click();
    }
    public static void swing() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Google Finance");
        frame.setSize(420, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        //-----Title-----
        JLabel titleLabel = new JLabel("Search, among others currencies, stocks and ETFs");
        titleLabel.setBounds(20, 20, 300, 25);
        panel.add(titleLabel);
        //-----First Currency-----
        JLabel currencyOne = new JLabel("First Currency");
        currencyOne.setBounds(10, 50, 80, 25);
        panel.add(currencyOne);
        currTextOne = new JTextField(20);
        currTextOne.setBounds(100, 50, 100, 25);
        panel.add(currTextOne);
        //-----Second Currency-----
        JLabel currencyTwo = new JLabel("Sec Currency");
        currencyTwo.setBounds(10, 80, 80, 25);
        panel.add(currencyTwo);
        currTextTwo = new JTextField(20);
        currTextTwo.setBounds(100, 80, 100, 25);
        panel.add(currTextTwo);
        //-----Button-----
        JButton button = new JButton("Calculate");
        button.setFocusable(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBounds(100, 110, 75, 25);
        button.addActionListener(new CurrencyConverter());
        panel.add(button);
        //-----Date-----
        dateLabel = new JLabel("");
        dateLabel.setBounds(220, 110, 300, 25);
        panel.add(dateLabel);
        //-----Result First to Second Currency-----
        resultOne = new JLabel("");
        resultOne.setBounds(220, 50, 300, 25);
        panel.add(resultOne);
        //-----Result Second to First Currency-----
        resultTwo = new JLabel("");
        resultTwo.setBounds(220, 80, 300, 25);
        panel.add(resultTwo);
        frame.setVisible(true);
    }
}
