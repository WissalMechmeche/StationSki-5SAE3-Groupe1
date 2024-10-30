package com.example.gestionstationskii.services;
import com.example.gestionstationskii.DTO.PisteResponseDTO;
import com.example.gestionstationskii.entities.*;
import java.util.List;

public interface IPisteServices {

    List<Piste> retrieveAllPistes();

    Piste  addPiste(Piste  piste);

    void removePiste (Long numPiste);

    Piste retrievePiste (Long numPiste);
    PisteResponseDTO getPistesByColor(Color color);
}
