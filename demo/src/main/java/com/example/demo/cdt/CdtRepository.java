package com.example.demo.cdt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdtRepository
        extends JpaRepository<Cdt, Long> {

}
