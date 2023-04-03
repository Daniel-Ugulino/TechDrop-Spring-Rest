package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.domain.Teclado;
import br.edu.infnet.TechStore.model.repository.TecladoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class TecladoService {
    @Autowired
    private TecladoRepository tecladoRepository;
    @Autowired
    S3fileService s3fileService;
    private String bucket_folder =  "techdrop_teclado/";
    private String type =  "teclado";
    public void incluir(Teclado teclado, MultipartFile multipartFile){
        teclado.setType(type);
        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,teclado.getMarca(),teclado.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            teclado.setImgUrl(s3FileUrl);
        }else {
            teclado.setImgUrl("https://techdrop-bucket.s3.amazonaws.com/default.png");
        }

        tecladoRepository.save(teclado);
    }

    public void excluir(Integer id){
        Teclado tecladoDB = tecladoRepository.findById(id).get();
        tecladoDB.setStatus(false);
        tecladoRepository.save(tecladoDB);
    }

    public void atualizar(Teclado teclado,Integer id, MultipartFile multipartFile){

        Teclado tecladoDB = tecladoRepository.findById(id).get();
        teclado.setId(tecladoDB.getId());
        teclado.setType(type);

        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,teclado.getMarca(),teclado.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            teclado.setImgUrl(s3FileUrl);
        }else{
            teclado.setImgUrl(tecladoDB.getImgUrl());
        }

        tecladoRepository.save(teclado);
    }

    public Collection<Teclado> obterLista(){
        return tecladoRepository.findAll(Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Collection<Teclado> obterLista(Integer id){
        return tecladoRepository.findAll(id,Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Teclado getById(Integer id){
        return tecladoRepository.findById(id).get();
    }

}
