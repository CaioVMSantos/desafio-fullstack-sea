package com.sea.desafiobackend.repository;

import com.sea.desafiobackend.domain.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
