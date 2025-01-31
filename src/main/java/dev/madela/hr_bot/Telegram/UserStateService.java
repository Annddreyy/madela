package dev.madela.hr_bot.Telegram;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserStateService {

    private Set<Long> activeUsers = new HashSet<>();

    public void addUser(long chatId) {
        activeUsers.add(chatId);
    }

    public Set<Long> getActiveUsers() {
        return activeUsers;
    }
}
