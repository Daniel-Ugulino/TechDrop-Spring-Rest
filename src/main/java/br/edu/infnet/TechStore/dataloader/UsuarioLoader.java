package br.edu.infnet.TechStore.dataloader;

import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.UsuarioService;
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

@Order(0)
@Component
public class UsuarioLoader implements ApplicationRunner {

    @Autowired
    private UsuarioService usuarioService;

    private List<Usuario> usuarios;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String file = "dummyData/usuarios.json";
        Gson gson = new Gson();
        try{
            FileReader fileR = new FileReader(file);
            BufferedReader leitura = new BufferedReader(fileR);

            JsonArray json = new JsonParser().parse(leitura).getAsJsonArray();

            Type collectionType = new TypeToken<List<Usuario>>(){}.getType();
            this.usuarios = gson.fromJson(json, collectionType);

            for(Usuario usuario : usuarios){
                usuario.setStatus(true);
                usuarioService.incluir(usuario);
                System.out.println("O Usuario: " + usuario.getUsername() + " foi incluido com sucesso");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
