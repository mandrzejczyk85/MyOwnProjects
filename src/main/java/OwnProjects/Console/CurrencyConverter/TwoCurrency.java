package OwnProjects.Console.CurrencyConverter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Scanner;

public class TwoCurrency {
    static String url = "https://www.google.com/finance/";
    static WebDriver driver;
    static Date date;
    static Scanner scanner;
    static double twoToOneDouble;
    static double oneToTwoDouble;
    static String currOne;
    static String currTwo;
    static double quantity;
    static DecimalFormat df = new DecimalFormat("0.########");

    public static void main(String[] args) {
        date = new Date();
        scanner = new Scanner(System.in);
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
        System.out.println("Wprowadź nazwę waluty wg ustandaryzowanego, trzyliterowego kodu\nPrzyjętego przez Międzynarodową Organizację Normalizacyjną (ISO).");
        System.out.println("Np. EUR, USD, GBP, CZK, THB - etc,\nW przypadku kryptowalut liter może być cztery jak DOGE, SHIB - etc");
        System.out.println("Nazwy możesz wprowadzać również małymi literami\n");
        System.out.println("Podaj pierwszą walutę:");
        currOne = scanner.nextLine().toUpperCase().replace(" ","");
        System.out.println("Podaj drugą walutę:");
        currTwo = scanner.nextLine().toUpperCase().replace(" ","");
        plnToOtherCurrencyOrKrypto(date);
    }
    public static void plnToOtherCurrencyOrKrypto(Date date) {
        WebElement coockiesAccept = driver.findElement(By.xpath("/html/body/c-wiz/div/div/div/div[2]/div[1]/div[3]/div[1]/div[1]/form[2]/div/div/button/span"));
        coockiesAccept.click();
        WebElement input = driver.findElement(By.xpath("/html/body/c-wiz/div/div[3]/div[3]/div/div/div/div[1]/input[2]"));
        input.sendKeys(currOne+currTwo);
        input.sendKeys(Keys.ENTER);
        WebElement firstCurrency = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        oneToTwoDouble = Double.parseDouble(firstCurrency.getText().replace(',', '.').replace(" ",""));
        driver.navigate().back();
        WebElement inputSec = driver.findElement(By.xpath("/html/body/c-wiz/div/div[3]/div[3]/div/div/div/div[1]/input[2]"));
        for (int i = 0; i < 7; i++) {
            inputSec.sendKeys(Keys.BACK_SPACE);
        }
        inputSec.sendKeys(currTwo + currOne);
        inputSec.sendKeys(Keys.ENTER);
        WebElement secondCurrency = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        twoToOneDouble = Double.parseDouble(secondCurrency.getText().replace(',', '.').replace(" ",""));
        System.out.println();
        System.out.println("Kurs Waluty na dzień: ");
        System.out.println(date + "\n");
        System.out.println("1 " + currOne + " = " + df.format(oneToTwoDouble).replace(',','.') + " " + currTwo);
        System.out.println("1 " + currTwo + " = " + df.format(twoToOneDouble).replace(',','.') + " " + currOne);
        driver.quit();
        System.out.println();
        System.out.println("Wpisz jaką kwotę przeliczyć:");
        quantity = scanner.nextDouble();
        System.out.println(quantity + " " + currOne + " / " + currTwo + " : " + df.format(quantity * oneToTwoDouble).replace(',', '.'));
        System.out.println(quantity + " " + currTwo + " / " + currOne + " : " + df.format(quantity * twoToOneDouble).replace(',', '.'));
    }
}
