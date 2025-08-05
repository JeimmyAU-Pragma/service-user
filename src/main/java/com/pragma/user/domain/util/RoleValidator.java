package com.pragma.user.domain.util;



import com.pragma.user.domain.model.Rol;
import com.pragma.user.domain.exception.UnauthorizedException;

public class RoleValidator {
    public static void validateRole(String actual, Rol expected) {
        if (!actual.equals(expected.name())) {
            throw new UnauthorizedException("Rol no autorizado para esta operación.");
        }
    }
}
