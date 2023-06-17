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

    public void clearAndFillField(String field, String value) {
        if (field.equals("amount")) {
            amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            amountField.setValue(value);
        } else if (field.equals("from")) {
            fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            fromField.setValue(value);
        }
    }

    public void fillField(String field, String value) {
        if (field.equals("amount")) {
            amountField.setValue(value);
        } else if (field.equals("from")) {
            fromField.setValue(value);
        }
    }

    public void submitTransfer() {
        transferButton.click();
    }

    public DashboardPage transferMoney(String sourceCardNumber, int amount, String destinationCardNumber) {
        clearAndFillField("amount", "");
        clearAndFillField("from", "");
        fillField("amount", String.valueOf(amount));
        fillField("from", sourceCardNumber);
        submitTransfer();
        return new DashboardPage();
    }
}