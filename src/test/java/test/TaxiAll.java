package test;

import com.microsoft.playwright.Locator;
import org.example.BaseTest;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaxiAll extends BaseTest {

    @Test
    public void checkButtonsMainPage() {
        assertThat(page.getByText("Такси")).isVisible();
        assertTrue(page.getByText("Спецоборудование").isVisible());
        assertTrue(page.getByText("Питание").isVisible());
        assertTrue(page.getByText("Аптека").isVisible());
        assertTrue(page.getByText("Обратная связь").isVisible());
        assertTrue(page.getByText("Выйти").isVisible());

        page.navigate("http://127.0.0.1:8000/");

        page.getByText("Такси").click();
        page.getByPlaceholder("Адрес отправления").fill("Проспект Шахтеров 48А");
        page.getByPlaceholder("Адрес назначения").fill("Проспект Шахтеров 40");
        page.getByPlaceholder("Ожидаемая стоимость").fill("456");
        page.locator(".vehicle-button").nth(0).click();
        page.locator(".vehicle-button").nth(1).click();
        page.locator(".vehicle-button").nth(2).click();
        page.getByText("Собака-поводырь").click();
        page.getByText("Нужна коляска").click();
        page.getByText("Нужно сопровождение мед работника").click();
        page.getByText("Подтвердить стоимость").click();
        assertThat(page.getByText("Стоимость поездки: 456 руб.")).isVisible();
        page.getByText("Сделать заказ").click();

        page.navigate("http://127.0.0.1:8000/");

        page.getByText("Такси").click();
        page.getByText("Сделать заказ").dblclick();
        page.onDialog(dialog -> {
            if (dialog.type().equals("alert")) {
                assertEquals("Выберите тип транспорта перед оформлением заказа", dialog.message());
                dialog.accept();
            }
        });

        page.navigate("http://127.0.0.1:8000/");

        page.getByText("Такси").click();
        page.getByText("Мой профиль").click();
        page.fill("#id_last_name", "Иванов");
        page.fill("#id_email", "ivanov@example.com");
        page.fill("#id_mobile_phone", "+7 900 123 45 67");
        page.selectOption("#id_diseases", "1");
        page.selectOption("#id_diseases", "3");
        page.getByText("Назад").click();
        assertTrue(page.url().contains("http://127.0.0.1:8000/order_page/"), "Ожидаемый URL после отправки формы");
        page.getByText("Главное меню").click();
        assertTrue(page.url().contains("http://127.0.0.1:8000/"), "Ожидаемый URL после отправки формы");

        page.navigate("http://127.0.0.1:8000/");
        page.getByText("Спецоборудование").click();
        page.locator(".cart").click();

        Locator backButton = page.locator("button:has-text('Назад')");
        assertTrue(backButton.isVisible(), "Кнопка 'Назад' не видна на странице");

        Locator cartTable = page.locator("#cart-table");
        assertTrue(cartTable.isVisible(), "Таблица с товарами не видна");

        Locator rows = cartTable.locator("tbody tr");
        assertTrue(rows.count() > 0, "Корзина пуста");

        page.navigate("http://127.0.0.1:8000/");
        page.getByText("Спецоборудование").click();
        Locator menuToggleButton = page.locator(".menu-toggle");

        menuToggleButton.click();
        Locator mainPageLink = page.locator("nav.side-menu ul li a[href='/']");
        mainPageLink.click();
        assertEquals("http://127.0.0.1:8000/", page.url(), "Перейти на Главную не удалось");

        page.goBack();
        menuToggleButton.click();
        Locator taxiLink = page.locator("nav.side-menu ul li a[href='/order_page/']");
        taxiLink.click();
        assertEquals("http://127.0.0.1:8000/order_page/", page.url(), "Перейти на страницу Такси не удалось");

        page.goBack();
        menuToggleButton.click();

        Locator nutritionLink = page.locator("nav.side-menu ul li a[href='/nutrition/']");
        nutritionLink.click();
        assertEquals("http://127.0.0.1:8000/nutrition/", page.url(), "Перейти на страницу Питания не удалось");

        page.goBack();
        menuToggleButton.click();

        Locator medicineLink = page.locator("nav.side-menu ul li a[href='/medicine/']");
        medicineLink.click();
        assertEquals("http://127.0.0.1:8000/medicine/", page.url(), "Перейти на страницу Аптеки не удалось");

        page.goBack();
        menuToggleButton.click();

        Locator feedbackLink = page.locator("nav.side-menu ul li a[href='/feedback/']");
        feedbackLink.click();
        assertEquals("http://127.0.0.1:8000/feedback/", page.url(), "Перейти на страницу Обратной связи не удалось");

        page.goBack();
        menuToggleButton.click();

        Locator logoutLink = page.locator("nav.side-menu ul li a[href='/logout/']");
        logoutLink.click();
        assertEquals("http://127.0.0.1:8000/logout_page/", page.url(), "Перейти на страницу Выйти не удалось");

        page.navigate("http://127.0.0.1:8000/");

        page.getByText("Спецоборудование").click();
        page.locator(".account").click();
        assertEquals("http://127.0.0.1:8000/", page.url(), "Перейти на Главную не удалось");

        page.getByText("Аптека").click();
        assertEquals("http://127.0.0.1:8000/medicine/", page.url(), "Перейти на Главную не удалось");
        page.goBack();

        page.getByText("Обратная связь").click();
        assertEquals("http://127.0.0.1:8000/feedback/", page.url(), "Перейти на Главную не удалось");
        Locator feedbackForm = page.locator(".feedback-form");
        assertTrue(feedbackForm.isVisible(), "Форма обратной связи не видна");

        Locator nameField = page.locator("input[name='name']");
        Locator emailField = page.locator("input[name='email']");
        Locator messageField = page.locator("textarea[name='message']");
        assertTrue(nameField.isVisible(), "Поле 'Ваше имя' не видно");
        assertTrue(emailField.isVisible(), "Поле 'Ваш email' не видно");
        assertTrue(messageField.isVisible(), "Поле 'Ваше сообщение' не видно");

        Locator submitButton = page.locator("button[type='submit']");
        assertTrue(submitButton.isVisible(), "Кнопка 'Отправить' не видна");

        nameField.fill("Иван Иванов");
        emailField.fill("ivan@example.com");
        messageField.fill("Это тестовое сообщение.");

        submitButton.click();

        Locator backButton2 = page.locator("a[href='/']");
        backButton2.click();

        assertTrue(page.url().equals("http://127.0.0.1:8000/"), "Не удалось вернуться на главную страницу");

        page.navigate("http://127.0.0.1:8000/");

        page.getByText("Выйти").click();
        page.getByText("Войти").click();
        page.goBack();
        page.getByText("Зарегистрироваться").click();
        page.getByText("Войти").click();
        assertTrue(page.url().equals("http://127.0.0.1:8000/login/"), "Не удалось вернуться на главную страницу");
        page.getByText("Зарегистрироваться").click();
        assertTrue(page.url().equals("http://127.0.0.1:8000/signup/"), "Не удалось вернуться на главную страницу");
        Locator signupForm = page.locator(".login-form");
        assertTrue(signupForm.isVisible(), "Форма регистрации не видна");

        Locator usernameField = page.locator("input[name='username']");
        Locator passwordField1 = page.locator("input[name='password1']");
        Locator passwordField2 = page.locator("input[name='password2']");
        Locator submitButton2 = page.locator("button[type='submit']");

        assertTrue(usernameField.isVisible(), "Поле 'Ваше имя пользователя' не видно");
        assertTrue(passwordField1.isVisible(), "Поле 'Ваш пароль' не видно");
        assertTrue(passwordField2.isVisible(), "Поле 'Подтверждение пароля' не видно");
        assertTrue(submitButton2.isVisible(), "Кнопка 'Зарегистрироваться' не видна");

        String username = "testuser_" + generateRandomString(8);
        usernameField.fill(username);
        passwordField1.fill("NXDBYtZM.Tw33de");
        passwordField2.fill("NXDBYtZM.Tw33de");

        submitButton2.click();
        assertTrue(page.url().contains("/login/"), "Не удалось выполнить редирект на страницу логина");

        page.locator("input[name='username']").fill(username);
        page.locator("input[name='password']").fill("NXDBYtZM.Tw33de");

        page.getByText("Войти").click();

    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }
}
