package com.mycompany.interface_pbo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class InterfacePbo {
    public static void main(String[] args) {
        
    }
    // Interface untuk membaca dan menulis data
    public interface DataStorage {
        void writeData(String data);
        String readData();
    }

    // Implementasi pada memory menggunakan ArrayList
    public static class MemoryStorage implements DataStorage {
        private ArrayList<String> data;

        public MemoryStorage() {
            this.data = new ArrayList<>();
        }

        @Override
        public void writeData(String data) {
            this.data.add(data);
        }

        @Override
        public String readData() {
            StringBuilder sb = new StringBuilder();
            for (String item : this.data) {
                sb.append(item).append("\n");
            }
            return sb.toString();
        }
    }

    // Implementasi pada file teks
    public static class FileStorage implements DataStorage {
        private String filePath;

        public FileStorage(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public void writeData(String data) {
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(data + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String readData() {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }

    // Implementasi pada SQLite database
    public static class DatabaseStorage implements DataStorage {
        private Connection connection;

        public DatabaseStorage(String databasePath) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
                try (Statement statement = connection.createStatement()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, value TEXT)");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void writeData(String data) {
            String sql = "INSERT INTO data (value) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, data);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String readData() {
            StringBuilder sb = new StringBuilder();
            String sql = "SELECT value FROM data";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    sb.append(resultSet.getString("value")).append("\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }

    // Penggunaan dalam aplikasi
    public static class Main {
        public static void main(String[] args) {
            // Contoh penggunaan MemoryStorage
            DataStorage memoryStorage = new MemoryStorage();
            memoryStorage.writeData("Data stored in memory");
            System.out.println("Memory storage: " + memoryStorage.readData());

            // Contoh penggunaan FileStorage
            DataStorage fileStorage = new FileStorage("data.txt");
            fileStorage.writeData("Data stored in file");
            System.out.println("File storage: " + fileStorage.readData());

            // Contoh penggunaan DatabaseStorage
            DataStorage databaseStorage = new DatabaseStorage("database.db");
            databaseStorage.writeData("Data stored in database");
            System.out.println("Database storage: " + databaseStorage.readData());
        }
    }
}
