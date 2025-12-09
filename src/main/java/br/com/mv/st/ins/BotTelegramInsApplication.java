package br.com.mv.st.ins;

import br.com.mv.st.ins.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class BotTelegramInsApplication {

	public static void main(String[] args) {

        // Inicia a aplicação Spring Boot
		SpringApplication.run(BotTelegramInsApplication.class, args);

        // Registra o bot na API do Telegram
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}
}