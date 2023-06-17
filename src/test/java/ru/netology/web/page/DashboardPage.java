package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private ElementsCollection cards = $$(".list__item");

  public DashboardPage() {
    cards.first().shouldBe(visible);
  }

  public int getCardBalance(int index) {
    String balanceText = cards.get(index).getText();
    return extractBalance(balanceText);
  }

  private int extractBalance(String balanceText) {
    String[] parts = balanceText.split("баланс: ")[1].split(" р.");
    String balanceValue = parts[0].replaceAll("\\s+", "");
    return Integer.parseInt(balanceValue);
  }

  public TransferPage clickCardDepositButton(int index) {
    cards.get(index).$$("button[data-test-id=action-deposit]").first().click();
    return new TransferPage();
  }

  public void verifyCardBalance(int index, int expectedBalance) {
    int actualBalance = getCardBalance(index);
    if (actualBalance != expectedBalance) {
      throw new AssertionError("Expected card balance: " + expectedBalance + ", but found: " + actualBalance);
    }
  }
}