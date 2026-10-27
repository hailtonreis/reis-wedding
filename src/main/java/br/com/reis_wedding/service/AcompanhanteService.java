package br.com.reis_wedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.reis_wedding.dto.AcompanhanteResponseDTO;
import br.com.reis_wedding.entity.Acompanhante;
import br.com.reis_wedding.entity.Convite;
import br.com.reis_wedding.exception.RegraNegocioException;
import br.com.reis_wedding.repository.AcompanhanteRepository;
import br.com.reis_wedding.repository.ConviteRepository;

@Service
public class AcompanhanteService {

    private final AcompanhanteRepository acompanhanteRepository;
    private final ConviteRepository conviteRepository;

    public AcompanhanteService(
            AcompanhanteRepository acompanhanteRepository,
            ConviteRepository conviteRepository) {

        this.acompanhanteRepository = acompanhanteRepository;
        this.conviteRepository = conviteRepository;
    }

    public Acompanhante salvar(Long conviteId, Acompanhante acompanhante) {

        Convite convite = conviteRepository.findById(conviteId)
                .orElseThrow(() ->
                        new RuntimeException("Convite não encontrado"));

        long quantidadeAtual =
                acompanhanteRepository.countByConviteId(conviteId);

        if (quantidadeAtual >= convite.getLimiteAcompanhantes()) {
            throw new RegraNegocioException(
                    "Limite de acompanhantes atingido para este convite"
            );
        }

        acompanhante.setConvite(convite);

        return acompanhanteRepository.save(acompanhante);
    }

    public List<Acompanhante> listarTodos() {
        return acompanhanteRepository.findAll();
    }

    public List<AcompanhanteResponseDTO> listarPorConvite(Long conviteId) {

        return acompanhanteRepository.findByConviteId(conviteId)
                .stream()
                .map(acompanhante -> {

                    AcompanhanteResponseDTO dto =
                            new AcompanhanteResponseDTO();

                    dto.setId(acompanhante.getId());
                    dto.setConviteId(acompanhante.getConvite().getId());
                    dto.setNome(acompanhante.getNome());
                    dto.setCreatedAt(acompanhante.getCreatedAt());

                    return dto;
                })
                .toList();
    }
}