package br.com.luara.consumidorkafka.service;

import br.com.luara.consumidorkafka.model.Pedido;
import br.com.luara.consumidorkafka.repository.ConsumidorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConsumidorService {

    @Autowired
    private ConsumidorRepository consumidorRepository;

    @KafkaListener(topics = "SalvarPedido", groupId = "MicrosservicoSalvaPedido")
    private void salvarPedido(ConsumerRecord<String, String> record) {

        log.info("Chave = {} ", record.key());
        log.info("Cabeçalho = {} ", record.headers());
        log.info("Partição = {} ", record.partition());

        String stringDados = record.value();
        Pedido pedido;

        ObjectMapper mapper = new ObjectMapper();

        try {
            stringDados = stringDados.replace("Pedido(", "{")
                    .replaceAll("codigo=([^,]+),", "\"codigo\":\"$1\",")
                    .replaceAll("nomeProduto=([^,]+),", "\"nomeProduto\":\"$1\",")
                    .replaceAll("valor=([^,)]+)", "\"valor\":\"$1\"}");

            if (!stringDados.startsWith("{")) {
                stringDados = "{" + stringDados;
            }
            if (stringDados.endsWith(";")) {
                stringDados = stringDados.substring(0, stringDados.length() - 1);
            }
            System.out.println(stringDados);
            pedido = mapper.readValue(stringDados, Pedido.class);
        } catch (JsonProcessingException e) {
            log.error("Falha ao converter evento! " + stringDados, e);
            return;
        }


        log.info("Evento recebido = {} ", pedido);
        try {
        salvarNoBancoDeDados(pedido);
        log.info("Evento salvo no banco de dados!");
        } catch (Exception e) {
            log.error("Não foi possível salvar o evento no banco de dados!");
        }
    }

    private void salvarNoBancoDeDados(Pedido pedido) {
        consumidorRepository.save(pedido);
    }
}
