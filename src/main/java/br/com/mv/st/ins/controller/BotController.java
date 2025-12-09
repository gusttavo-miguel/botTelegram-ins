package br.com.mv.st.ins.controller;

import br.com.mv.st.ins.bot.BotCredentials;
import br.com.mv.st.ins.bot.TelegramBot;
import br.com.mv.st.ins.pojo.Data;
import br.com.mv.st.ins.pojo.MessageWrapper;
import br.com.mv.st.ins.pojo.Ticket;
import br.com.mv.st.ins.utils.FormatMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/bot")
public class BotController {

    @PostMapping(path = "/tickets/send")
    public ResponseEntity<String> ticketsSend (@RequestBody MessageWrapper wrapper) {

        // Instancia o objeto Data a partir do json recebido como parâmetro e transformado em objeto Java.
        // OBS: O é enviado pela Hemdall-API a partir de uma requisição HTTP POST para a API do Bot.
        Data data = wrapper.getMessage().getData();

        //obtém a lista de tickets a partir do objeto Data
        List<Ticket> ticketsList = data.getTicketsAsList();

        // Verifica se a lista de tickets está vazia se sim, retorna NOT_FOUND
        if (ticketsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Formata a mensagem a ser enviada para o Telegram
        FormatMessage message = new FormatMessage();
        String messageFormated = message.formattedMessage(ticketsList);

        // Envia a mensagem formatada para o Telegram
        TelegramBot bot = new TelegramBot();
        bot.sendMessage(messageFormated, BotCredentials.CHAT_ID);

        // Retorna status OK com a mensagem formatada no corpo da resposta da requisição a API
        return ResponseEntity.status(HttpStatus.OK).body(messageFormated);
    }

    // Futura importação de lógica para endpoint de recebimento de pings
    @PostMapping(path = "/ping/send")
    public ResponseEntity<String> PingReceived (@RequestBody String ping) {

        // Implementar lógica

        return null;
    }
}