package com.leadauto.api.controller;

import com.leadauto.api.dto.request.ServicoRequest;
import com.leadauto.api.dto.response.ServicoResponse;
import com.leadauto.api.model.entity.StatusServico;
import com.leadauto.api.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento de serviços da oficina")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    @Operation(summary = "Abrir novo serviço")
    public ResponseEntity<ServicoResponse> criar(@RequestBody @Valid ServicoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos os serviços")
    public ResponseEntity<List<ServicoResponse>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar serviços por cliente")
    public ResponseEntity<List<ServicoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(servicoService.listarPorCliente(clienteId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar serviços por status")
    public ResponseEntity<List<ServicoResponse>> listarPorStatus(@PathVariable StatusServico status) {
        return ResponseEntity.ok(servicoService.listarPorStatus(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do serviço")
    public ResponseEntity<ServicoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusServico status) {
        return ResponseEntity.ok(servicoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}