package com.leadauto.api.repository;

import com.leadauto.api.model.entity.Servico;
import com.leadauto.api.model.entity.StatusServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByClienteId(Long clienteId);
    List<Servico> findByStatus(StatusServico status);
}