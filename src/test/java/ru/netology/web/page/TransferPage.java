package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public void clearFields() {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public void fillAmountField(String value) {
        amountField.setValue(value);
    }

    public void fillCardNumberField(String value) {
        fromField.setValue(value);
    }

    public void submitTransfer() {
        transferButton.click();
    }

    public DashboardPage transferMoney(String sourceCardNumber, int amount) {
        clearFields();
        fillAmountField(String.valueOf(amount));
        fillCardNumberField(sourceCardNumber);
        submitTransfer();
        return new DashboardPage();
    }
}