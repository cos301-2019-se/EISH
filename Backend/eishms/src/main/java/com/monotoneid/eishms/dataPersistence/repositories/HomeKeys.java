package com.monotoneid.eishms.dataPersistence.repositories;

import java.util.Date;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.*;

import org.springframework.stereotype.Component;

@Component
public class HomeKeys {

    List<HomeKey> homeKeys;
    Date lastCreation;

    public HomeKeys() {
        generateNewKeys();
    }

    public HomeKey findByKeyName(String keyname) {
        for (int i=0; i < homeKeys.size(); i++) {
            if (homeKeys.get(i).getKeyName().matches(keyname))
                return homeKeys.get(i);
        }

        return null;
    }

    public void generateNewKeys() {

    }

    public void updateKeys() {
        if (keysExpired()) {
            generateNewKeys();
        }
    }

    public boolean keysExpired() {
        return false;
    }
}