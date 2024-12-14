package com.dsi.insibo.sice.Seguridad.ClasesDeSeguridad;

import java.util.regex.Pattern;

// Clase interna para encapsular la respuesta de validación
public  class PasswordValidationResponse {
    private int criteriaMetCount;
    private boolean tamaño;
    private boolean mayuscula;
    private boolean minuscula;
    private boolean numero;
    private boolean especial;

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
        this.mayuscula = Pattern.compile("[A-Z]").matcher(password).find();
        this.minuscula = Pattern.compile("[a-z]").matcher(password).find();
        this.numero = Pattern.compile("[0-9]").matcher(password).find();
        this.especial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();

        this.criteriaMetCount = (tamaño ? 1 : 0) + (mayuscula ? 1 : 0) + (minuscula ? 1 : 0) + 
                               (numero ? 1 : 0) + (especial ? 1 : 0);
    }
}
