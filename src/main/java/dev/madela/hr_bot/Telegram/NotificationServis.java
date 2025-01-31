package dev.madela.hr_bot.Telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class NotificationServis {

    @Autowired
    private HrTelegramBot hrTelegramBot;

    public void sendNotification(String chatId, String message) {
        hrTelegramBot.sendNotification(chatId, message);
    }
}
