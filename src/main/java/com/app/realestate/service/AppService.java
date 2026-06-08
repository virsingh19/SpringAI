package com.app.realestate.service;

import com.app.realestate.model.House;
import com.app.realestate.repository.AppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppService {
    private static final Logger logger = LoggerFactory.getLogger(com.app.realestate.service.AppService.class);

    @Autowired
    private AppRepository appRepository;

    public List<House> getAllHouses() {
        List<House> houses = new ArrayList<>(appRepository.findAll());
        logger.info("  **** getHouses count: {}", houses.size());
        return houses;
    }

    public Optional<House> getHouseByPropertyId(String propertyId) {
        Optional<House> house;

        try {
            house = appRepository.findById(propertyId);
            if (house.isPresent()) {
                logger.info("  **** getHouseByPropertyId: {}", house.get());
            } else {
                logger.info("  **** getHouseByPropertyId not found: {}", propertyId);
            }
        } catch (Exception e) {
            logger.error("message=Exception in getHouseByPropertyId; exception={}", e.toString());
            return Optional.empty();
        }
        return house;
    }

    public List<House> findHousesByType(String type) {
        List<House> houses = appRepository.findByType(type);
        logger.info("  **** findHouseByType: {}", houses);
        return houses;
    }

    public List<House> findHousesByTypeAndZipCode(String type, String zipCode) {
        List<House> houses = appRepository.findByTypeAndZipCode(type, zipCode);
        logger.info("  **** findHouseByTypeAndZipCode: {}", houses);
        return houses;
    }
}
