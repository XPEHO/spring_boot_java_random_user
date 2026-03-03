package com.xpeho.spring_boot_java_random_user.presentation.controllers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by id",
            description = "Given a user by id, return the user if it exists in the database",
            parameters = {
                    @Parameter(name = "id", description = "id of the requested user")
            }
    )
    @ApiResponse(responseCode = "200", description = "User successfully found and returned")
    @ApiResponse(responseCode = "404", description = "The requested user does not exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    ResponseEntity<UserEntity> getUserById(
            @PathVariable
            int id
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Modify a random user",
            description = "Giving a random user id, modify the content of the user",
            parameters = {
                    @Parameter(name = "id", description = "id of the requested user"),
                    @Parameter(name= "UserEntity", description = "changeable parameters")
            }
    )
    @ApiResponse(responseCode = "200", description = "User successfully modified and saved")
    @ApiResponse(responseCode = "404", description = "The requested user does not exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    ResponseEntity<UserEntity> updateRandomUser(
            @PathVariable
            int id,
            @RequestBody
            UserRequest user
    );

    @PostMapping("")
    @Operation(
        summary = "Create a user",
        description = "Creates a new user in the database.",
        parameters = {
            @Parameter(name = "UserRequest", description = "User data to persist")
        }
    )
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    ResponseEntity<UserEntity> createUser(@RequestBody UserRequest user);
}
