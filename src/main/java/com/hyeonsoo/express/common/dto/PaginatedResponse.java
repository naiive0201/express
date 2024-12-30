package com.hyeonsoo.express.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}
