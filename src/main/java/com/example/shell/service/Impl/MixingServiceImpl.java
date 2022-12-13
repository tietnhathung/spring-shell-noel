package com.example.shell.service.Impl;

import com.example.shell.service.MixingService;
import com.example.shell.type.Subscriber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MixingServiceImpl implements MixingService {
    @Override
    public List<Subscriber> mix(List<Subscriber> subscribers) {
        Random rand = new Random();
        List<Subscriber> males = subscribers.stream().filter(Subscriber::getSex).toList();
        List<Subscriber> females = subscribers.stream().filter(x -> !x.getSex()).toList();
        females.forEach(female -> {
            List<Subscriber> malesToPair = males.stream().filter(x -> x.getPairingId() == null).toList();
            Subscriber male = malesToPair.get(rand.nextInt(malesToPair.size()));
            female.setPairingId(male.getId());
            male.setPairingId(female.getId());
        });
        males.stream().filter(x -> x.getPairingId() == null).forEach( male -> {
            List<Subscriber> malesToPair = males.stream().filter(x -> x.getPairingId() == null).toList();
            Subscriber maleX = malesToPair.get(rand.nextInt(malesToPair.size()));
            male.setPairingId(maleX.getId());
            maleX.setPairingId(male.getId());
        });
        females.stream().filter(x -> x.getPairingId() == null).forEach( female -> {
            List<Subscriber> femalesToPair = females.stream().filter(x -> x.getPairingId() == null).toList();
            Subscriber femalesX = femalesToPair.get(rand.nextInt(femalesToPair.size()));
            female.setPairingId(femalesX.getId());
            femalesX.setPairingId(female.getId());
        });

        return subscribers;
    }
}
