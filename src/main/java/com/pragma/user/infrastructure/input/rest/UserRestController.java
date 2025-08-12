package com.pragma.user.infrastructure.input.rest;

import com.pragma.user.application.dto.request.EmployeeRequestDto;
import com.pragma.user.application.dto.request.UserRequestDto;
import com.pragma.user.application.dto.response.UserResponseDto;
import com.pragma.user.application.handler.IUserHandler;
import com.pragma.user.infrastructure.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserRestController {

    private final IUserHandler userHandler;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Add a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Object created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Object already exists", content = @Content)
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/owner")
    public ResponseEntity<Void> saveOwner(@RequestBody UserRequestDto dto) {
        userHandler.saveOwner(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "employee created", content = @Content),
            @ApiResponse(responseCode = "409", description = "employee already exists", content = @Content)
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/employee")
    public ResponseEntity<Void> saveEmployee(@RequestBody EmployeeRequestDto dto) {

        userHandler.saveEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Add a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "client created", content = @Content),
            @ApiResponse(responseCode = "409", description = "client already exists", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<Void> saveClient(@RequestBody EmployeeRequestDto dto) {

        userHandler.saveClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userHandler.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userHandler.getUserById(id);
        return ResponseEntity.ok(user);
    }


}