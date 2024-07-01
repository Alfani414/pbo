package com.mycompany.interface_pbo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


    // Interface untuk membaca dan menulis data
    public interface DataStorage {
        void writeData(String data);
        String readData();
    }

    // Implementasi pada memory menggunakan ArrayList


    // Implementasi pada file teks

    // Implementasi pada SQLite database


    // Penggunaan dalam aplikasi
