package org.example;

import org.example.service.DataService;

// Класс для инициализации данных
public class DataInitializer {
    private final DataService postDataService;

    public DataInitializer(DataService postDataService) {
        this.postDataService = postDataService;
    }

    public void initialize() {
        postDataService.initializePosts();
        postDataService.initializeEmployees();
    }

}
