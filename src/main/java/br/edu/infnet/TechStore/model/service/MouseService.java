package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.repository.MouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collection;

@Service
public class MouseService{
    @Autowired
    private MouseRepository mouseRepository;
    @Autowired
    S3fileService s3fileService;
    private String bucket_folder =  "techdrop_mouse/";
    private String type =  "mouse";
    public void incluir(Mouse mouse, MultipartFile multipartFile ){
        mouse.setType(type);
        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,mouse.getMarca(),mouse.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            mouse.setImgUrl(s3FileUrl);
        }
        else{
            mouse.setImgUrl("https://techdrop-bucket.s3.amazonaws.com/default.png");
        }

        mouseRepository.save(mouse);
    }

    public void excluir(Integer id){
        Mouse mouseDB = mouseRepository.findById(id).get();
        mouseDB.setStatus(false);
        mouseRepository.save(mouseDB);
    }

    public void atualizar(Mouse mouse,Integer id, MultipartFile multipartFile){
        mouse.setType(type);
        Mouse mouseDB = mouseRepository.findById(id).get();
        mouse.setId(mouseDB.getId());

        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,mouse.getMarca(),mouse.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            mouse.setImgUrl(s3FileUrl);
        }else{
            mouse.setImgUrl(mouseDB.getImgUrl());
        }

        mouseRepository.save(mouse);
    }


    public Collection<Mouse> obterLista(){
        return mouseRepository.findAll(Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Collection<Mouse> obterLista(Integer id){
        return mouseRepository.findAll(id,Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Mouse getById(Integer id){
        return mouseRepository.findById(id).get();
    }

}
