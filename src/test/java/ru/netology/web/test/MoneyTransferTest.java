package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

  @Test
  void shouldTransferMoneyBetweenOwnCards() {
    open("http://localhost:9999");
    LoginPage loginPage = new LoginPage();
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
    VerificationPage verificationPage = loginPage.validLogin(authInfo);
    DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

    int initialBalanceFirstCard = dashboardPage.getCardBalance(0);
    int initialBalanceSecondCard = dashboardPage.getCardBalance(1);

    String sourceCardNumber = "5559 0000 0000 0002";
    String destinationCardNumber = "5559 0000 0000 0001";
    int transferAmount = 1000;

    dashboardPage.clickCardDepositButton(0);
    TransferPage transferPage = new TransferPage();
    transferPage.clearAndFillField("amount", "");
    transferPage.clearAndFillField("from", "");
    transferPage.fillFieldWithKeyboardShortcut("amount", Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.BACK_SPACE);
    transferPage.fillFieldWithKeyboardShortcut("from", Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.BACK_SPACE);
    transferPage.fillField("amount", String.valueOf(transferAmount));
    transferPage.fillField("from", sourceCardNumber);
    transferPage.submitTransfer();

    int expectedBalanceFirstCard = initialBalanceFirstCard + transferAmount;
    int expectedBalanceSecondCard = initialBalanceSecondCard - transferAmount;

    int actualBalanceFirstCard = dashboardPage.getCardBalance(0);
    int actualBalanceSecondCard = dashboardPage.getCardBalance(1);

    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    sourceCardNumber = "5559 0000 0000 0001";
    destinationCardNumber = "5559 0000 0000 0002";

    int updatedInitialBalanceFirstCard = dashboardPage.getCardBalance(0);
    int updatedInitialBalanceSecondCard = dashboardPage.getCardBalance(1);

    dashboardPage.clickCardDepositButton(1);
    transferPage.clearAndFillField("amount", "");
    transferPage.clearAndFillField("from", "");
    transferPage.fillFieldWithKeyboardShortcut("amount", Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.BACK_SPACE);
    transferPage.fillFieldWithKeyboardShortcut("from", Keys.chord(Keys.SHIFT, Keys.HOME) + Keys.BACK_SPACE);
    transferPage.fillField("amount", String.valueOf(transferAmount));
    transferPage.fillField("from", sourceCardNumber);
    transferPage.submitTransfer();

    expectedBalanceFirstCard = updatedInitialBalanceFirstCard - transferAmount;
    expectedBalanceSecondCard = updatedInitialBalanceSecondCard + transferAmount;

    actualBalanceFirstCard = dashboardPage.getCardBalance(0);
    actualBalanceSecondCard = dashboardPage.getCardBalance(1);

    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }
}