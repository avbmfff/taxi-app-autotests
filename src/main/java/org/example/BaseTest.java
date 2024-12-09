package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;


public class BaseTest {
    public static Playwright playwright;
    public static Browser browser;
    public static Page page;

    @BeforeEach
    public void launch() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(250)
                .setArgs(Arrays.asList(
                        "--start-maximized",
                        "--disable-infobars",
                        "--no-sandbox",
                        "--disable-dev-shm-usage")));

        page = browser.newPage();

        page.navigate("http://127.0.0.1:8000/");
    }


    @AfterEach
    public void close() {
        browser.close();
        playwright.close();
    }
}