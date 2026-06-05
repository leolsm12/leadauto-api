package com.leadauto.api.controller;

import com.leadauto.api.dto.request.PecaRequest;
import com.leadauto.api.dto.response.PecaResponse;
import com.leadauto.api.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pecas")
@RequiredArgsConstructor
@Tag(name = "Peças", description = "Gerenciamento de peças")
public class PecaController {

    private final PecaService pecaService;

    @PostMapping
    @Operation(summary = "Cadastrar nova peça")
    public ResponseEntity<PecaResponse> criar(@RequestBody @Valid PecaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar todas as peças")
    public ResponseEntity<List<PecaResponse>> listarTodos() {
        return ResponseEntity.ok(pecaService.listarTodos());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar peças por nome")
    public ResponseEntity<List<PecaResponse>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(pecaService.buscarPorNome(nome));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar peça por ID")
    public ResponseEntity<PecaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pecaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar peça")
    public ResponseEntity<PecaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PecaRequest request) {
        return ResponseEntity.ok(pecaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar peça")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pecaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}