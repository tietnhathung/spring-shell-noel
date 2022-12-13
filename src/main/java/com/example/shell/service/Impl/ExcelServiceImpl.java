package com.example.shell.service.Impl;

import com.example.shell.service.ExcelService;
import com.example.shell.type.Subscriber;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<Subscriber> readMixFile(FileInputStream file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<Subscriber> subscribers = new ArrayList<>();
        int rawTotal = sheet.getLastRowNum();
        for (int j = 1; j <= rawTotal; j++) {
            Row row = sheet.getRow(j);
            Cell cellId = row.getCell(0);
            Cell cellPairingId = row.getCell(1);
            Cell cellEmail = row.getCell(2);
            Cell cellGive1 = row.getCell(3);
            Cell cellGive2 = row.getCell(4);
            Cell cellGive3 = row.getCell(5);
            Subscriber subscriber = new Subscriber(cellId.getStringCellValue());
            subscriber.setPairingId(cellPairingId.getStringCellValue());
            subscriber.setEmail(cellEmail.getStringCellValue());
            subscriber.setGive1(cellGive1.getStringCellValue());
            subscriber.setGive2(cellGive2.getStringCellValue());
            subscriber.setGive3(cellGive3.getStringCellValue());
            subscribers.add(subscriber);
        }
        return subscribers;
    }

    @Override
    public List<Subscriber> readData(FileInputStream file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<Subscriber> subscribers = new ArrayList<>();
        int rawTotal = sheet.getLastRowNum();
        for (int j = 1; j <= rawTotal; j++) {
            Row row = sheet.getRow(j);
            Cell cellTime = row.getCell(0);
            Cell cellEmail = row.getCell(1);
            Cell cellSex = row.getCell(2);
            Cell cellGive1 = row.getCell(3);
            Cell cellGive2 = row.getCell(4);
            Cell cellGive3 = row.getCell(5);
            Subscriber subscriber = new Subscriber();
            subscriber.setTime(cellTime.getLocalDateTimeCellValue());
            subscriber.setSex(cellSex.getNumericCellValue() == 1);
            subscriber.setEmail(cellEmail.getStringCellValue());
            subscriber.setGive1(cellGive1.getStringCellValue());
            subscriber.setGive2(cellGive2.getStringCellValue());
            subscriber.setGive3(cellGive3.getStringCellValue());
            subscribers.add(subscriber);
        }
        return subscribers;
    }

    @Override
    public void writeData(List<Subscriber> data,String path) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row rowHeader = sheet.createRow(0);
        rowHeader.createCell(0).setCellValue("ID");
        rowHeader.createCell(1).setCellValue("PairingId");
        rowHeader.createCell(2).setCellValue("Email");
        rowHeader.createCell(3).setCellValue("Give1");
        rowHeader.createCell(4).setCellValue("Give2");
        rowHeader.createCell(5).setCellValue("Give3");
        int rowNumber = 1;
        for (Subscriber subscriber : data) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(subscriber.getId().toString());
            row.createCell(1).setCellValue(subscriber.getPairingId().toString());
            row.createCell(2).setCellValue(subscriber.getEmail());
            row.createCell(3).setCellValue(subscriber.getGive1());
            row.createCell(4).setCellValue(subscriber.getGive2());
            row.createCell(5).setCellValue(subscriber.getGive3());
        }
        FileOutputStream out = new FileOutputStream(path+"//mix.xlsx");
        workbook.write(out);
        workbook.close();
    }
}
