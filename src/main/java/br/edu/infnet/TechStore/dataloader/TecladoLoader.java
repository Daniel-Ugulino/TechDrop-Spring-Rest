package br.edu.infnet.TechStore.dataloader;

import br.edu.infnet.TechStore.model.domain.Teclado;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.TecladoService;
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

@Order(2)
@Component
public class TecladoLoader implements ApplicationRunner {

    @Autowired
    private TecladoService tecladoService;

    private List<Teclado> teclados;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String file = "dummyData/teclados.json";
        Gson gson = new Gson();

        Usuario admin = new Usuario();
        admin.setId(1);

        try{

            FileReader fileR = new FileReader(file);
            BufferedReader leitura = new BufferedReader(fileR);

            JsonArray json = new JsonParser().parse(leitura).getAsJsonArray();

            Type collectionType = new TypeToken<List<Teclado>>(){}.getType();
            this.teclados = gson.fromJson(json, collectionType);

            for(Teclado teclado : teclados){
                teclado.setUsuario(admin);
                teclado.setStatus(true);
                tecladoService.incluir(teclado,null);
                System.out.println("O teclado: " + teclado.getMarca() + " " + teclado.getModelo() + " foi incluido com sucesso");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
