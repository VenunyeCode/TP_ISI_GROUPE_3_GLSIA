package EGA.api.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Compte {
    @Id
    private String numeroCompte;
    @Enumerated(EnumType.ORDINAL)
    private TypeCompte typeCompte;
    private LocalDate dateCreation;
    private double solde;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public String getNumeroCompte(){
        return this.numeroCompte;
    }
    public TypeCompte getTypeCompte(){
        return this.typeCompte;
    }
    public TypeCompte setTypeCompte(TypeCompte typeCompte){
        this.typeCompte = typeCompte;
        return this.typeCompte;
    }
    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Client getClient(){return this.client; }

    public double getSolde(){
        return this.solde;
    }
    public boolean decreaseSolde(double solde){
        if(this.solde>=solde){
            this.solde -= solde;
            return true;
        }
        return false;
    }
    public boolean increaseSolde(double solde){
        this.solde += solde;
        return true;
    }

}
