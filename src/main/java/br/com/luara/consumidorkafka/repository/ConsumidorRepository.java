package br.com.luara.consumidorkafka.repository;

import br.com.luara.consumidorkafka.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumidorRepository extends JpaRepository<Pedido, String> {
}
