package br.edu.infnet.TechStore.dataloader;

import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.HeadsetService;
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

@Order(4)
@Component
public class HeadsetLoader implements ApplicationRunner {

    @Autowired
    private HeadsetService headsetService;

    private List<Headset> headsets;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String file = "dummyData/headsets.json";
        Gson gson = new Gson();
        Usuario admin = new Usuario();
        admin.setId(1);
        try{

            FileReader fileR = new FileReader(file);
            BufferedReader leitura = new BufferedReader(fileR);

            JsonArray json = new JsonParser().parse(leitura).getAsJsonArray();

            Type collectionType = new TypeToken<List<Headset>>(){}.getType();
            this.headsets = gson.fromJson(json, collectionType);

            for(Headset headset : headsets){
                headset.setUsuario(admin);
                headset.setStatus(true);
                headsetService.incluir(headset,null);
                System.out.println("O Headset: " + headset.getMarca() + " " + headset.getModelo() + " foi incluido com sucesso");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
