package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private S3fileService s3fileService;

    private String bucket_folder = "techdrop_cliente/";
    public Cliente incluir(Cliente cliente){
        cliente.setStatus(true);
        return clienteRepository.save( cliente);
    }
    public void incluir(Cliente cliente, MultipartFile multipartFile ){
        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,cliente.getNome(),cliente.getSobrenome());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            cliente.setImgUrl(s3FileUrl);
        }
        else{
            cliente.setImgUrl("https://techdrop-bucket.s3.amazonaws.com/default.png");
        }
        clienteRepository.save(cliente);
    }

    public void atualizar(Cliente cliente,Integer id, MultipartFile multipartFile){

        Cliente clienteDB = clienteRepository.findById(id).get();
        cliente.setId(clienteDB.getId());

        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,cliente.getNome(),cliente.getSobrenome());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            cliente.setImgUrl(s3FileUrl);
        }else{
            cliente.setImgUrl(clienteDB.getImgUrl());
        }

        clienteRepository.save(cliente);
    }
    public void excluir(Integer id) {
        Cliente clienteDB = clienteRepository.findById(id).get();
        clienteDB.setStatus(false);
        clienteRepository.save(clienteDB);
    }

    public Collection<Cliente> obterLista(){

        return clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Collection<Cliente> obterLista(Integer id){
        return clienteRepository.findAll(id, Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Collection<Cliente> listaPaginada(Integer page){
        return clienteRepository.findPaginated(page);
    }

    public Cliente getById(Integer id){
        return clienteRepository.findById(id).get();
    }

}
