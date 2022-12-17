package ru.job4j.cars.repository;

import ru.job4j.cars.model.PostSearchParams;
import ru.job4j.cars.model.PostSearchResult;

import java.util.List;

public interface PostSearchResultRepository {

    List<PostSearchResult> findAll();

    List<PostSearchResult> search(PostSearchParams postSearchParams);
}