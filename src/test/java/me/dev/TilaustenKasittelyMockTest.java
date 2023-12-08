package me.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TilaustenKasittelyMockTest {
    @Mock
    IHinnoittelija hintaMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaaKäsittelijäWitchMockitoHinnoittelija() {
        float alkuSaldo = 100.0f;
        float listaHinta = 30.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta*(1-alennus/100));

        Asiakas asiakas = new Asiakas(alkuSaldo);

        Tuote tuote = new Tuote("TDD in action", listaHinta);

        when(hintaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);
// Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hintaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
// Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hintaMock, atLeast(2)).getAlennusProsentti(asiakas, tuote);
    }
    @Test
    public void testaaYliSata() {
        float alkuSaldo = 500.0f;
        float listaHinta = 150.0f;
        float alennus = 25.0f;

        Asiakas asiakas = new Asiakas(alkuSaldo);

        Tuote tuote = new Tuote("tuote", listaHinta);

        when(hintaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);

        TilaustenKäsittely handler = new TilaustenKäsittely();
        handler.setHinnoittelija(hintaMock);

        handler.käsittele(new Tilaus(asiakas, tuote));

        float alePros = 1f-(alennus)/100;
        float alennettuHinta = listaHinta*(alePros);

        float loppuSaldo = (alkuSaldo - (listaHinta*(1f-(alennus)/100)));

        System.out.println(loppuSaldo);

        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);

        verify(hintaMock, atLeast(2)).getAlennusProsentti(asiakas, tuote);
    }

    @Test
    public void testaaAlleSata() {
        float alkuSaldo = 100.0f;
        float listaHinta = 50.0f;
        float alennus = 20.0f;

        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("muumimuki", listaHinta);

        when (hintaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);

        TilaustenKäsittely handler = new TilaustenKäsittely();
        handler.setHinnoittelija(hintaMock);

        handler.käsittele(new Tilaus(asiakas, tuote));

        assertEquals(asiakas.getSaldo(), alkuSaldo - (listaHinta * ( 1 - (alennus/100) )));
        System.out.println(asiakas.getSaldo());
    }
}
