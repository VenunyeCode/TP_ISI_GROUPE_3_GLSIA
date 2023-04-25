package EGA.api.Services;

import EGA.api.Entity.Client;
import EGA.api.Repository.ClientRepository;
import EGA.api.RequestEntity.TransactionRequest;
import EGA.api.RequestEntity.TransfertRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import EGA.api.Repository.CompteRepository;
import EGA.api.Entity.Compte;


import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("api/v1/comptes")
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/all")
    public List<Compte> getAllComptes(){
        return compteRepository.findAll();
    }

    @GetMapping("/{num}")
    public ResponseEntity<Compte> getCompteByNumeroCompte(@PathVariable(value = "num") String num){
        Optional<Compte> compte = compteRepository.findById(num);
        if(compte.isPresent()){
            return ResponseEntity.ok().body(compte.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{client_id}/saveAccount")
    public ResponseEntity<Compte> createCompte(@PathVariable(value = "client_id") Integer client_id, @RequestBody Compte compte) {
        Optional<Client> client = clientRepository.findById(client_id);
        if(!client.isPresent())
            return ResponseEntity.notFound().build();
        compte.setNumeroCompte(UUID.
                randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase() +
                        Calendar.getInstance().get(Calendar.YEAR));
        compte.setClient(client.get());
        compteRepository.save(compte);

        return ResponseEntity.ok().body(compte);
    }

    @PutMapping("/{num}/account")
    public ResponseEntity<Compte> updateCompte(@PathVariable(value = "num") String num, @RequestBody Compte compte) {
        Optional<Compte> existingCompte = compteRepository.findById(num);
        if (existingCompte.isPresent()) {
            Compte updatedCompte = existingCompte.get();
            updatedCompte.setTypeCompte(existingCompte.get().getTypeCompte());
            // set other properties

            Compte savedCompte = compteRepository.save(updatedCompte);
            return ResponseEntity.ok().body(savedCompte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/depot")
    public ResponseEntity<String> faireDepot(@RequestBody TransactionRequest request) {
        Optional<Compte> existingCompte = compteRepository.findById(request.getNumeroCompte());
        if (existingCompte.isPresent()) {
            existingCompte.get().increaseSolde(request.getMontant());
            compteRepository.save(existingCompte.get());

            return ResponseEntity.ok().body("Opération effectuée avec succès");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/retrait")
    public ResponseEntity<String> faireRetrait(@RequestBody TransactionRequest request) {
        Optional<Compte> existingCompte = compteRepository.findById(request.getNumeroCompte());
        if (existingCompte.isPresent()) {
            Compte updatedCompte = existingCompte.get();
            if(updatedCompte.decreaseSolde(request.getMontant())){
                Compte savedCompte = compteRepository.save(updatedCompte);
                return ResponseEntity.badRequest().body("Opération effectuée avec succès");
            }
            else
                return ResponseEntity.badRequest().body("Fonds insuffisants");


        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/transfert")
    public ResponseEntity<String> transfer(@RequestBody TransfertRequest request) {

        Optional<Compte> fromAccount = compteRepository.findById(request.getSender());
        Optional<Compte> toAccount = compteRepository.findById(request.getReceiver());

        if (!fromAccount.isPresent()) {
            return ResponseEntity.badRequest().body("Numéro de compte du destinateur invalide");
        }

        if (!toAccount.isPresent()) {
            return ResponseEntity.badRequest().body("Numéro de compte du destinataire invalide");
        }

        if (fromAccount.get().getSolde() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Fonds insuffisants");
        }

        fromAccount.get().decreaseSolde(request.getAmount());
        toAccount.get().increaseSolde(request.getAmount());

        Compte savedFromCompte = compteRepository.save(fromAccount.get());
        Compte savedToCompte = compteRepository.save(toAccount.get());

        return ResponseEntity.ok("Transfert effectué avec succès");
    }

    @DeleteMapping("/{num}/account")
    public ResponseEntity<Compte> deleteCompte(@PathVariable(value = "num") String num) {
        Optional<Compte> compte = compteRepository.findById(num);
        if (!compte.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        compteRepository.delete(compte.get());
        return ResponseEntity.noContent().build();
    }
}
