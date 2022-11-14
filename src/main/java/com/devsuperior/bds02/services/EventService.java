package com.devsuperior.bds02.services;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO eventDTO) {
		try {
			Event eventEntity = eventRepository.getOne(id);
			eventEntity.setId(id);
			eventEntity.setCity(new City(eventDTO.getCityId(), null));
			eventEntity.setDate(LocalDate.from(eventDTO.getDate()));
			eventEntity.setName(eventDTO.getName());
			eventEntity.setUrl(eventDTO.getUrl());
			eventEntity = eventRepository.save(eventEntity);
			return new EventDTO(eventEntity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
}
