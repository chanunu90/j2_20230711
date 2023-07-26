package org.zerock.j2.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.zerock.j2.util.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProdcutService {

  private final ProductRepository productRepository;

  private final FileUploader fileUploader;
  @Override
  public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {


    return productRepository.listWithReview(requestDTO);
  }

  @Override
  public Long register(ProductDTO productDTO) {

    Product product = Product.builder()
            .pname(productDTO.getPname())
            .pdesc(productDTO.getPdesc())
            .price(productDTO.getPrice())
            .build();

    productDTO.getImages().forEach(fname->{
      product.addImage(fname);
    });

    return productRepository.save(product).getPno();

  }

  @Override
  public ProductDTO readOne(Long pno) {

    Product product = productRepository.selectOne(pno);

    ProductDTO dto = ProductDTO.builder()
            .pno(product.getPno())
            .pname(product.getPname())
            .price(product.getPrice())
            .pdesc(product.getPdesc())
            .images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
            .build();
    return dto;
  }

  @Override
  public void remove(Long pno) {
    // 삭제전 조회
    Product product =productRepository.selectOne(pno);

    product.changeDel(true);

    productRepository.save(product);

    List<String> fileNames =
            product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

    fileUploader.removeFiles(fileNames);

  }

  @Override
  public void modify(ProductDTO productDTO) {
    //기존의 프로덕트를 가지고와라
    Optional<Product> result = productRepository.findById(productDTO.getPno());

    Product product = result.orElseThrow();

    //기본 정보들 수정
    product.changePaname(productDTO.getPname());
    product.changePdesc(productDTO.getPdesc());
    product.changePrice(productDTO.getPrice());


    //기존 이미지 목록을 살린다.
    List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

    //IMAGE는 한번 지우고 즉 CLEAR IAMGES같은걸 해줘서
    product.clearImages();

    productDTO.getImages().forEach(fname -> product.addImage(fname));


    log.info("===================asd=====================");
    log.info(product);
    log.info("========asd================================");

    //이미지 문자열을 추가 한다. addImage()

    //SAVE()해준다.
    productRepository.save(product);
    
    // 비교 삭제productDTO.getImages() 없는 파일 찾아서 삭제
    List<String> newFiles = productDTO.getImages();
    List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1)
            .collect(Collectors.toList());


    fileUploader.removeFiles(wantDeleteFiles);


  }


}