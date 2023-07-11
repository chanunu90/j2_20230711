package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.FileBoardImage;

import jakarta.transaction.Transactional;

@SpringBootTest
public class RepositoryTests {
    
    @Autowired
    FileBoardRepository repository;

    @Test
    public void insert(){
        for(int i = 0; i < 100; i++){

             FileBoard fileBoard = FileBoard.builder()
            .title("AA")
            .content("AAA")
            .writer("AA")
            .build();

            FileBoardImage img1 =  FileBoardImage.builder()
            .uuid(UUID.randomUUID().toString())
            .fname("aaa.jpg")
            .build();

            FileBoardImage img2 =  FileBoardImage.builder()
            .uuid(UUID.randomUUID().toString())
            .fname("bb.jpg")
            .build();

            fileBoard.addImage(img1);
            fileBoard.addImage(img2);

            repository.save(fileBoard);


        }//end for 
       
        
    }

    @Commit
    @Transactional
    @Test
    public void testRemove() {

        Long bno = 2L;

        repository.deleteById(bno);

    }

    @Transactional
    @Test
    public void testRead() {

        Long bno = 100L;

        Optional<FileBoard> result = repository.findById(bno);

        FileBoard board = result.orElseThrow();

        System.out.println(board);
        

    }

    @Transactional
    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0,10);

        Page<FileBoard> result = repository.findAll(pageable);

        //System.out.println(result);

        result.get().forEach(board -> {
            System.out.println(board);
            System.out.println(board.getImages());
        });
    }

    @Transactional
    @Test
    public void testListQuerydsl(){

        PageRequestDTO requestDTO = new  PageRequestDTO();

        System.out.println(repository.list(requestDTO));


    }


    @Test
    public void testSelectOne(){
        Long bno = 100L;

        FileBoard board = repository.selectOne(bno);
        System.out.println(board);
        System.out.println(board.getImages());
        
        
    }


    @Test
    @Commit
    @Transactional
    public void testDelete(){
        Long bno = 85L;


        repository.deleteById(bno);
    }

    @Test
    @Commit
    @Transactional
    public void testUpdate(){
        Long bno = 20L;


        Optional<FileBoard> file = repository.findById(bno);
        FileBoard board = file.orElseThrow();

        board.clearImages();


        FileBoardImage img1 =  FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("zzz.jpg")
        .build();

        board.addImage(img1);

        repository.save(board);


        // repository.deleteById(bno);
    }



}