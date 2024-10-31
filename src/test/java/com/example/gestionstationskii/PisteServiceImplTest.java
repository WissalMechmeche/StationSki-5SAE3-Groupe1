package com.example.gestionstationskii;

import com.example.gestionstationskii.DTO.PisteResponseDTO;
import com.example.gestionstationskii.entities.Color;
import com.example.gestionstationskii.entities.Piste;
import com.example.gestionstationskii.repositories.IPisteRepository;
import com.example.gestionstationskii.services.PisteServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class PisteServiceImplTest {


    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllPistes() {
        Piste piste1 = new Piste();
        piste1.setNumPiste(1L);
        piste1.setNamePiste("Piste 1");
        piste1.setLength(150);

        Piste piste2 = new Piste();
        piste2.setNumPiste(2L);
        piste2.setNamePiste("Piste 2");
        piste2.setLength(200);


        when(pisteRepository.findAll()).thenReturn(Arrays.asList(piste1, piste2));

        List<Piste> pistes = pisteServices.retrieveAllPistes();

        assertEquals(2, pistes.size());
        assertEquals("Piste 1", pistes.get(0).getNamePiste());
        assertEquals("Piste 2", pistes.get(1).getNamePiste());
    }

    @Test
    public void testAddPiste() {
        Piste piste = new Piste(null, "Piste 3", Color.BLUE, 180, 30, null); // Utilisez le constructeur avec les bonnes valeurs
        when(pisteRepository.save(piste)).thenReturn(piste);

        Piste createdPiste = pisteServices.addPiste(piste);

        assertNotNull(createdPiste);
        assertEquals("Piste 3", createdPiste.getNamePiste());
    }
    @Test
    public void testGetPistesByColor() {
        Piste piste1 = new Piste(null, "Piste 1", Color.BLUE, 150, 30, null);
        Piste piste2 = new Piste(null, "Piste 2", Color.BLUE, 200, 20, null);
        Piste piste3 = new Piste(null, "Piste 3", Color.RED, 100, 10, null);

        when(pisteRepository.findByColor(Color.BLUE)).thenReturn(Arrays.asList(piste1, piste2));

        PisteResponseDTO response = pisteServices.getPistesByColor(Color.BLUE);

        assertEquals(2, response.getTotal());
        assertEquals("Nom: Piste 1, Longueur: 150 mètres", response.getPistesDetails().get(0));
        assertEquals("Nom: Piste 2, Longueur: 200 mètres", response.getPistesDetails().get(1));
    }

}