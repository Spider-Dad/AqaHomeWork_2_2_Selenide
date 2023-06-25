package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();

    }

    public String generateTestDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format((DateTimeFormatter.ofPattern(pattern)));
    }

    @Test
    void shouldSendFormTest() {
        String planningDate = generateTestDate(4, "dd.MM.yyyy");

        $("[data-test-id=city] input").sendKeys("Санкт-Петербург");
        $("[data-test-id=city] input").shouldBe(Condition.value("Санкт-Петербург"));

        $(By.cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=date] input")).shouldBe(Condition.value(planningDate));

        $("[data-test-id=name] input").sendKeys("Николай Римский-Корсаков");
        $("[data-test-id=name] input").shouldBe(Condition.value("Николай Римский-Корсаков"));

        $("[data-test-id=phone] input").sendKeys("+79991234567");
        $("[data-test-id=phone] input").shouldBe(Condition.value("+79991234567"));

        $("[data-test-id=agreement]").click();
        $("[data-test-id=agreement] input").shouldBe(Condition.selected);

        $("div.grid-col button.button_view_extra").click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
