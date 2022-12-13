package com.example.shell.shell;

import com.example.shell.service.EmailService;
import com.example.shell.service.ExcelService;
import com.example.shell.type.Subscriber;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class EmailShell {
    private final EmailService emailService;
    private final ExcelService excelService;

    public EmailShell(EmailService emailService, ExcelService excelService) {
        this.emailService = emailService;
        this.excelService = excelService;
    }

    @ShellMethod("Send email to each subscribers.")
    public void sendEmail(String path) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path);
        List<Subscriber> subscribers = excelService.readMixFile(fileInputStream);
        for (Subscriber subscriber : subscribers) {
            Optional<Subscriber> oPairingSubscriber = subscribers.stream().filter(x -> x.getPairingId() == subscriber.getPairingId()).findFirst();
            if (oPairingSubscriber.isEmpty()){
                throw new Exception("Subscriber "+subscriber.getEmail()+" thiếu người gép đôi");
            }
            Subscriber pairingSubscriber = oPairingSubscriber.get();

            String content = "Chúc mừng bạn đã trở thành Ông già Noel. Hãy cùng xem món quà năm nay bạn sẽ chuẩn bị là gì nào?\n " +
                    " ví dụ 1: "+subscriber.getGive1()+",\n "+
                    " ví dụ 2: "+subscriber.getGive2()+",\n "+
                    " ví dụ 3: "+subscriber.getGive3();
            emailService.sendEmail(pairingSubscriber.getEmail(),"[Y-TEAM] Noel may mắn",content);
        }
    }
}
