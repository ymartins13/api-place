package com.clickbus.places.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.clickbus.places.domain.dtos.PlaceDto;
import com.clickbus.places.domain.entities.PlaceEntity;
import com.clickbus.places.services.PlacesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/places")
@Api(tags = { "Places" })
public class PlacesController {

	public static final String Error = "Could not complete operation.";
	public static final String Sucess = "Sucess.";
	public static final String Created = "Created.";
	public static final String NoContent = "No content.";
	public static final String BadRequest = "Bad request.";
	public static final String NotFound = "Not found.";
	public static final String InternalServerError = "Internal server error.";
	
	private final PlacesService service;

	@Autowired
	public PlacesController(PlacesService service) {
		super();
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get places.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Sucess),
			@ApiResponse(code = 204, message = NoContent), 
			@ApiResponse(code = 500, message = InternalServerError) })
	public ResponseEntity<List<PlaceEntity>> get(
			@ApiParam(value = "filter name") @RequestParam(value = "filterName", required = false) String filterName) {
		try {
			List<PlaceEntity> places = service.getPlaces(filterName);

			if (!places.isEmpty())
				return new ResponseEntity<List<PlaceEntity>>(places, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Error, e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ApiOperation(value = "Get place by id.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Sucess),
			@ApiResponse(code = 404, message = NotFound),
			@ApiResponse(code = 500, message = InternalServerError) })
	public ResponseEntity<PlaceEntity> getById(
			@ApiParam(value = "id place") @PathVariable(value = "id", required = true) long id) {
		try {
			PlaceEntity place = service.getPlace(id);
			if (place != null)
				return new ResponseEntity<PlaceEntity>(place, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Error, e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	@ApiOperation(value = "Create a new place.")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = Created),
			@ApiResponse(code = 400, message = BadRequest),
			@ApiResponse(code = 500, message = InternalServerError) })
	public ResponseEntity<PlaceEntity> post(
			@ApiParam(value = "place", required = true) @Valid @RequestBody PlaceDto request) {
		try {
			PlaceEntity place = service.createPlace(request);
			return new ResponseEntity<PlaceEntity>(place, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Error, e);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ApiOperation(value = "Update an existing place.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Sucess),
			@ApiResponse(code = 400, message = BadRequest),
			@ApiResponse(code = 404, message = NotFound),
			@ApiResponse(code = 500, message = InternalServerError) })
	public ResponseEntity<PlaceEntity> put(
			@ApiParam(value = "id place") @PathVariable(value = "id", required = true) long id,
			@ApiParam(value = "place", required = true) @Valid @RequestBody PlaceDto request) {
		try {
			PlaceEntity place = service.updatePlace(id, request);
			if (place != null)
				return new ResponseEntity<PlaceEntity>(place, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Error, e);
		}
	}
}
