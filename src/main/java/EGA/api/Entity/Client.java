package EGA.api.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String nom;
        private String prenoms;
        private LocalDate datenaissance;
        private String sexe;
        private String adresse;
        private String telephone;
        private String courriel;
        private String nationalite;
        @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
        private List<Compte> comptes;

        public String getNom(){
            return this.nom;
        }
        public String setNom(String nom){
            this.nom = nom;
            return this.nom;
        }
        public String getPrenom(){
            return this.prenoms;
        }
        public String setPrenom(String prenoms){
            this.prenoms = prenoms;
            return this.prenoms;
        }
        public String getTelephone(){
            return this.telephone;
        }
        public String setTelephone(String telephone){
            this.telephone = telephone;
            return this.telephone;
        }
        public String getCourriel(){
            return this.courriel;
        }
        public String setCourriel(String courriel){
            this.courriel = courriel;
            return this.courriel;
        }
        public String getNationalite(String nationalite){
            this.nationalite = nationalite;
            return nationalite;
        }
        public String getSexe(){
            return this.sexe;
        }
        public String setSexe(String sexe){
            this.sexe = sexe;
            return this.sexe;
        }
    }


