package br.com.mv.st.ins.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return BotCredentials.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BotCredentials.BOT_TOKEN;
    }

    // Método responsável por coletar a mensagem enviada pelo usuário no chat do bot no telegram e realizar uma ação apartir disso.
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textMessage = update.getMessage().getText();
            String response;

            if (textMessage.equalsIgnoreCase("chatId") || textMessage.equalsIgnoreCase("1")) {
                response = "O seu chatId é:\s" + update.getMessage().getChatId();

            } else {
                response = "No momento, o único comando aceito é:\n 1 - chatId";
            }

            try {
                execute(SendMessage.builder()
                        .text(response)
                        .chatId(update.getMessage().getChatId())
                        .build()); // método que envia a resposta do bot ao chat do usuário no telegram
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    // Método responsávem por realizar as ações do bot com base na integração do usuário
    public void sendMessage(String text, long chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}