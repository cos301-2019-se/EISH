package com.monotoneid.eishms.datapersistence.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.monotoneid.eishms.datapersistence.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HomeKeys {

    List<HomeKey> homeKeys;
    long lastCreation;
    Random ran;

    @Autowired
    PasswordEncoder encoder;

    public HomeKeys() {
        ran = new Random(System.currentTimeMillis());
        homeKeys = null;
        lastCreation = 0;
        //String enc = encoder.encode("Juhsjdll");
        //generateNewKeys();
    }

    public HomeKey findByKeyName(String keyname) {
        for (int i=0; homeKeys != null && i < homeKeys.size(); i++) {
            if (homeKeys.get(i).getKeyName().matches(keyname))
                return homeKeys.get(i);
        }

        return null;
    }


    private int generateRandomInt(int max) {
        return ran.nextInt(max);
    }

    private String generateKey() {
        char[] keyCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+".toCharArray();
        String genKey = "";
        
        for (int i=0; i < 8; i++) {
            genKey += keyCharacters[generateRandomInt(keyCharacters.length)];
        }

        return genKey;
    }

    public void generateNewKeys() {
        HomeKey generalKey = new HomeKey("general", generateKey(), UserType.ROLE_GENERAL);
        HomeKey renewalKey = new HomeKey("renewal", generateKey(), UserType.ROLE_RENEWAL);

        generalKey.setUserkey(encoder.encode(generalKey.getUserkey()));
        renewalKey.setUserkey(encoder.encode(renewalKey.getUserkey()));

        if (homeKeys == null) {
            homeKeys = new ArrayList<HomeKey>();
            homeKeys.add(generalKey);
            homeKeys.add(renewalKey);
        } else {
            homeKeys.clear();
            homeKeys.add(generalKey);
            homeKeys.add(renewalKey);
        }

        lastCreation = System.currentTimeMillis();
    }

    public void updateKeys() {
        if (keysExpired() || homeKeys == null) {
            generateNewKeys();
        }
    }

    public boolean keysExpired() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastCreation;

        //86400000 is 24 hours in milliseconds
        return timeElapsed >= 86400000;
    }
}