package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.repository.HeadsetRepository;
import br.edu.infnet.TechStore.model.repository.MouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class HeadsetService {
    @Autowired
    private HeadsetRepository headsetRepository;
    @Autowired
    S3fileService s3fileService;
    private String bucket_folder =  "techdrop_headset/";
    private String type =  "headset";
    public void incluir(Headset headset, MultipartFile multipartFile ){
        headset.setType(type);
        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile,bucket_folder,headset.getMarca(),headset.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            headset.setImgUrl(s3FileUrl);
        }
        else{
            headset.setImgUrl("https://techdrop-bucket.s3.amazonaws.com/default.png");
        }
        headsetRepository.save(headset);
    }

    public void excluir(Integer id){
        Headset headsetDB = headsetRepository.findById(id).get();
        headsetDB.setStatus(false);
        headsetRepository.save(headsetDB);
    }

    public void atualizar(Headset headset,Integer id, MultipartFile multipartFile){

        Headset headsetDB = headsetRepository.findById(id).get();
        headset.setId(headsetDB.getId());
        headset.setType(type);

        if (multipartFile != null){
            String path = s3fileService.getFilePath(multipartFile, bucket_folder,headset.getMarca(),headset.getModelo());
            String s3FileUrl = s3fileService.uploadFile(path, multipartFile);
            headset.setImgUrl(s3FileUrl);
        }else{
            headset.setImgUrl(headsetDB.getImgUrl());
        }

        headsetRepository.save(headset);
    }

    public Collection<Headset> obterLista(){

        return headsetRepository.findAll(Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Collection<Headset> obterLista(Integer id){
        return headsetRepository.findAll(id, Sort.by(Sort.Direction.ASC, "marca"));
    }

    public Headset getById(Integer id){
        return headsetRepository.findById(id).get();
    }

}
