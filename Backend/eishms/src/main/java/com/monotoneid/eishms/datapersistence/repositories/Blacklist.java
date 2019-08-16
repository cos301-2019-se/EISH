package com.monotoneid.eishms.datapersistence.repositories;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.datapersistence.models.BlacklistItem;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Blacklist {
    List<BlacklistItem> blacklistedUsers;

    public Blacklist() {
        blacklistedUsers = new ArrayList<BlacklistItem>();
    }

    public void addBlacklistItem(BlacklistItem blacklistedItem) {
        blacklistedUsers.add(blacklistedItem);
    }

    public boolean isTokenBlacklisted(String token) throws NotFoundException {
        for (BlacklistItem item : blacklistedUsers) {
            if (item.isSameToken(token)) {
                    return item.isBlacklisted();
            }
        }

        throw new NotFoundException();
    }

    public void removeBlacklistItem(String token) {
        for (BlacklistItem item : blacklistedUsers) {
            if (item.isSameToken(token)) {
                blacklistedUsers.remove(item);
            }
        }
    }

    // If there are multiple entries with the same userName blacklist all.
    public void blacklistUser(String userName) {
        for (BlacklistItem item : blacklistedUsers) {
            if (item.isSameUserName(userName)) {
                item.blacklist();
            }
        }
    }
}