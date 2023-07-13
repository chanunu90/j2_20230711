package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileBoardImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgno;

    private String uuid;

    private String fname;

    private int ord;

    public void changeOrd (int ord){
        this.ord = ord;
    }


}
