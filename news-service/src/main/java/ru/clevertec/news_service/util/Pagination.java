package ru.clevertec.news_service.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalItems;
    private long totalPages;
    @JsonProperty("isFirst")
    private boolean isFirst;
    @JsonProperty("isLast")
    private boolean isLast;
    @JsonProperty("isEmpty")
    private boolean isEmpty;

    public Pagination(Page<T> page) {
        this.content = page.getContent();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.isEmpty = page.isEmpty();
    }

}
