package com.app.realestate.controller;

import com.app.realestate.model.House;
import com.app.realestate.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/houses", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @GetMapping
    public ResponseEntity<List<House>> getAllHouses(
    ) {
        List<House> houses = appService.getAllHouses();
        return ok(houses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(
            @PathVariable("id") String id

    ) {
        Optional<House> house = appService.getHouseByPropertyId(id);

        if (house.isPresent()) {
            return new ResponseEntity<>(house.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{type}")
    public ResponseEntity<List<House>> findHousesByType(@PathVariable("type") String type) {
        List<House> houses = appService.findHousesByType(type);
        logger.info("findByName: {}", houses);

        if (houses != null && !houses.isEmpty()) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find/{type}/{zip}")
    public ResponseEntity<List<House>> findHousesByTypeAndZipCode(
            @PathVariable("type") String type,
            @PathVariable("zip") String zipCode
    ) {
        List<House> houses = appService.findHousesByTypeAndZipCode(type, zipCode);
        logger.info("findByNameAndState: {}", houses);

        if (houses != null && !houses.isEmpty()) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
