package org.poo.cb;

import java.util.ArrayList;

public class APIBanca {
    public APIBanca() {}
    public void executeCommand(String command, String[] params, String exchange, String stock) {
        ExecutareComenzi ec = new ExecutareComenzi();
        if (command.equals("CREATE USER")) {
            ec.createUser(params);
        } else if (command.equals("ADD FRIEND")) {
            ec.addFriend(params);
        } else if (command.equals("ADD ACCOUNT")) {
            ec.addAccount(params);
        } else if (command.equals("ADD MONEY")) {
            ec.addMoney(params);
        } else if (command.equals("EXCHANGE MONEY")) {
            ec.exchangeMoney(params, "src/main/resources/" + exchange);
        } else if (command.equals("TRANSFER MONEY")) {
            ec.transferMoney(params);
        } else if (command.equals("BUY STOCKS")) {
            ec.buyStocks(params, "src/main/resources/" + stock);
        } else if (command.equals("RECOMMEND STOCKS")) {
            ec.recommendStocks("src/main/resources/" + stock);
        } else if (command.equals("LIST USER")) {
            ec.listUser(params);
        } else if (command.equals("LIST PORTFOLIO")) {
            ec.listPortfolio(params, "src/main/resources/" + stock);
        } else {
            ec.buyPremium(params);
        }
    }
}
