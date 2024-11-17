package com.factoriaf5.kata;

public class Character {

    private static final double SALUD_MAXIMA = 1000;
    private static final double RANGO_MAXIMO_MELEE = 2;
    private static final double RANGO_MAXIMO_RANGED = 20;
    private static final double RANGO_MAXIMO_POR_DEFECTO = 1000000;

    private double salud = SALUD_MAXIMA;
    private int nivel = 1;
    private boolean vivo = true;
    private String tipoLuchador;
    private double rangoMaximoAtaque;

    public Character() {
    }

    public Character(double salud, int nivel, boolean vivo, String tipoLuchador) {
        this.salud = salud;
        this.nivel = nivel;
        this.vivo = vivo;
        this.tipoLuchador = tipoLuchador;
        this.rangoMaximoAtaque = determinarRangoMaximoAtaque(tipoLuchador);
    }

    public double getSalud() {
        return salud;
    }

    public void setSalud(double salud) {
        this.salud = salud;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public String getTipoLuchador() {
        return tipoLuchador;
    }

    public void setTipoLuchador(String tipoLuchador) {
        this.tipoLuchador = tipoLuchador;
        this.rangoMaximoAtaque = determinarRangoMaximoAtaque(tipoLuchador);
    }

    public double getRangoMaximoAtaque() {
        return rangoMaximoAtaque;
    }

    private double determinarRangoMaximoAtaque(String tipoLuchador) {
        switch (tipoLuchador) {
            case "meleeFighter":
                return RANGO_MAXIMO_MELEE;
            case "rangedFighter":
                return RANGO_MAXIMO_RANGED;
            default:
                return RANGO_MAXIMO_POR_DEFECTO;
        }
    }

    public void hacerDanio(double danio, Character atacante, Character objetivo, double distancia) {
        validarRangoDeAtaque(distancia, atacante);

        if (objetivo == atacante) {
            throw new IllegalArgumentException("Un personaje no puede hacer daño a sí mismo.");
        }

        double danioFinal = calcularDanio(danio, atacante, objetivo);
        aplicarDanio(danioFinal, objetivo);
    }

    private void validarRangoDeAtaque(double distancia, Character atacante) {
        if (distancia > atacante.rangoMaximoAtaque) {
            throw new IllegalArgumentException("El personaje no puede atacar porque está demasiado lejos de su objetivo.");
        }
    }

    private double calcularDanio(double danio, Character atacante, Character objetivo) {
        if (objetivo.nivel - atacante.nivel >= 5) {
            return danio * 0.5;
        } else if (atacante.nivel - objetivo.nivel >= 5) {
            return danio * 1.5;
        } else {
            return danio;
        }
    }

    private void aplicarDanio(double danio, Character objetivo) {
        if (objetivo.salud <= danio) {
            objetivo.salud = 0;
            objetivo.vivo = false;
        } else {
            objetivo.salud -= danio;
        }
    }

    public void curar(double puntosDeCuracion, Character personaje) {
        if (!personaje.vivo) {
            throw new IllegalStateException("No se puede curar a un personaje muerto.");
        }

        if (puntosDeCuracion <= 0) {
            throw new IllegalArgumentException("Los puntos de curación deben ser positivos.");
        }

        personaje.salud = Math.min(personaje.salud + puntosDeCuracion, SALUD_MAXIMA);
    }
}
