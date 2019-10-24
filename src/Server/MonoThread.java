// класс, реализующий соединение с одним клиентом
package Server;

import Server.controller.*;
import model.*;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MonoThread implements Runnable {

    private Socket clientDialog;

    public MonoThread(Socket client) {
        this.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientDialog.getInputStream());
            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом
            while (!clientDialog.isClosed()) {
                System.out.println("IP-адрес клиента: " + clientDialog.getInetAddress());
                System.out.println("Порт: " + clientDialog.getLocalPort());
                System.out.println("Сервер производит считывание с канала");
                String entry = (String)in.readObject();
                if (entry.equals("Registration"))
                {
                    DBWorker dbUserData = new DBWorker();
                    Users user = ((Users)in.readObject());
                    System.out.println("В меню регистрации");
                    try {
                        String answertoserver = dbUserData.AddOne(user);
                        out.writeObject(answertoserver);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (entry.equals("Login"))
                {
                    DBWorker dbUserData = new DBWorker();
                    Users user = ((Users)in.readObject());
                    System.out.println("В меню авторизации");
                    try {
                        String answertoserver =  dbUserData.checkuser(user);
                        out.writeObject(answertoserver);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (entry.equals("DeleteUser"))
                {
                    UsersController UC = new UsersController();
                    Users uc = ((Users)in.readObject());
                    System.out.println("Удаляем пользователя");
                    String answertoserver = UC.DeleteUser(uc);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("ShowAllClients"))
                {
                    ClientsController CC = new ClientsController();
                    ArrayList<Clients> mas;
                    System.out.println("Выводим список клиентов");
                    mas = CC.ShowClients();
                    out.writeObject(mas);
                }
                if (entry.equals("CurrencyFor_Add"))
                {
                    CurrencyController CC = new CurrencyController();
                    ArrayList<String> mas;
                    System.out.println("Выводим список имеющейся валюты");
                    mas = CC.GetCurrencyID();
                    out.writeObject(mas);
                }
                if (entry.equals("AddOneClient"))
                {
                    ClientsController CC = new ClientsController();
                    Clients cl = new Clients((Clients)in.readObject());
                    System.out.println("Добавляем клиента");
                    String answertoserver = CC.AddOne(cl);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("DeleteOneClient"))
                {
                    ClientsController CC = new ClientsController();
                    Clients cc = ((Clients)in.readObject());
                    System.out.println("Удаляем клиента");
                    String answertoserver = CC.DeleteClient(cc);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("AddOneCredit"))
                {
                    CreditsController CC = new CreditsController();
                    Credits cr = new Credits((Credits)in.readObject());
                    System.out.println("Добавляем кредит");
                    String answertoserver = CC.AddOne(cr);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("DeleteOneCredit"))
                {
                    CreditsController CC = new CreditsController();
                    Credits cc = ((Credits)in.readObject());
                    System.out.println("Удаляем кредит");
                    String answertoserver = CC.DeleteCredit(cc);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("GiveCredit"))
                {
                    ClientsCreditsController CCC = new ClientsCreditsController();
                    ClientsCredits cc = new ClientsCredits((ClientsCredits)in.readObject());
                    System.out.println("Выдаём кредит");
                    String answertoserver = CCC.AddOne(cc);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("ReturnCredit"))
                {
                    ClientsCreditsController CCC = new ClientsCreditsController();
                    ClientsCredits cc = (ClientsCredits)in.readObject();
                    System.out.println("Погашаем кредит");
                    String answertoserver = CCC.ReturnCredit(cc);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("EditClient"))
                {
                    ClientsController CC = new ClientsController();
                    Clients cl = new Clients((Clients)in.readObject());
                    System.out.println("Редактируем клиента");
                    String answertoserver = CC.EditClient(cl);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("EditCredit"))
                {
                    CreditsController CC = new CreditsController();
                    Credits cr = new Credits((Credits)in.readObject());
                    System.out.println("Редактируем кредит");
                    String answertoserver = CC.EditCredit(cr);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("EditCurrency"))
                {
                    CurrencyController CC = new CurrencyController();
                    Currency cur = new Currency((Currency)in.readObject());
                    System.out.println("Изменяем курс валюты");
                    CC.EditCurrency(cur);
                }
                if (entry.equals("SearchClientBySurname"))
                {
                    ClientsController CC = new ClientsController();
                    Clients c = (Clients)in.readObject();
                    System.out.println("Ищем по фамилии");
                    ArrayList<Clients> answertoserver = CC.SearchSurname(c);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("SearchClientByCity"))
                {
                    ClientsController CC = new ClientsController();
                    Clients c = (Clients)in.readObject();
                    System.out.println("Ищем по городу");
                    ArrayList<Clients> answertoserver = CC.SearchCity(c);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("SearchCreditBySumm"))
                {
                    CreditsController CC = new CreditsController();
                    int min = (int)in.readObject();
                    System.out.println("Ищем по максимальной сумме");
                    int max = (int)in.readObject();
                    String curr = (String)in.readObject();
                    ArrayList<Credits> answertoserver = CC.SearchSumm(min, max, curr);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("SearchCreditByRate"))
                {
                    CreditsController CC = new CreditsController();
                    int min = (int)in.readObject();
                    System.out.println("Ищем по номинальной процентной ставке");
                    int max = (int)in.readObject();
                    String curr = (String)in.readObject();
                    ArrayList<Credits> answertoserver = CC.SearchRate(min, max, curr);
                    out.writeObject(answertoserver);
                }
                if (entry.equals("Clients_CurrencyFor_Add"))
                {
                    ClientsController CC = new ClientsController();
                    CreditsController C = new CreditsController();
                    ArrayList<String> mas1;
                    System.out.println ("Считываем списки клиентов и кредитов");
                    mas1 = CC.GetClientsSurname();
                    out.writeObject(mas1);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String> mas2;
                    mas2 = C.GetCreditsName();
                    out.writeObject(mas2);
                }
                if (entry.equals("GetIDBySurname"))
                {
                    ClientsController CC = new ClientsController();
                    Clients cl = new Clients((Clients)in.readObject());
                    System.out.println("Получаем идентификатор клиента по фамилии");
                    int id = CC.GetIDBySurname(cl);
                    out.writeObject(id);
                }
                if (entry.equals("GetIDByCredit"))
                {
                    CreditsController CC = new CreditsController();
                    Credits cr = new Credits((Credits)in.readObject());
                    System.out.println("Устанавливаем название кредита по значению ID");
                    Credits crN = CC.GetIDByCredit(cr);
                    out.writeObject(crN);
                }
                if (entry.equals("ShowAllCurrency"))
                {
                    CurrencyController CC = new CurrencyController();
                    ArrayList<Currency> mas;
                    System.out.println("В таблице валюты");
                    mas = CC.ShowCurrency();
                    out.writeObject(mas);
                }
                if (entry.equals("ShowAllCredits"))
                {
                    CreditsController CC = new CreditsController();
                    ArrayList<Credits> mas;
                    System.out.println("В таблице кредитов");
                    mas = CC.ShowCredits();
                    out.writeObject(mas);
                }
                if (entry.equals("ShowAllClientsCredits"))
                {
                    ClientsCreditsController CC = new ClientsCreditsController();
                    ArrayList<ClientsCredits> mas;
                    System.out.println("В таблице кредитов клиентов");
                    mas = CC.ShowClientsCredits();
                    out.writeObject(mas);
                }
                if (entry.equals("ShowAllUsers"))
                {
                    UsersController CC = new UsersController();
                    ArrayList<Users> mas;
                    System.out.println("В таблице пользователей");
                    mas = CC.ShowUsers();
                    out.writeObject(mas);
                }

                System.out.println("Получена следующая команда от клиента - " + entry);
                Thread.sleep(3000);
                // инициализация проверки условия продолжения работы с клиентом
                // по этому сокету по кодовому слову - quit в любом регистре
                if (entry.equals("quit")) {
                    // если кодовое слово получено то инициализируется закрытие
                    // серверной нити
                    System.out.println("Клиент инициировал разрыв содинения...");
                    in.close();
                    out.close();
                    // потом закрываем сокет общения с клиентом в нити моносервера
                    clientDialog.close();
                    //break;
                }

                // если условие окончания работы не верно - продолжаем работу -
                // отправляем эхо обратно клиенту

                out.flush();

                // возвращаемся в начало для считывания нового сообщения
            }
            // если условие выхода - верно выключаем соединения
            Date date = new Date();
            System.out.println("Клиент "+ clientDialog.getInetAddress() +" отключился. Время отключения: " + date.toString());
            System.out.println("Закрыто соединение сокета");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}