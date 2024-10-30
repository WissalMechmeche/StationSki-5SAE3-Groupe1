package com.example.gestionstationskii.repositories;

import com.example.gestionstationskii.entities.Color;
import com.example.gestionstationskii.entities.Piste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPisteRepository extends JpaRepository<Piste, Long> {
    List<Piste> findByColor(Color color);
}
