package br.com.reis_wedding.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reis_wedding.entity.Acompanhante;

public interface AcompanhanteRepository extends JpaRepository<Acompanhante, Long>{

	 List<Acompanhante> findByConviteId(Long conviteId);
	 
	 long countByConviteId(Long conviteId);
}
