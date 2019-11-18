package com.clickbus.places;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.clickbus.places.domain.entities.PlaceEntity;
import com.clickbus.places.repositories.PlacesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PlacesApplication.class, H2TestConfig.class})
@ActiveProfiles("test")
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PlacesRepositoryTests {
	
	@Autowired
	private PlacesRepository repository;
		
	@Before
	public void setUp() {
		
		PlaceEntity entity = new PlaceEntity("Home", "Contagem", "MG");
		entity.setSlug("slug");
		entity.setCreatedAt(LocalDateTime.now());
	    repository.save(entity);

		entity = new PlaceEntity("Home2", "Contagem", "MG");
		entity.setSlug("slug");
		entity.setCreatedAt(LocalDateTime.now());
	    repository.save(entity);
	    
		entity = new PlaceEntity("School", "Contagem", "MG");
		entity.setSlug("slug");
		entity.setCreatedAt(LocalDateTime.now());
	    repository.save(entity);
	}
	
	@Test
	public void testSavePlace_Success() {
		PlaceEntity entity = new PlaceEntity("Place", "Contagem", "MG");
		entity.setSlug("slug");
		entity.setCreatedAt(LocalDateTime.now());
	    PlaceEntity place = repository.save(entity);
	    assertNotNull(place);
	}
	
    @Test
    public void testGetPlaceById_Success() {
        Optional<PlaceEntity> place = repository.findById((long) 1);
        assertNotNull(place);
    }

    @Test
    public void testGetPlaceById_NotFound() {
        Optional<PlaceEntity> place = repository.findById((long) 10);
        assertFalse(place.isPresent());
    }
    
    @Test
    public void testGetAllPlaces_Success() {
        List<PlaceEntity> places = repository.findAll();
        assertEquals(3, places.size());
    }
    
    @Test
    public void testGetAllPlaces_NotFound() {
    	repository.deleteAll();
        List<PlaceEntity> places = repository.findAll();
        assertEquals(0, places.size());
    }
    
    @Test
    public void testGetAllPlacesByName_Success() {
        List<PlaceEntity> places = repository.findByNameContainingIgnoreCaseOrderByNameAsc("home");
        assertEquals(2, places.size());
    }
    
    @Test
    public void testGetAllPlacesByName_NoContent() {
        List<PlaceEntity> places = repository.findByNameContainingIgnoreCaseOrderByNameAsc("EUA");
        assertEquals(0, places.size());
    }
    
}
