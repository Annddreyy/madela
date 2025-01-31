package dev.madela.hr_bot.Telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class HrTelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(HrTelegramBot.class);

    @Autowired
    private UserStateService userStateService;

    @Override
    public String getBotUsername() {
        return "Madela_HRBot"; // имя пользователя бота
    }

    @Override
    public String getBotToken() {
        return "7087269656:AAH7h68xN7lNOR4Q8DTpq6tVQE7GC6BxFjg"; // токен бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Добавляем пользователя в список активных
            userStateService.addUser(chatId);

            switch (messageText) {
                case "/start":
                    StartCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    HelpCommandRecieved(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "такой команды нет");
            }
        }
    }

    private void StartCommandRecieved(long chatId, String name) {
        String answer = "Здравствуйте, " + name + ", Я HRBot!";
        sendMessage(chatId, answer);
        // Отправка уведомления после приветствия
        String notificationMessage = "Появились новые вакансии!";
        sendNotification(String.valueOf(chatId), notificationMessage);
    }

    private void HelpCommandRecieved(long chatId, String name) {
        String answer = "Команды: /start - начать, /help - помощь.";
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
            logger.info("Notification sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            logger.error("Error sending notification to chatId: {}", chatId, e);
        }
    }

    public void sendNotificationToAll(String message) {
        logger.info("Sending notification to all active users...");
        for (Long chatId : userStateService.getActiveUsers()) {
            logger.info("Sending notification to chatId: {}", chatId);
            sendNotification(String.valueOf(chatId), message);
        }
    }
}
