package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public Page<Sale> findSales(String minDate, String maxDate, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate min = validacaoDataMinima(minDate, today);
		LocalDate max = validacaoDataMaxima(maxDate, today);
		return repository.findSales(min, max, pageable);
	}

	private LocalDate validacaoDataMaxima(String maxDate, LocalDate today) {
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		return max;
	}

	private LocalDate validacaoDataMinima(String minDate, LocalDate today) {
		LocalDate min = minDate.equals("") ? today.minusDays(365) : LocalDate.parse(minDate);
		return min;
	}

}
