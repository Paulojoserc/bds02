package com.devsuperior.bds02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	@Autowired
	private CityRepository repository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> cityList = repository.findAll(Sort.by("name"));
		return cityList.stream().map(CityDTO::new).toList();
	}

	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		City cityEntity = new City();
		cityEntity.setName(cityDTO.getName());
		return new CityDTO(repository.save(cityEntity));
	}

	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("City not found"+ id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Referential integrity failure");
		}
	}
}
