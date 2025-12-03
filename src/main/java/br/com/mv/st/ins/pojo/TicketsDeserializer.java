package br.com.mv.st.ins.pojo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketsDeserializer extends JsonDeserializer<List<Ticket>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public List<Ticket> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        // Debug opcional para confirmar que o deserializador está sendo chamado
        System.out.println("TicketsDeserializer chamado. node = " + node);

        if (node.isArray()) {
            // tickets já é um array JSON
            return MAPPER.convertValue(node, new TypeReference<List<Ticket>>() {});
        }

        if (node.isTextual()) {
            String text = node.asText();
            if (text.isBlank()) {
                return List.of();
            }
            // tentar parsear a string como array JSON
            try {
                return MAPPER.readValue(text, new TypeReference<List<Ticket>>() {});
            } catch (Exception e) {
                // fallback: tentar como um único objeto Ticket
                try {
                    Ticket t = MAPPER.readValue(text, Ticket.class);
                    List<Ticket> list = new ArrayList<>();
                    list.add(t);
                    return list;
                } catch (Exception ex) {
                    // em caso de falha, retorna lista vazia para não quebrar a requisição
                    return List.of();
                }
            }
        }

        if (node.isObject()) {
            // objeto único -> lista com um Ticket
            try {
                Ticket t = MAPPER.convertValue(node, Ticket.class);
                List<Ticket> list = new ArrayList<>();
                list.add(t);
                return list;
            } catch (Exception e) {
                return List.of();
            }
        }

        return List.of();
    }
}