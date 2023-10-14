package com.corretora.dao;

import com.corretora.model.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, Long> {

    @Query(value = "SELECT ticker FROM corretoradb.posicao WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<String> findTickers(@Param("idUsuario")Long idUsuario);
    @Query(value = "SELECT * FROM corretoradb.posicao WHERE ticker = :ticker AND id_usuario = :idUsuario",nativeQuery = true)
    Posicao findPosicaoByTicker(@Param("ticker") String ticker,@Param("idUsuario")Long idUsuario);

    @Query(value = "SELECT ticker, TRUNCATE(preco_medio,2), quantidade_total, TRUNCATE(valor_total,2) FROM corretoradb.posicao WHERE id_usuario = :idUsuario",nativeQuery = true)
    List<Object[]> findFormattedPosicoes(@Param("idUsuario")Long idUsuario);
    @Modifying
    @Query(value = "delete FROM corretoradb.posicao WHERE ticker = :ticker AND id_usuario = :idUsuario", nativeQuery = true)
    int deleteByTicker(@Param("ticker") String ticker, @Param("idUsuario")Long idUsuario);




}
