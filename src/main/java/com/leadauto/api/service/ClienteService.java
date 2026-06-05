package com.leadauto.api.service;

import com.leadauto.api.dto.request.ClienteRequest;
import com.leadauto.api.dto.response.ClienteResponse;
import com.leadauto.api.exception.BusinessException;
import com.leadauto.api.exception.ResourceNotFoundException;
import com.leadauto.api.model.entity.Cliente;
import com.leadauto.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse criar(ClienteRequest request) {
        if (clienteRepository.existsByTelefone(request.telefone())) {
            throw new BusinessException("Já existe um cliente com esse telefone.");
        }

        Cliente cliente = Cliente.builder()
                .nome(request.nome())
                .telefone(request.telefone())
                .email(request.email())
                .cpf(request.cpf())
                .build();

        return toResponse(clienteRepository.save(cliente));
    }

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = findById(id);

        // Verifica se o telefone já pertence a outro cliente
        clienteRepository.findByTelefone(request.telefone())
                .ifPresent(c -> {
                    if (!c.getId().equals(id)) {
                        throw new BusinessException("Telefone já cadastrado para outro cliente.");
                    }
                });

        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());
        cliente.setEmail(request.email());
        cliente.setCpf(request.cpf());

        return toResponse(clienteRepository.save(cliente));
    }

    public void deletar(Long id) {
        findById(id);
        clienteRepository.deleteById(id);
    }

    // Método auxiliar reutilizado internamente
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com id: " + id));
    }

    // Converte entidade para DTO de response
    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getCriadoEm()
        );
    }
}