package br.com.mv.st.ins.utils;

import br.com.mv.st.ins.pojo.Ticket;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.List;

public class FormatMessage {

    public String formattedMessage(List<Ticket> ticketsList) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ticketsList.size(); i++) {
            Ticket ticket = ticketsList.get(i);

            StringFormattedMessage formattedMessage = new StringFormattedMessage(
                    "\uD83D\uDEA8 ALERTA \uD83D\uDEA8\n\n" +
                            "====== Novo ticket crítico! ======\n" +
                            "Ticket ID: %s\n" +
                            "SLA: %s\n" +
                            "Product: %s\n" +
                            "Status: %s\n" +
                            "Cliente: %s\n" +
                            "Data de abertura: %s\n" +
                            "Ticket URL: %s\n",
                    ticket.getTicketId(),
                    ticket.getTicketSlaPassed(),
                    ticket.getTicketProduct(),
                    ticket.getTicketState(),
                    ticket.getTicketClient(),
                    ticket.getTicketDateOpened(),
                    ticket.getTicketURL()
            );

            builder.append(formattedMessage);

            // Se não for o último, adiciona separador
            if (i < ticketsList.size() - 1) {
                builder.append("\n\n-----------------\n\n");
            }
        }

        return builder.toString();
    }
}