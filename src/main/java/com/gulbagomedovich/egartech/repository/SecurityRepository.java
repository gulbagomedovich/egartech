package com.gulbagomedovich.egartech.repository;

import com.gulbagomedovich.egartech.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    Security getSecurityById(Long id);

}
