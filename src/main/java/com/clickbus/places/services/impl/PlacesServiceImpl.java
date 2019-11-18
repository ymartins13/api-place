package com.clickbus.places.services.impl;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clickbus.places.domain.dtos.PlaceDto;
import com.clickbus.places.domain.entities.PlaceEntity;
import com.clickbus.places.repositories.PlacesRepository;
import com.clickbus.places.services.PlacesService;

@Service
public class PlacesServiceImpl implements PlacesService {

	private final PlacesRepository repository;

	@Autowired
	public PlacesServiceImpl(PlacesRepository repository) {
		super();
		this.repository = repository;
	}

	public PlaceEntity getPlace(long id) {

		Optional<PlaceEntity> place =  repository.findById(id);

		if (place.isPresent())
			return place.get();
		else
			return null;

	}

	public List<PlaceEntity> getPlaces(String filterName) {

		if (validNullOrEmpty(filterName))
			return repository.findAll();
		else
			return repository.findByNameContainingIgnoreCaseOrderByNameAsc(filterName);
		
	}


	public PlaceEntity createPlace(PlaceDto request) {

		PlaceEntity place = new PlaceEntity(request.getName(), request.getCity(), request.getState());
		place.setSlug(generateSlugValue(request.getName(), request.getCity(), request.getState()));

		place.setCreatedAt(LocalDateTime.now());

		return repository.save(place);

	}

	public PlaceEntity updatePlace(long id, PlaceDto request) {

		PlaceEntity place = this.getPlace(id);
		if (place == null)
			return null;

		place.setName(!validNullOrEmpty(request.getName()) ? request.getName() : place.getName());
		place.setCity(!validNullOrEmpty(request.getCity()) ? request.getCity() : place.getCity());
		place.setState(!validNullOrEmpty(request.getState()) ? request.getState() : place.getState());
		
		place.setId(id);
		place.setSlug(generateSlugValue(place.getName(), place.getCity(), place.getState()));
		place.setUpdatedAt(LocalDateTime.now());

		return repository.save(place);

	}
	
	private boolean validNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private String generateSlugValue(String name, String city, String state) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name).append(" ").append(city).append(" ").append(state);
		String slug = stringBuilder.toString().toLowerCase();
		slug = slug.replace(' ', '-');
		slug = Normalizer.normalize(slug, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		return slug;
	}

}
