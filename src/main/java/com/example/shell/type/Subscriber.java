package com.example.shell.type;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subscriber {
    private final UUID id;
    private LocalDateTime time;
    private String email;
    private Boolean sex;
    private String give1;
    private String give2;
    private String give3;
    private UUID pairingId;
    public Subscriber() {
        this.id = UUID.randomUUID();
    }
    public Subscriber(String id) {
        this.id = UUID.fromString(id);
    }
    public Subscriber(LocalDateTime time, String email, Boolean sex, String give1, String give2, String give3) {
        this.id = UUID.randomUUID();
        this.time = time;
        this.email = email;
        this.sex = sex;
        this.give1 = give1;
        this.give2 = give2;
        this.give3 = give3;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getGive1() {
        return give1;
    }

    public void setGive1(String give1) {
        this.give1 = give1;
    }

    public String getGive2() {
        return give2;
    }

    public void setGive2(String give2) {
        this.give2 = give2;
    }

    public String getGive3() {
        return give3;
    }

    public void setGive3(String give3) {
        this.give3 = give3;
    }
    public UUID getPairingId() {
        return pairingId;
    }

    public void setPairingId(UUID pairingId) {
        this.pairingId = pairingId;
    }
    public void setPairingId(String pairingId) {
        this.pairingId =  UUID.fromString(pairingId);;
    }
    @Override
    public String toString() {
        return "{" +
                "id=" + id + '\'' +
                ", pairingId=" + pairingId + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                '}';
    }

}
