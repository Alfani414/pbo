/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interface_pbo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author A-15
 */
    public class FileStorage implements DataStorage {
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
