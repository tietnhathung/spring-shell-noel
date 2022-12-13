package com.example.shell.shell;

import com.example.shell.service.ExcelService;
import com.example.shell.service.MixingService;
import com.example.shell.type.Subscriber;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@ShellComponent
public class MixFileShell {
    private final ExcelService excelService;
    private final MixingService mixingService;

    public MixFileShell(ExcelService excelService, MixingService mixingService) {
        this.excelService = excelService;
        this.mixingService = mixingService;
    }

    @ShellMethod("mix subscribers with each other.")
    public void mix(String path) {
        try {
            File file = new File(path);
            String parent = file.getParent();
            FileInputStream fileInputStream = new FileInputStream(file);
            List<Subscriber> subscribers = excelService.readData(fileInputStream);
            List<Subscriber> afterMix = mixingService.mix(subscribers);
            excelService.writeData(afterMix, parent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
