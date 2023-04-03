package br.edu.infnet.TechStore.dataloader;

import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Endereco;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.ClienteService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@Order(1)
@Component
public class ClienteLoader implements ApplicationRunner {

    @Autowired
    private ClienteService clienteService;

    private List<Cliente> clientes;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String file = "dummyData/clientes.json";
        Gson gson = new Gson();

        Usuario admin = new Usuario();
        admin.setId(1);
        try{

            FileReader fileR = new FileReader(file);
            BufferedReader leitura = new BufferedReader(fileR);

            JsonArray json = new JsonParser().parse(leitura).getAsJsonArray();
            Type collectionType = new TypeToken<List<Cliente>>(){}.getType();
            this.clientes = gson.fromJson(json, collectionType);

            for(Cliente cliente : clientes){
                cliente.setUsuario(admin);
                cliente.setStatus(true);
                cliente.setEndereco(null);
                clienteService.incluir(cliente);
                System.out.println("O cliente: "+cliente.getNome()+" foi incluido com sucesso");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
