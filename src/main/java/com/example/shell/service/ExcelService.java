package com.example.shell.service;

import com.example.shell.type.Subscriber;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    List<Subscriber> readMixFile(FileInputStream file) throws IOException;
    List<Subscriber> readData(FileInputStream file) throws IOException;
    void writeData(List<Subscriber> data,String path) throws IOException;
}
