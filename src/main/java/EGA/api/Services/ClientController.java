package EGA.api.Services;

import EGA.api.Entity.Compte;
import EGA.api.Repository.CompteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import EGA.api.Entity.Client;
import EGA.api.Repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("api/v1/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }
    @GetMapping("/{id}/comptes")
    public List<Compte> getAllAccountFromClient(@PathVariable(value = "id") Integer id){
        return compteRepository.findByIdClient(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Integer id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return ResponseEntity.ok().body(client.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/saveClient")
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}/updateClient")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Integer id, @RequestBody Client client) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setNom(client.getNom());
            updatedClient.setPrenom(client.getPrenom());
            updatedClient.setSexe(client.getSexe());
            updatedClient.setTelephone(client.getTelephone());
            updatedClient.setCourriel(client.getCourriel());
            updatedClient.setAdresse(client.getAdresse());
            updatedClient.setNationalite(client.getNationalite());
            // set other properties

            Client savedClient = clientRepository.save(updatedClient);
            return ResponseEntity.ok().body(savedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}/deleteClient")
    public ResponseEntity<Client> deleteClient(@PathVariable(value = "id") Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.delete(client.get());
        return ResponseEntity.noContent().build();
    }
}
