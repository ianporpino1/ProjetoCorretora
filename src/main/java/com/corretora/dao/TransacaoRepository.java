package com.corretora.dao;


import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query(value = "SELECT ticker, SUM(quantidade), TRUNCATE(AVG(preco),2), TRUNCATE(SUM(total),2) FROM corretoradb.transacao GROUP BY ticker",nativeQuery = true)
    List<Object[]> calcularResumoTransacoes();
}
