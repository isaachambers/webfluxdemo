package com.getir.repository;

import com.getir.document.Product;
import java.util.UUID;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, UUID> {
}