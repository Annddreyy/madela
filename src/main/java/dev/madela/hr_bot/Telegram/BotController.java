package dev.madela.hr_bot.Telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BotController {

    private final HrTelegramBot hrTelegramBot;

    @Autowired
    public BotController(HrTelegramBot hrTelegramBot) {
        this.hrTelegramBot = hrTelegramBot;
    }

    @GetMapping("/sendMessage")
    public String sendMessage(Long chatId, String text) {
        hrTelegramBot.sendMessage(chatId, text);
        return "Сообщение отправлено!";
    }
}
