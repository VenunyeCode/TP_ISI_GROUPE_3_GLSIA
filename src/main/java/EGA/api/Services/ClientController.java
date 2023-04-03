package EGA.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import EGA.api.Entity.Client;
import EGA.api.Repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
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
    @PostMapping("/")
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Integer id, @RequestBody Client client) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setNom(client.getNom());
            updatedClient.setPrenom(client.getPrenom());
            // set other properties

            Client savedClient = clientRepository.save(updatedClient);
            return ResponseEntity.ok().body(savedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable(value = "id") Integer id) {
        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clientRepository.delete(client.get());
        return ResponseEntity.noContent().build();
    }
}
