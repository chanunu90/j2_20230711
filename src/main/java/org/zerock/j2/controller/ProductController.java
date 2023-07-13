package org.zerock.j2.controller;

import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.*;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.service.ProdcutService;
import org.zerock.j2.util.FileUploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/products/")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class ProductController {

    private final ProdcutService service;

    private final FileUploader uploader;

    @PostMapping("")
    public Map<String , Long> register(ProductDTO productDTO){

        log.info("==========================================================================");
        log.info(productDTO);

        List<String> fileNames =  uploader.uploadFiles( productDTO.getFiles() , true);
        productDTO.setImages(fileNames);

        Long pno = service.register(productDTO);

        return Map.of( "result" , pno );
        
    }



    @GetMapping(value ="list")
    public PageResponseDTO<ProductListDTO> list (PageRequestDTO requestDTO){

        
        log.info("==========================================================");
        log.info(requestDTO);


        return service.list(requestDTO);
        
    }

    @GetMapping("{pno}")
    public ProductDTO list (@PathVariable("pno") Long pno){

        log.info("========================================" , pno);
        return service.readOne(pno);

    }

    @DeleteMapping("{pno}")
    public Map<String , Long> delete (@PathVariable("pno") Long pno){

        log.info("========================================" , pno);

        service.remove(pno);

        return Map.of("result" , pno);

    }

    @PostMapping("modify")
    public Map<String , Long> modify(ProductDTO productDTO){

        log.info("==============================modify============================================");
        log.info("==============================modify============================================");
        log.info("==============================modify============================================");
        log.info(productDTO);


        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0){
            List<String> uploadFileNames = uploader.uploadFiles( productDTO.getFiles() , true);
            List<String> oldFileNames = productDTO.getImages();
            uploadFileNames.forEach(fname -> oldFileNames.add(fname));
            log.info(uploadFileNames);
        }





        log.info("After..........................................................");
        log.info(productDTO);

        log.info("After...............asdasds...........................................");
        service.modify(productDTO);

        return Map.of( "result" , 111L );

    }
    
}
