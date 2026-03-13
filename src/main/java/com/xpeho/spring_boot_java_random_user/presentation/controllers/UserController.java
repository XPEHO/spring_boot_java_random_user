package com.xpeho.spring_boot_java_random_user.presentation.controllers;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import com.xpeho.spring_boot_java_random_user.domain.enums.Gender;
import com.xpeho.spring_boot_java_random_user.domain.enums.UserSource;
import com.xpeho.spring_boot_java_random_user.presentation.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/random-users")
@Tag(name = "User", description = "Endpoints for random user generation")
public interface UserController {

    @GetMapping("")
    @Operation(
            summary = "Get random users",
            description = "Fetches a paginated list of random users from the external API and saves them to the database.",
            parameters = {
                    @Parameter(name = "page", description = "Page number", example = "1"),
                    @Parameter(name = "size", description = "Number of users per page (max 30)", example = "10"),
                    @Parameter(name = "source", description = "External source to use", example = "DUMMY")
            }
    )
    @ApiResponse(responseCode = "200", description = "List of users successfully fetched and saved")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "503", description = "External service unavailable")
    ResponseEntity<UserResponseDTO> getRandomUsers(
            @RequestParam(defaultValue = "1")
            @Min(1)
            int page,
            @RequestParam(defaultValue = "10")
            @Min(1)
            @Max(30)
            int size,
            @RequestParam(defaultValue = "DUMMY")
            UserSource source
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
                    @Parameter(name = "UserEntity", description = "changeable parameters")
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


    @GetMapping("/filter")
    @Operation(
            summary = "Filter users",
            description = "Search users by optional filters on gender, firstname, lastname, civility, email, phone and nationality. All filters are case-insensitive and support partial matching.",
            parameters = {
                    @Parameter(name = "gender", description = "Filter by gender (MALE or FEMALE)"),
                    @Parameter(name = "firstname", description = "Filter by firstname"),
                    @Parameter(name = "lastname", description = "Filter by lastname"),
                    @Parameter(name = "civility", description = "Filter by civility"),
                    @Parameter(name = "email", description = "Filter by email"),
                    @Parameter(name = "phone", description = "Filter by phone"),
                    @Parameter(name = "nat", description = "Filter by nationality")
            }
    )
    @ApiResponse(responseCode = "200", description = "Filtered list of users")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    ResponseEntity<List<UserEntity>> filterUsers(
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String civility,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String nat
    );

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user by id",
            description = "Given a user by id, delete the user if it exists in the database",
            parameters = {
                    @Parameter(name = "id", description = "id of the requested user")
            }
    )
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "The requested user does not exist")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    void deleteUserById(
            @PathVariable
            int id
    );
}
