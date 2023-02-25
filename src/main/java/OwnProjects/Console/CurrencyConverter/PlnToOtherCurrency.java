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

public class PlnToOtherCurrency {
    static String url = "https://www.google.com/finance/";
    static WebDriver driver;
    static Date date;
    static Scanner scanner;
    static double otherToPlnDouble;
    static double plnToOtherDouble;
    static String currency;
    static double quantity;
    static final char c = '"';
    static DecimalFormat df = new DecimalFormat("0.########");

    public static void main(String[] args) {
        date = new Date();
        scanner = new Scanner(System.in);
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
        System.out.print("Kurs jakiej waluty chcesz poznać ? \n"
                + "Kurs Euro, wpisz\t" + c + "EUR" + c + "\n"
                + "Kurs Funta, wpisz\t" + c + "GBP" + c + "\n"
                + "Kurs Dolara, wpisz\t" + c + "USD" + c + "\n");
        System.out.println("Albo może chcesz sprawdzić kurs kryptowaluty ? \n"
                + "Kurs Bitcoina, wpisz\t" + c + "BTC" + c + "\n"
                + "Kurs Ethereum, wpisz\t" + c + "ETH" + c + "\n"
                + "itd\t" + "\n");
        System.out.println();
        currency = scanner.nextLine().toUpperCase().replace(" ", "");
        plnToOtherCurrencyOrKrypto(date);
    }

    public static void plnToOtherCurrencyOrKrypto(Date date) {
        // Akceptuje pliki Coockies
        // Wpisuje w pole tekstowe PLN/USD i wyszukuje wybrane wartości
        WebElement coockiesAccept = driver.findElement(By.xpath("/html/body/c-wiz/div/div/div/div[2]/div[1]/div[3]/div[1]/div[1]/form[2]/div/div/button/span"));
        coockiesAccept.click();
        WebElement input = driver.findElement(By.xpath("/html/body/c-wiz/div/div[3]/div[3]/div/div/div/div[1]/input[2]"));
        input.sendKeys("PLN/" + currency);
        input.sendKeys(Keys.ENTER);
        //-------------------------------------------------------------------------------------------------------------
        // Pobiera WebElement ze strony,
        // Zamienia WebElement na String-a ze zmianą przecinka na kropkę a później na double-a
        // Wraca do poprzedniej strony
        WebElement plnUsd = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        plnToOtherDouble = Double.parseDouble(plnUsd.getText().replace(',', '.').replace(" ", ""));
        driver.navigate().back();
//----------------------------------------------------------------------------------------------------------------------
        WebElement inputSec = driver.findElement(By.xpath("/html/body/c-wiz/div/div[3]/div[3]/div/div/div/div[1]/input[2]"));
        for (int i = 0; i < 7; i++) {
            inputSec.sendKeys(Keys.BACK_SPACE);
        }
        inputSec.sendKeys(currency + "/PLN");
        inputSec.sendKeys(Keys.ENTER);
//----------------------------------------------------------------------------------------------------------------------
        // Pobiera WebElement ze strony,
        // Zamienia WebElement na String-a ze zmianą przecinka na kropkę a później na double-a
        // Wyświetla na konsoli zadane informacje
        WebElement usdPln = driver.findElement(By.xpath("/html/body/c-wiz[2]/div/div[4]/div/main/div[2]/div[1]/div[1]/c-wiz/div/div[1]/div/div[1]/div/div[1]/div/span/div/div"));
        otherToPlnDouble = Double.parseDouble(usdPln.getText().replace(',', '.').replace(" ", ""));
        System.out.println();
        System.out.println("Aktualny kurs Waluty:\t");
        System.out.println("Dzisiejsza data: \t" + date);
        System.out.println("1 PLN = " + df.format(plnToOtherDouble).replace(',', '.') + " " + currency);
        System.out.println("1 " + currency + " = " + otherToPlnDouble + " PLN");
        driver.quit();
        System.out.println();
        System.out.println("Wpisz jaką kwotę przeliczyć:");
        quantity = scanner.nextDouble();
        System.out.println(quantity + " PLN/" + currency + ": " + df.format(quantity * plnToOtherDouble).replace(',', '.'));
        System.out.println(quantity + " " + currency + "/PLN: " + df.format(quantity * otherToPlnDouble).replace(',', '.'));
    }
}
