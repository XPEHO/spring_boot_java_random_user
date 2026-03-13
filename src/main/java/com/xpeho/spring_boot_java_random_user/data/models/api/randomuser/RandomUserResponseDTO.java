package com.xpeho.spring_boot_java_random_user.data.models.api.randomuser;

import java.util.List;

public class RandomUserResponseDTO {
    private List<RandomUserResultDTO> results;

    public List<RandomUserResultDTO> getResults() {
        return results;
    }

    public void setResults(List<RandomUserResultDTO> results) {
        this.results = results;
    }
}

