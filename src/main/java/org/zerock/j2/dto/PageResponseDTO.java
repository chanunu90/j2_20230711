package org.zerock.j2.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

// JSON으로 만들때, 어떤것들을 전달할 것인가
// 어떻게 만들 것인가.
// 통신할때 최대한 단순하게 해준다.

@Data

public class PageResponseDTO<E> {
    
    private List<E> dtoList;
    
    private long totalCount;

    private List<Integer> pageNums;

    private boolean prev, next;
    
    private PageRequestDTO requestDTO;
    
    private int page, size, start, end;


    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO){
       
        this.dtoList = dtoList;
        this.totalCount = totalCount;
        this.requestDTO = pageRequestDTO;

        this.page = pageRequestDTO.getPage();

        this.size = pageRequestDTO.getSize();

        // 13
        int tempEnd = (int)(Math.ceil(page/10.0) * 10);

        this.start = tempEnd-9;
        this.prev = this.start !=1;

        //20 17.8
        int realEnd =  (int)Math.ceil(totalCount/(double)size);

        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        this.next  = (this.end * this.size) < totalCount;

        //pageNums 처리
        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
    }
}
