package me.dev;

public class FakeHinnoittelija implements IHinnoittelija{
    private float ale;

    public FakeHinnoittelija(float alennus) {
        this.ale = alennus;
    }
    public float getAlennusProsentti(Asiakas asiakas, Tuote tuote) {
        return this.ale;
    }

    @Override
    public void aloita() {

    }

    @Override
    public void setAlennusProsentti(Asiakas asiakas, float v) {
        this.ale = v;
    }

    @Override
    public void lopeta() {

    }
}
