package com.clickbus.places.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clickbus.places.domain.entities.PlaceEntity;

@Repository
public interface PlacesRepository extends JpaRepository<PlaceEntity, Long> {
	
	public List<PlaceEntity> findByNameContainingIgnoreCaseOrderByNameAsc(String filterName);	

}