package com.leadauto.api.service;

import com.leadauto.api.dto.request.ServicoRequest;
import com.leadauto.api.dto.response.ClienteResponse;
import com.leadauto.api.dto.response.PecaResponse;
import com.leadauto.api.dto.response.ServicoResponse;
import com.leadauto.api.exception.BusinessException;
import com.leadauto.api.exception.ResourceNotFoundException;
import com.leadauto.api.model.entity.Peca;
import com.leadauto.api.model.entity.Servico;
import com.leadauto.api.model.entity.StatusServico;
import com.leadauto.api.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ClienteService clienteService;
    private final PecaService pecaService;

    public ServicoResponse criar(ServicoRequest request) {
        var cliente = clienteService.findById(request.clienteId());

        List<Peca> pecas = request.pecaIds() != null
                ? request.pecaIds().stream()
                .map(pecaService::findById)
                .toList()
                : List.of();

        Servico servico = Servico.builder()
                .cliente(cliente)
                .pecas(pecas)
                .descricao(request.descricao())
                .valorMaoObra(request.valorMaoObra())
                .build();

        return toResponse(servicoRepository.save(servico));
    }

    public List<ServicoResponse> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ServicoResponse> listarPorCliente(Long clienteId) {
        return servicoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ServicoResponse> listarPorStatus(StatusServico status) {
        return servicoRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServicoResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    public ServicoResponse atualizarStatus(Long id, StatusServico novoStatus) {
        Servico servico = findById(id);

        // Regra de negócio — não pode voltar pra ABERTO se já estiver CONCLUIDO
        if (servico.getStatus() == StatusServico.CONCLUIDO) {
            throw new BusinessException("Serviço já concluído não pode ter status alterado.");
        }

        servico.setStatus(novoStatus);
        return toResponse(servicoRepository.save(servico));
    }

    public void deletar(Long id) {
        findById(id);
        servicoRepository.deleteById(id);
    }

    public Servico findById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Serviço não encontrado com id: " + id));
    }

    private ServicoResponse toResponse(Servico servico) {
        ClienteResponse clienteResponse = new ClienteResponse(
                servico.getCliente().getId(),
                servico.getCliente().getNome(),
                servico.getCliente().getTelefone(),
                servico.getCliente().getEmail(),
                servico.getCliente().getCpf(),
                servico.getCliente().getCriadoEm()
        );

        List<PecaResponse> pecasResponse = servico.getPecas() != null
                ? servico.getPecas().stream()
                .map(p -> new PecaResponse(
                        p.getId(),
                        p.getNome(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getEstoque(),
                        p.getFabricante()
                ))
                .toList()
                : List.of();

        return new ServicoResponse(
                servico.getId(),
                clienteResponse,
                pecasResponse,
                servico.getDescricao(),
                servico.getValorMaoObra(),
                servico.getStatus(),
                servico.getCriadoEm(),
                servico.getAtualizadoEm()
        );
    }
}