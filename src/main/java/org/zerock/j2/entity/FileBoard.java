package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "images")
public class FileBoard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    private String writer;

    @BatchSize(size=20)
    @OneToMany(cascade = {CascadeType.ALL} , fetch = FetchType.LAZY , orphanRemoval = true)
    @JoinColumn(name="board")
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>();

    public void addImage(FileBoardImage boardImage){
        boardImage.changeOrd(images.size());
        images.add(boardImage);
    }

    public void cleanImages(){
        images.clear();
    }
    
}
