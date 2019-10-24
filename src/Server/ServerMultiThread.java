//класс, реализующий многопоточный сервер
package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMultiThread {

    static ExecutorService executeIt = Executors.newFixedThreadPool(3);


    public static void main(String[] args) throws IOException {

        // стартуем сервер и инициализируем переменную для обработки консольных команд с самого сервера
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (server)\\src\\files\\SocketSettings.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        int port = Integer.parseInt(input.readLine());
        input.close();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("ВНИМАНИЕ! Во избежание возникновения ошибок на сервере,\nнастоятельно просим " +
                    "не производить резкого закрытия окна клиента,\n" +
                    "до того как сервер уведомит о готовности считать\n" +
                    "новую команду.");
            System.out.println("Создан сокет сервера, ожидаем подключения клиентов");

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                // становимся в ожидание
                // подключения к сокету общения под именем - "clientDialog" на
                // серверной стороне
                Socket client = server.accept();

                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable(при необходимости можно создать Callable)
                // монопоточную нить = сервер - MonoThreadClientHandler и тот
                // продолжает общение от лица сервера
                executeIt.execute(new MonoThread(client));
                Date date = new Date();
                System.out.println("Соединение установлено. Время подключения: " + date.toString());
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
