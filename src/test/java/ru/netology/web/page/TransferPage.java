package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

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
            amountField.sendKeys("\uE008", "\uE012", value);
        } else if (field.equals("from")) {
            fromField.sendKeys("\uE008", "\uE012", value);
        }
    }

    public void fillFieldWithKeyboardShortcut(String field, String value) {
        if (field.equals("amount")) {
            amountField.sendKeys(value);
        } else if (field.equals("from")) {
            fromField.sendKeys(value);
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
        fillFieldWithKeyboardShortcut("amount", "\uE008" + "\uE012");
        fillFieldWithKeyboardShortcut("from", "\uE008" + "\uE012");
        fillField("amount", String.valueOf(amount));
        fillField("from", sourceCardNumber);
        submitTransfer();
        return new DashboardPage();
    }
}