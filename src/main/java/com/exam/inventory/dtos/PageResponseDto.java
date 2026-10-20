package com.exam.inventory.dtos;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> content;

    private long totalElements;

    private int totalPages;

    private int number;

    private int size;

    public static <T> PageResponseDto<T> from(Page<T> page) {

        return new PageResponseDto<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}