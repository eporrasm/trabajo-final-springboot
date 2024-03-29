package com.example.demo.repositories;

import com.example.demo.entities.Cdt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CdtRepository
        extends JpaRepository<Cdt, Long> {
    @Query("SELECT SUM(c.valor) FROM Cdt c")
    Long getValorTotalCdts();
}
