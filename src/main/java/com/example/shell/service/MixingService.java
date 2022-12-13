package com.example.shell.service;

import com.example.shell.type.Subscriber;

import java.util.List;

public interface MixingService {
    List<Subscriber> mix(List<Subscriber> subscribers);
}
