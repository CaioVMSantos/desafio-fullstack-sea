package com.sea.desafiobackend.repository;

import com.sea.desafiobackend.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
