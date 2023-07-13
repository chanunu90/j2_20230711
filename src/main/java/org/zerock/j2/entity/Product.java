package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private String pdesc;

    private String writer;

    private int price;

    private boolean delFlag;

    @ElementCollection(fetch = FetchType.LAZY )
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    public void addImage(String name){
        ProductImage pImage = ProductImage.builder().fname(name)
        .ord(images.size()).build();
        images.add(pImage);
    }

    public void clearImages() {
        images.clear();
    }

    public void changePrice(int price){
        this.price = price;
    }

    public void changePaname(String pname) { this.pname = pname; }

    public void changePdesc(String pdesc) { this.pdesc = pdesc; }

    public void changeDel(boolean delFlag) { this.delFlag = delFlag; }

}
