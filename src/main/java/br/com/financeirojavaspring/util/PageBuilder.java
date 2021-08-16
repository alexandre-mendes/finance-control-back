package br.com.financeirojavaspring.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageBuilder {

    private PageBuilder() {}

    public static  <T, R> Page<R> createPage(Page<T> page, Pageable pageable, Function<? super T, ? extends R> mapper) {
        return new PageImpl<>((List<R>) page.stream().map(mapper).collect(Collectors.toList()), pageable, page.getTotalElements());
    }
}
