package com.kanz.newslettersubscriptionengine.common.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.LinkedList;
import java.util.List;


public class JpaUtil {
    public static <T> List<T> findAllPages(PagingAndSortingRepository<T, Long> repository) {
        Pageable pageable = PageRequest.of(0, 100);
        List<T> contents = new LinkedList<>();
        Page<T> page;
        do {
            page = repository.findAll(pageable);
            contents.addAll(page.getContent());
            pageable = page.nextPageable();
        } while (page.hasNext());
        return contents;
    }
}
