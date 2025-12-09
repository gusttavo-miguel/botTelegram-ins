package br.com.mv.st.ins.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

// Classe principal do bot do Telegram, responsável por gerenciar as interações com os usuários.
public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return BotCredentials.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BotCredentials.BOT_TOKEN;
    }

    // Método responsável por coletar a mensagem enviada pelo usuário no chat do bot no telegram e realizar uma ação a partir disso.
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textMessage = update.getMessage().getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            String response;

            // Personaliza resposta de acordo com a opção selecionada
            if ("chatId".equalsIgnoreCase(textMessage)) {
                response = "1 - O seu chatId é: " + chatId;
            } else if ("Confirmar recebimento".equalsIgnoreCase(textMessage)) {
                response = "Em breve será disponibilizada uma ação para esta opção.";
            } else if ("Ajuda".equalsIgnoreCase(textMessage)) {
                response = "Opções disponíveis:\n1 - chatId (mostrar seu chatId)\n2 - Opção 2 (Confirmar recebimento do alerta).";
            } else {
                response = "Escolha uma das opções abaixo:";
            }

            // Envia mensagem com botões fixos
            sendMessageWithKeyboard(chatId, response);
        }
    }

//      Método responsável por enviar uma mensagem com teclado customizado para o usuário no Telegram
    private void sendMessageWithKeyboard(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

//         Cria teclado customizado com opções fixas
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

//        Faz com que o teclado se ajuste ao conteúdo / tela do cliente Telegram.
//        Em vez de ocupar espaço fixo, o cliente pode reduzir a altura dos botões para ficar mais compacto.
//        Útil para evitar que o teclado cubra muita área da conversa.
        keyboardMarkup.setResizeKeyboard(true);

//        Define se o teclado deve desaparecer automaticamente após o usuário pressionar um botão.
//        false mantém o teclado visível após o clique; true faz o teclado sumir logo após a escolha (comportamento “uma vez só”).
        keyboardMarkup.setOneTimeKeyboard(false);

//         Cria as linhas do teclado e adiciona os botões
        List<KeyboardRow> keyboard = new ArrayList<>();

//        Primeira linha do teclado com um botão "chatId" e segunda linha com os botões "Opção 2" e "Ajuda"
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(KeyboardButton.builder().text("chatId").build());
        row2.add(KeyboardButton.builder().text("Confirmar recebimento").build());
        row3.add(KeyboardButton.builder().text("Ajuda").build());

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

//        Envia a mensagem com o teclado customizado para o usuário no Telegram
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Método responsávem por realizar as ações do bot com base na integração do usuário
    public void sendMessage(String text, String chatId) {
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