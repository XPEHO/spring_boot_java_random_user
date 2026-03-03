package com.xpeho.spring_boot_java_random_user.presentation.controllers;

import org.springframework.http.ResponseEntity;
import java.util.List;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/random-users")
@Tag(name = "User", description = "Endpoints for random user generation")
public interface UserController {
    
    @GetMapping("")
    @Operation(
        summary = "Get random users",
        description = "Fetches a list of random users from the external API and saves them to the database.",
        parameters = {
            @Parameter(name = "count", description = "Number of users to generate (max 5000)", example = "500")
        }
    )
    @ApiResponse(responseCode = "200", description = "List of users successfully fetched and saved")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "503", description = "External service unavailable")
    ResponseEntity<List<UserEntity>> getRandomUsers(
        @RequestParam(defaultValue = "500")
        @Min(1)
        @Max(5000)
        int count
    );
}
