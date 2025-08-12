package com.pragma.user.domain.util;

public class DomainConstants {
    private DomainConstants() {
    }

    public static final String ROLE_ADMIN = "ADMINISTRADOR";
    public static final String ROLE_OWNER = "PROPIETARIO";
    public static final String ROLE_EMPLOYEE = "EMPLEADO";
    public static final String ROLE_CLIENT = "CLIENTE";
    public static final String ROLE_NOT_FOUND = "el Rol no existe";
    public static final String MESSAGE_ERROR_USER = "el Rol no existe";


    public static final String INVALID_DOCUMENT = "Documento debe ser numérico";
    public static final String INVALID_EMAIL = "Correo inválido";
    public static final String INVALID_PHONE_NUMBER = "Teléfono inválido";
    public static final String EMAIL_ALREADY_EXISTS = "El correo ya existe.";
    public static final String DOCUMENT_ALREADY_EXISTS = "El documento ya existe.";

    public static final String INVALID_CREDENTIALS = "Credenciales inválidas";
    public static final String USER_NOT_FOUND = "Usuario no fue encontrado: ";

    public static final String MESSAGE_ADULT = "Debe ser mayor de edad";


}
