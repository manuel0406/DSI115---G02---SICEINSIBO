package com.dsi.insibo.sice.Seguridad.ClasesDeSeguridad;

import java.util.regex.Pattern;

public class PasswordValidationResponse {
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]");

    private int criteriaMetCount;
    private boolean tamaño;
    private boolean mayuscula;
    private boolean minuscula;
    private boolean numero;
    private boolean especial;
    private int passwordStrength;
    private String passwordColor;
    private String passwordMessage;

    public String getPasswordMessage() {
        return passwordMessage;
    }

    public void setPasswordMessage(String passwordMessage) {
        this.passwordMessage = passwordMessage;
    }

    public String getPasswordColor() {
        return passwordColor;
    }

    public int getPasswordStrength() {
        return passwordStrength;
    }

    public int getCriteriaMetCount() {
        return criteriaMetCount;
    }

    public boolean isTamaño() {
        return tamaño;
    }

    public boolean isMayuscula() {
        return mayuscula;
    }

    public boolean isMinuscula() {
        return minuscula;
    }

    public boolean isNumero() {
        return numero;
    }

    public boolean isEspecial() {
        return especial;
    }

    public PasswordValidationResponse(String password) {
        // Validación individual para cada criterio
        this.tamaño = password.length() >= 8;
        this.mayuscula = UPPERCASE_PATTERN.matcher(password).find();
        this.minuscula = LOWERCASE_PATTERN.matcher(password).find();
        this.numero = NUMBER_PATTERN.matcher(password).find();
        this.especial = SPECIAL_CHAR_PATTERN.matcher(password).find();

        // Contar criterios cumplidos
        this.criteriaMetCount = (tamaño ? 1 : 0) + (mayuscula ? 1 : 0) +
                                (minuscula ? 1 : 0) + (numero ? 1 : 0) +
                                (especial ? 1 : 0);

        // Calcular fuerza de la contraseña como porcentaje
        this.passwordStrength = (criteriaMetCount * 100) / 5;

        // Asignar color basado en la fuerza
        this.passwordColor = passwordStrength < 50 ? "bg-danger" :
                             passwordStrength < 75 ? "bg-warning" :
                             passwordStrength < 100 ? "bg-info" :
                             "bg-success";
        // Asignar color de texto baso en la fuerza
        this.passwordMessage = passwordStrength < 50 ? "Débil" :
                               passwordStrength < 75 ? "Moderada" :
                               passwordStrength < 100 ? "Buena" :
                               "Fuerte";
    }
}

