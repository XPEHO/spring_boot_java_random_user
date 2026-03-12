package com.xpeho.spring_boot_java_random_user.data.models.api.randomuser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RandomUserResponseDTOTest {
    @Test
    @DisplayName("Should store and return results list")
    void shouldStoreAndReturnResultsList() {
        RandomUserResponseDTO response = new RandomUserResponseDTO();
        List<RandomUserResultDTO> results = List.of(new RandomUserResultDTO(), new RandomUserResultDTO());
        response.setResults(results);

        assertEquals(results, response.getResults());
    }

    @Test
    @DisplayName("Should have null results list by default")
    void shouldHaveNullResultsListByDefault() {
        RandomUserResponseDTO response = new RandomUserResponseDTO();

        assertNull(response.getResults());
    }
}

