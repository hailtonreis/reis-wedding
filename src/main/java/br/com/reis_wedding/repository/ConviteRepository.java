package br.com.reis_wedding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import br.com.reis_wedding.entity.Convite;

public interface ConviteRepository extends JpaRepository<Convite, Long>{

	List<Convite> findByCasamentoId(Long casamentoId);
	
	Optional<Convite> findByToken(String token);
}
