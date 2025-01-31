package dev.madela.hr_bot.Telegram;

import dev.madela.hr_bot.Telegram.HrTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private HrTelegramBot hrTelegramBot;

    @Override
    public void run(String... args) throws Exception {
        String message = "Появились новые вакансии!";
        hrTelegramBot.sendNotificationToAll(message);
    }
}
