package com.group8.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ServerInfoImportor {
    public static void loadServerDataFromFile() {
        try (BufferedReader textReader = new BufferedReader(new FileReader("serverInfo.csv"))) {
            String line;
            while ((line = textReader.readLine()) != null) {
                String[] lineInfo = line.split(";");

                // dbserver,localserver, port, userName,userPassword, authcollection
                DatabaseController.dbServer = lineInfo[0];
                DatabaseController.dbLocalServer = lineInfo[1];
                DatabaseController.dbPort = Integer.parseInt(lineInfo[2]);
                DatabaseController.dbUser = lineInfo[3];
                DatabaseController.dbPassword = lineInfo[4];
                DatabaseController.authdbName = lineInfo[5];
                DatabaseController.isAuth = Boolean.valueOf(lineInfo[6]);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveServerDataToFile() {
        try (BufferedWriter transactionWriter = new BufferedWriter(new FileWriter("serverInfo.csv"))) {

            transactionWriter.write(DatabaseController.dbServer + ";" + DatabaseController.dbLocalServer + ";"
                    + DatabaseController.dbPort + ";" + DatabaseController.dbUser + ";" + DatabaseController.dbPassword
                    + ";" + DatabaseController.authdbName + ";" + DatabaseController.isAuth + ";"
                    + System.lineSeparator());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
    }
}
