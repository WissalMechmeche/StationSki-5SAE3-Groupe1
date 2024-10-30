package com.example.gestionstationskii.DTO;

import java.util.List;

public class PisteResponseDTO
{
    private int total;
    private List<String> pistesDetails;

    public PisteResponseDTO(int total, List<String> pistesDetails) {
        this.total = total;
        this.pistesDetails = pistesDetails;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getPistesDetails() {
        return pistesDetails;
    }

    public void setPistesDetails(List<String> pistesDetails) {
        this.pistesDetails = pistesDetails;
    }
}

