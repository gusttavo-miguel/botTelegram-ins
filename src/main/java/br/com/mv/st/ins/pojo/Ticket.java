package br.com.mv.st.ins.pojo;

import lombok.Data;

@Data
public class Ticket {
    private String ticketId;
    private String ticketSlaPassed;
    private String ticketProduct;
    private String ticketState;
    private String ticketClient;
    private String ticketDateOpened;
    private String ticketURL;
}