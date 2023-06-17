package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
  private LoginPage loginPage;
  private DashboardPage dashboardPage;
  private TransferPage transferPage;

  @BeforeEach
  void setUp() {
    open("http://localhost:9999");
    loginPage = new LoginPage();
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
    VerificationPage verificationPage = loginPage.validLogin(authInfo);
    DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCode();
    dashboardPage = verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferMoneyBetweenOwnCards() {
    int initialBalanceFirstCard = dashboardPage.getCardBalance(0);
    int initialBalanceSecondCard = dashboardPage.getCardBalance(1);

    String sourceCardNumber = DataHelper.getSourceCardNumber();
    String destinationCardNumber = DataHelper.getDestinationCardNumber();
    int transferAmount = 1000;

    transferPage = dashboardPage.clickCardDepositButton(0);
    dashboardPage = transferPage.transferMoney(sourceCardNumber, transferAmount, destinationCardNumber);

    int expectedBalanceFirstCard = initialBalanceFirstCard + transferAmount;
    int expectedBalanceSecondCard = initialBalanceSecondCard - transferAmount;

    int actualBalanceFirstCard = dashboardPage.getCardBalance(0);
    int actualBalanceSecondCard = dashboardPage.getCardBalance(1);

    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    sourceCardNumber = DataHelper.getDestinationCardNumber();
    destinationCardNumber = DataHelper.getSourceCardNumber();

    initialBalanceFirstCard = dashboardPage.getCardBalance(0);
    initialBalanceSecondCard = dashboardPage.getCardBalance(1);

    transferPage = dashboardPage.clickCardDepositButton(1);
    dashboardPage = transferPage.transferMoney(sourceCardNumber, transferAmount, destinationCardNumber);

    expectedBalanceFirstCard = initialBalanceFirstCard - transferAmount;
    expectedBalanceSecondCard = initialBalanceSecondCard + transferAmount;

    actualBalanceFirstCard = dashboardPage.getCardBalance(0);
    actualBalanceSecondCard = dashboardPage.getCardBalance(1);

    assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }
}