package EGA.api.RequestEntity;

import java.io.Serializable;

public class TransactionRequest implements Serializable {
    private String numeroCompte;
    private double montant;

    public double getMontant() {
        return montant;
    }

    public double setMontant(double montant){
        this.montant = montant;
        return this.montant;
    }
    public String setNumeroCompte(String numeroCompte){
        this.numeroCompte = numeroCompte;
        return this.numeroCompte;
    }
    public String getNumeroCompte() {
        return numeroCompte;
    }
}
