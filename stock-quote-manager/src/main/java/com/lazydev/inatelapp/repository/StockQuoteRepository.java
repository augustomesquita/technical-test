package com.lazydev.inatelapp.repository;

import com.lazydev.inatelapp.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    Optional<StockQuote> findById(String id);

}
