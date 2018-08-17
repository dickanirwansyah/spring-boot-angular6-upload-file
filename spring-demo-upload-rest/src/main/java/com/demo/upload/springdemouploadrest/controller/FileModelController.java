package com.demo.upload.springdemouploadrest.controller;

import com.demo.upload.springdemouploadrest.entity.FileModel;
import com.demo.upload.springdemouploadrest.entity.View;
import com.demo.upload.springdemouploadrest.repository.FileModelRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(origins = {"*"})
public class FileModelController {

    @Autowired private FileModelRepository fileModelRepository;

    @JsonView(View.FileInfo.class)
    @GetMapping(value = "/files")
    public List<FileModel> listFile(){
        List<FileModel> fileModels = new ArrayList<>();
        for (FileModel fileModel : fileModelRepository.findAll()){
            fileModels.add(fileModel);
        }
        return fileModels;
    }

    /**
     *
     * @param file
     * @param name
     * @return message
     */

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file,
                             @RequestParam("name")String name){
        try{

            FileModel fileModel = FileModel.builder()
                    .name(name)
                    .mimetype(file.getContentType())
                    .pic(file.getBytes())
                    .build();

            fileModelRepository.save(fileModel);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "file berhasil di upload ";
    }

    /**
     *
     * @param file
     * @return message
     */

    @PostMapping(value = "/uploaded")
    public String fileUpload(@RequestParam("file")MultipartFile file){
        try{

            FileModel fileModel = FileModel.builder()
                    .name(file.getOriginalFilename())
                    .mimetype(file.getContentType())
                    .pic(file.getBytes())
                    .build();
            fileModelRepository.save(fileModel);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "file berhasil di upload";
    }

    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id){
        Optional<FileModel> fileModel = fileModelRepository.findById(id);
        if (fileModel.isPresent()){
            FileModel file = fileModel.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +file.getName()+"\"")
                    .body(file.getPic());
        }
        return ResponseEntity.status(404).body(null);
    }

}
