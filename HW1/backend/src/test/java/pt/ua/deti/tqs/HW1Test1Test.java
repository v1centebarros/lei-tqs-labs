package pt.ua.deti.tqs;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SeleniumJupiter.class)
class HW1Test1Test {

    @BeforeEach
    void setUp() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Test
    void hW1Test1(FirefoxDriver driver) {
        driver.get("http://localhost:4173/");
        driver.manage().window().setSize(new Dimension(979, 1005));
        driver.findElement(By.cssSelector(".input")).click();
        driver.findElement(By.cssSelector(".input")).sendKeys("Porto");
        driver.findElement(By.cssSelector(".false")).click();
        driver.findElement(By.cssSelector(".content-center")).click();
        assertThat(driver.findElement(By.cssSelector(".text-4xl")).getText(), is("Porto, Portugal"));
        driver.findElement(By.cssSelector(".select")).click();
        {
            WebElement dropdown = driver.findElement(By.cssSelector(".select"));
            dropdown.findElement(By.xpath("//option[. = 'Forecast']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input")).click();
        driver.findElement(By.cssSelector(".false")).click();
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".btn-block"));
            assert (elements.size() > 0);
        }
        driver.findElement(By.cssSelector(".btn-block")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".stats:nth-child(22)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold().perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".stats:nth-child(22)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.cssSelector(".stats:nth-child(22)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().perform();
        }
        driver.findElement(By.cssSelector(".stats:nth-child(22)")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".stats:nth-child(22) > .stat:nth-child(7) > .stat-value"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.cssSelector(".stats:nth-child(22) > .stat:nth-child(7) > .stat-value")).click();
        driver.findElement(By.cssSelector(".select")).click();
        {
            WebElement dropdown = driver.findElement(By.cssSelector(".select"));
            dropdown.findElement(By.xpath("//option[. = 'Statistics']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(5)")).click();
        driver.findElement(By.cssSelector(".false")).click();
    }
}
