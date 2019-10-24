package Server.controller;

import java.io.*;

public class DBSettings  {
    protected String dbHost;
    protected String dbPort;
    protected String dbUser;
    protected String dbPass;
    protected String dbName;

    DBSettings () throws IOException {
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\CP (server)\\src\\files\\DBSettings.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        dbHost = input.readLine();
        dbPort = input.readLine();
        dbUser = input.readLine();
        dbPass = input.readLine();
        dbName = input.readLine();
        input.close();
    }
}
