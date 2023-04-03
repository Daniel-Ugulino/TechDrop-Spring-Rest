package br.edu.infnet.TechStore.dataloader;

import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.MouseService;
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

@Order(3)
@Component
public class MouseLoader implements ApplicationRunner {

    @Autowired
    private MouseService mouseService;

    private List<Mouse> mouses;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String file = "dummyData/mouses.json";
        Gson gson = new Gson();

        Usuario admin = new Usuario();
        admin.setId(1);

        try{

            FileReader fileR = new FileReader(file);
            BufferedReader leitura = new BufferedReader(fileR);

            JsonArray json = new JsonParser().parse(leitura).getAsJsonArray();

            Type collectionType = new TypeToken<List<Mouse>>(){}.getType();
            this.mouses = gson.fromJson(json, collectionType);

            for(Mouse mouse : mouses){
                mouse.setUsuario(admin);
                mouse.setStatus(true);

                mouseService.incluir(mouse,null);
                System.out.println("O mouse: " + mouse.getMarca() + " " + mouse.getModelo() + " foi incluido com sucesso");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
