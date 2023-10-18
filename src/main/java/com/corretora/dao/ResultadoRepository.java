package com.corretora.dao;


import com.corretora.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    @Query(value = "SELECT ativo, TRUNCATE(resultado,2), TRUNCATE(resultado_porcentagem,2) FROM corretoradb.resultado WHERE MONTH(data) = :mes AND YEAR(data) = :ano AND id_usuario = :idUsuario",nativeQuery = true)
    List<Object[]> findResultadoByData(@Param("mes") int mes,@Param("ano") int ano, @Param("idUsuario")Long idUsuario);
}
