package com.bankcards.console;

import com.bankcards.domain.Cardholder;
import com.bankcards.domain.Creditcard;
import com.bankcards.service.CardholderService;
import com.bankcards.service.CreditcardService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleView {

    public ConsoleView() {
        while (true) {
            this.show();
        }
    }

    public void show(){
        System.out.println("1. Добавить владельца карты.");
        System.out.println("2. Добавить кредитную карту.");
        System.out.println("3. Изменить данные владельца карты.");
        System.out.println("4. Вывести информацию о пользователе.");
        System.out.println("5. Вывести информацию о карте.");
        System.out.println("6. Изменить данные кредитной карты.");
        System.out.println("7. Удалить владельца кредитной карты.");
        System.out.println("8. Удалить кредитную карту.");
        System.out.println("9. Вывести всех владельцев карт.");
        System.out.println("10. Вывести все кредитные карты.");
        System.out.println("11. Закрыть программу.");

        Cardholder cardholder = null;
        Creditcard creditcard = null;

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();

        CreditcardService creditcardService = new CreditcardService();
        CardholderService cardholderService = new CardholderService();
        switch (i) {
            case 1:
                System.out.println("Введите ID пользователя: ");
                long userId = in.nextLong();
                System.out.println("Введите имя пользователя: ");
                in.nextLine();
                String username = in.nextLine();
                System.out.println("Введите адрес пользователя: ");
                String address = in.nextLine();
                System.out.println("Введите номер телефона пользователя: ");
                String phoneNumber = in.nextLine();
                System.out.println("Введите дату рождения пользователя (YYYY-MM-DD): ");
                Date birthday = Date.valueOf(in.nextLine());

                cardholder = new Cardholder(userId, username, address, phoneNumber, birthday);
                CardholderService cs = new CardholderService();
                try {
                    cs.create(cardholder);
                    System.out.println("Cardholder created successfully.");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 2:
                System.out.println("Введите ID карты: ");
                long cardId = in.nextLong();
                System.out.println("Введите номер карты: ");
                in.nextLine();
                Long cardNumber = in.nextLong();
                System.out.println("Введите срок окончания действия карты (mm/YY): ");
                in.nextLine();
                String expDate = in.nextLine();
                System.out.println("Введите CVV карты: ");
                int cvv = in.nextInt();
                System.out.println("Введите ID владельца карты: ");
                long ownerId = in.nextLong();

                creditcard = new Creditcard(cardId, cardNumber, expDate, cvv, ownerId);

                try {
                    creditcardService.create(creditcard);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 3:
                System.out.println("Введите ID пользователя: ");
                long id = in.nextLong();
                try {
                    cardholderService.update(id);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 4:
                System.out.println("Введите ID пользователя: ");
                in.nextLine();
                long getId = in.nextLong();
                try {
                    System.out.println(cardholderService.get(getId).toString());
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 5:
                System.out.println("Введите ID карты: ");
                in.nextLine();
                getId = in.nextLong();
                try {
                    System.out.println(creditcardService.get(getId).toString());
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 6:
                System.out.println("Введите ID карты: ");
                in.nextLine();
                getId = in.nextLong();
                try {
                    System.out.println(creditcardService.update(getId));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 7:
                System.out.println("Введите ID владельца карты: ");
                in.nextLine();
                getId = in.nextLong();
                try {
                    System.out.println(cardholderService.delete(getId));
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 8:
                System.out.println("Введите ID карты: ");
                in.nextLine();
                getId = in.nextLong();
                try {
                    System.out.println(creditcardService.delete(getId));
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 9:
                try{
                    System.out.println(cardholderService.getAll().toString());
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 10:
                try{
                    System.out.println(creditcardService.getAll().toString());
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            case 11:
                System.exit(0);
        }
    }
}
