package br.com.mv.st.ins.controller;

import br.com.mv.st.ins.pojo.Ticket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bot")
public class BotController {

    @PostMapping(path = "/tickets/send")
    public void ticketsSend (Ticket ticket) {

    }
}
