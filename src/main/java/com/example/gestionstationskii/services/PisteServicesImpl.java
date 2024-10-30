package com.example.gestionstationskii.services;

import com.example.gestionstationskii.DTO.PisteResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gestionstationskii.entities.*;
import com.example.gestionstationskii.repositories.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PisteServicesImpl implements  IPisteServices {

    private IPisteRepository pisteRepository;

    @Override
    public List<Piste> retrieveAllPistes() {
        return pisteRepository.findAll();
    }

    @Override
    public Piste addPiste(Piste piste) {
        return pisteRepository.save(piste);
    }

    @Override
    public void removePiste(Long numPiste) {
        pisteRepository.deleteById(numPiste);
    }

    @Override
    public Piste retrievePiste(Long numPiste) {
        return pisteRepository.findById(numPiste).orElse(null);
    }

    @Override
    public PisteResponseDTO getPistesByColor(Color color) {
        List<Piste> pistes = pisteRepository.findByColor(color);
        List<String> pistesDetails = pistes.stream()
                .map(p -> "Nom: " + p.getNamePiste() + ", Longueur: " + p.getLength() + " mètres")
                .collect(Collectors.toList());
        int total = pistesDetails.size();
        return new PisteResponseDTO(total, pistesDetails);
    }
}