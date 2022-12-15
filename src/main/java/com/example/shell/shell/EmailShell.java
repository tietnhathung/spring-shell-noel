package com.example.shell.shell;

import com.example.shell.service.EmailService;
import com.example.shell.service.ExcelService;
import com.example.shell.type.Subscriber;
import jakarta.mail.MessagingException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            Map<String, String> gifts = new HashMap<>();
            gifts.put("gift1", subscriber.getGive1());
            gifts.put("gift2", subscriber.getGive2());
            gifts.put("gift3", subscriber.getGive3());
            emailService.sendEmailHtml(pairingSubscriber.getEmail(),"[Y-TEAM] Noel may mắn",gifts);
        }
    }

    @ShellMethod("Send email test.")
    public void testEmail(){
        try {
            Map<String, String> gifts = new HashMap<>();
            gifts.put("gift1", "Cốc nước ông già noel thiệt là dế thưn :> (For example: https://www.instagram.com/p/ClfuxhhPMzx/?igshid=YmMyMTA2M2Y= (Hình đầu tiên, chiếc cốc ở trên bên phải)");
            gifts.put("gift2", "Vẫn là cốc nước ông già noel siêu dế thưn :> (For example: Vẫn là link trên, vẫn là hình đầu tiên, nhưng là chiếc cốc ở dưới bên phải =)))) )");
            gifts.put("gift3", "Tinh thần - Lời chúc");
            emailService.sendEmailHtml("tietnhathung@gmail.com","[Y-TEAM] Noel may mắn",gifts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
