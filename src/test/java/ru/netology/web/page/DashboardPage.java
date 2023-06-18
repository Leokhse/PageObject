package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;

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

}