package com.factoriaf5.kata;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {

    private Character atacante;
    private Character objetivo;

    @BeforeEach
    void setUp() {
        // Configuración inicial para los tests
        atacante = new Character(1000, 1, true, "meleeFighter");
        objetivo = new Character(1000, 1, true, "rangedFighter");
    }

    @Test
    void testCrearPersonajeConTipoLuchadorMelee() {
        Character melee = new Character(1000, 1, true, "meleeFighter");
        assertThat(melee.getRangoMaximoAtaque(), is(2.0));
    }

    @Test
    void testCrearPersonajeConTipoLuchadorRanged() {
        Character ranged = new Character(1000, 1, true, "rangedFighter");
        assertThat(ranged.getRangoMaximoAtaque(), is(20.0));
    }

    @Test
    void testPersonajeConTipoLuchadorDefault() {
        Character defaultFighter = new Character(1000, 1, true, "otherFighter");
        assertThat(defaultFighter.getRangoMaximoAtaque(), is(1000000.0));
    }

    @Test
    void testHacerDanioDentroDelRango() {
        atacante.hacerDanio(200, atacante, objetivo, 1.5);
        assertThat(objetivo.getSalud(), is(equalTo(800.0)));
    }

    @Test
    void testHacerDanioFueraDelRango() {
        try {
            atacante.hacerDanio(200, atacante, objetivo, 30.0);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("El personaje no puede atacar porque está demasiado lejos de su objetivo."));
        }
    }

    @Test
    void testHacerDanioAUnMismoPersonaje() {
        try {
            atacante.hacerDanio(200, atacante, atacante, 1.5);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Un personaje no puede hacer daño a sí mismo."));
        }
    }

    @Test
    void testHacerDanioConNivelSuperior() {
        atacante.setNivel(6);
        objetivo.setNivel(1);
        atacante.hacerDanio(200, atacante, objetivo, 1.5);
        assertThat(objetivo.getSalud(), is(equalTo(400.0)));
    }

    @Test
    void testHacerDanioConNivelInferior() {
        atacante.setNivel(1);
        objetivo.setNivel(6);
        atacante.hacerDanio(200, atacante, objetivo, 1.5);
        assertThat(objetivo.getSalud(), is(equalTo(600.0)));
    }

    @Test
    void testCurarPersonaje() {
        objetivo.setSalud(500);
        atacante.curar(400, objetivo);
        assertThat(objetivo.getSalud(), is(equalTo(800.0)));
    }

    @Test
    void testCurarPersonajeAFull() {
        objetivo.setSalud(900);
        atacante.curar(200, objetivo);
        assertThat(objetivo.getSalud(), is(equalTo(1000.0)));
    }

    @Test
    void testCurarPersonajeMuerto() {
        objetivo.setVivo(false);
        try {
            atacante.curar(200, objetivo);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is("No se puede curar a un personaje muerto."));
        }
    }

    @Test
    void testCurarPersonajeConPuntosNegativos() {
        try {
            atacante.curar(-200, objetivo);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Los puntos de curación deben ser positivos."));
        }
    }
}
