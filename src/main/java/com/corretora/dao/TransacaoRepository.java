package com.corretora.dao;


import com.corretora.dto.TransacaoResumo;
import com.corretora.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query(value = "SELECT ticker, quantidade, TRUNCATE(preco,2), TRUNCATE(total_transacao,2), tipo_transacao, data FROM corretoradb.transacao WHERE id_usuario = :idUsuario ",nativeQuery = true)
    List<Object[]> calcularResumoTransacoes(@Param("idUsuario")Long idUsuario);

    @Query(value = "SELECT distinct ticker FROM corretoradb.transacao",nativeQuery = true)
    List<String> findTickers();
}
