package com.leadauto.api.service;

import com.leadauto.api.dto.request.PecaRequest;
import com.leadauto.api.dto.response.PecaResponse;
import com.leadauto.api.exception.ResourceNotFoundException;
import com.leadauto.api.model.entity.Peca;
import com.leadauto.api.repository.PecaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaResponse criar(PecaRequest request) {
        Peca peca = Peca.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .preco(request.preco())
                .estoque(request.estoque())
                .fabricante(request.fabricante())
                .build();

        return toResponse(pecaRepository.save(peca));
    }

    public List<PecaResponse> listarTodos() {
        return pecaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PecaResponse> buscarPorNome(String nome) {
        return pecaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PecaResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    public PecaResponse atualizar(Long id, PecaRequest request) {
        Peca peca = findById(id);

        peca.setNome(request.nome());
        peca.setDescricao(request.descricao());
        peca.setPreco(request.preco());
        peca.setEstoque(request.estoque());
        peca.setFabricante(request.fabricante());

        return toResponse(pecaRepository.save(peca));
    }

    public void deletar(Long id) {
        findById(id);
        pecaRepository.deleteById(id);
    }

    public Peca findById(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Peça não encontrada com id: " + id));
    }

    private PecaResponse toResponse(Peca peca) {
        return new PecaResponse(
                peca.getId(),
                peca.getNome(),
                peca.getDescricao(),
                peca.getPreco(),
                peca.getEstoque(),
                peca.getFabricante()
        );
    }
}