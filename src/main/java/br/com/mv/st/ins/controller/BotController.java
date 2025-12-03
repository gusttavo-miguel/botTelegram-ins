package br.com.mv.st.ins.controller;

import br.com.mv.st.ins.bot.BotCredentials;
import br.com.mv.st.ins.bot.TelegramBot;
import br.com.mv.st.ins.pojo.Data;
import br.com.mv.st.ins.pojo.MessageWrapper;
import br.com.mv.st.ins.pojo.Ticket;
import br.com.mv.st.ins.utils.FilterAndFormatMessage;
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

        Data data = wrapper.getMessage().getData();
        List<Ticket> ticketsList = data.getTicketsAsList();

        if (ticketsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        FilterAndFormatMessage filter = new FilterAndFormatMessage();
        String mensageFormated = filter.filterAndFormat(ticketsList);

        TelegramBot bot = new TelegramBot();
        bot.sendMessage(String.valueOf(mensageFormated), BotCredentials.CHAT_ID);

        return ResponseEntity.status(HttpStatus.CREATED).body(mensageFormated);
    }
}