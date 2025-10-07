package bqminh.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
