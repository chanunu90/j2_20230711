package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images") // exclude 제외한다.
// @ToString
public class FileBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private String writer;
    
    @BatchSize(size = 20)
    @OneToMany(cascade = {CascadeType.ALL} , fetch = FetchType.LAZY , orphanRemoval = true)
    @JoinColumn(name="board")
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>(); // 바꿀 수 없다, 지우면 안됨

    public void addImage(FileBoardImage boardImage){

        // 순번을 준다 size값으로
        boardImage.changeOrd(images.size());
        // 이미지 추가
        images.add(boardImage);


    }

    public void clearImages(){
        images.clear();
        // repository.deleteById(bno);
    }



}
