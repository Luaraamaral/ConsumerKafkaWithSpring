package br.com.luara.consumidorkafka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @SequenceGenerator(name = "pedido_seq", sequenceName = "pedido_seq", allocationSize = 1)
    @Column(length = 50)
    private String codigo;
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Column(name = "valor")
    private BigDecimal valor;

}