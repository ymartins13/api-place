package com.clickbus.places.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clickbus.places.domain.dtos.PlaceDto;
import com.clickbus.places.domain.entities.PlaceEntity;

@Service
public interface PlacesService {

	public PlaceEntity getPlace(long id);
	public List<PlaceEntity> getPlaces(String nameFilter);
	public PlaceEntity createPlace(PlaceDto place);
	public PlaceEntity updatePlace(long id, PlaceDto place);
}
