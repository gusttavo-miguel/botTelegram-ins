package br.com.mv.st.ins.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Data {
    private String title;
    private String body;
    private String android_channel_id;
    private String tag;
    private String deviceId;

    // Pode vir como String com JSON, lista ou mesmo objeto
    private Object tickets;

    @JsonIgnore
    public List<Ticket> getTicketsAsList() {
        if (tickets == null) {
            return List.of();
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            if (tickets instanceof List<?>) {
                // já é uma lista de mapas/objetos -> converter
                return mapper.convertValue(tickets, mapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));
            }

            if (tickets instanceof String s) {
                if (s.isBlank()) {
                    return List.of();
                }
                try {
                    // tentar como array JSON
                    return mapper.readValue(s, mapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));
                } catch (Exception e) {
                    // tentar como objeto único
                    Ticket t = mapper.readValue(s, Ticket.class);
                    List<Ticket> list = new ArrayList<>();
                    list.add(t);
                    return list;
                }
            }

            // fallback genérico (por exemplo, objeto único)
            Ticket t = mapper.convertValue(tickets, Ticket.class);
            List<Ticket> list = new ArrayList<>();
            list.add(t);
            return list;
        } catch (Exception e) {
            return List.of();
        }
    }
}