package com.clickbus.places;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clickbus.places.domain.dtos.PlaceDto;
import com.clickbus.places.domain.entities.PlaceEntity;
import com.clickbus.places.repositories.PlacesRepository;
import com.clickbus.places.services.PlacesService;
import com.clickbus.places.services.impl.PlacesServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlacesServiceTests {

    private PlacesService service;
	
	@Mock
	private PlacesRepository repository;
	
   @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new PlacesServiceImpl(repository);
    }
		
	@Test
	public void testGetPlaceById_Sucess() {
		long id = 1;
		when(repository.findById(anyLong())).thenReturn(Optional.of(new PlaceEntity()));
		PlaceEntity place = service.getPlace(id);
		Assert.assertNotNull(place);
	}
	
	@Test
	public void testGetPlaceById_NotFound() {
		long id = 1;
		when(repository.findById(anyLong())).thenReturn(Optional.<PlaceEntity>empty());
		PlaceEntity place = service.getPlace(id);
		Assert.assertNull(place);
	}
	
	@Test
	public void testGetPlaces_Sucess() {
		
		List<PlaceEntity> list = new ArrayList<>();
		PlaceEntity place = new PlaceEntity("Place", "", "");
		list.add(place);
		
		when(repository.findAll()).thenReturn(list);
		List<PlaceEntity> places = service.getPlaces(null);
		Assert.assertEquals(1, places.size());
	}
	
	@Test
	public void testGetPlaces_NoContent() {
		
		List<PlaceEntity> listEmpty = new ArrayList<>();
		
		when(repository.findAll()).thenReturn(listEmpty);
		List<PlaceEntity> places = service.getPlaces(null);
		Assert.assertEquals(0, places.size());
	}
	
	@Test
	public void testGetPlacesByName_Sucess() {
		
		List<PlaceEntity> list = new ArrayList<>();
		PlaceEntity place = new PlaceEntity("Place", "", "");
		list.add(place);
		
		when(repository.findByNameContainingIgnoreCaseOrderByNameAsc(anyString())).thenReturn(list);
		List<PlaceEntity> places = service.getPlaces("place");
		Assert.assertEquals(1, places.size());
	}
	
	@Test
	public void testGetPlacesByName_NotFound() {
		
		List<PlaceEntity> listEmpty = new ArrayList<>();
		
		when(repository.findByNameContainingIgnoreCaseOrderByNameAsc(anyString())).thenReturn(listEmpty);
		List<PlaceEntity> places = service.getPlaces("place");
		Assert.assertEquals(0, places.size());
	}
	
	@Test
	public void testCreatePlace_Sucess() {
		
		PlaceDto placeDto = new PlaceDto();
		
		placeDto.setName("Name");
		placeDto.setCity("City");
		placeDto.setState("State");
		
		when(repository.save(any(PlaceEntity.class))).thenReturn(new PlaceEntity());
		PlaceEntity place = service.createPlace(placeDto);
		Assert.assertNotNull(place);
	}
	
	@Test
	public void testUpdatePlace_Sucess() {
		
		long id = 1;
		PlaceDto placeDto = new PlaceDto();
		
		placeDto.setName("Name");
		placeDto.setCity("City");
		placeDto.setState("State");
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(new PlaceEntity()));
		when(repository.save(any(PlaceEntity.class))).thenReturn(new PlaceEntity());
		PlaceEntity place = service.updatePlace(id, placeDto);
		Assert.assertNotNull(place);
	}
	
	@Test
	public void testUpdatePlace_NotFound() {
		
		PlaceDto placeDto = new PlaceDto();
		
		placeDto.setName("Name");
		placeDto.setCity("City");
		placeDto.setState("State");
		
		when(repository.findById(anyLong())).thenReturn(Optional.<PlaceEntity>empty());
		PlaceEntity place = service.createPlace(placeDto);
		Assert.assertNull(place);
	}
    
}
