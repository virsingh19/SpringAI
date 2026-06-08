package com.app.realestate.tools;

import com.app.realestate.model.House;
import com.app.realestate.service.AppService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Component
public class SearchTools {
    private static final Logger logger = LoggerFactory.getLogger(SearchTools.class);

    @Autowired
    private AppService appService;


    @Tool(description = "Find houses by type only. Use this tool when the user provides a type but NO zip code. " +
            "Do NOT use this tool when a zip code is also available.", returnDirect = true)
    public List<String> findHousesByType(
            @ToolParam(description = "Type of the house") String type
    ) {
        logger.info("  **** Tool called => findHousesByType");
        logger.info("  **** Type => {}", type);
        return appService.findHousesByType(type).stream().map(House::getPropertyId).toList();
    }

    @Tool(description = "Find houses by type AND zip code. Use this tool ONLY when the user provides BOTH a type " +
            "AND a zip code. Do NOT use this tool if no zip code is provided.", returnDirect = true)
    public List<String> findHousesByTypeAndZipCode(
            @ToolParam(description = "Type of the house") String type,
            @ToolParam(description = "Zip code of the house") String zipCode
    ) {
        logger.info("  **** Tool called => findHousesByTypeAndZipCode");
        logger.info("  **** Type => {}", type);
        logger.info("  **** StateCode => {}", zipCode);
        return appService.findHousesByTypeAndZipCode(type, zipCode).stream().map(House::getPropertyId).toList();
    }

    @Tool(description = "Find a house by its exact property Id. Use this tool ONLY when the user provides a " +
            "Property ID. Do NOT use this tool when a type or zip code is provided instead.")
    public House findHouseByPropertyId(
            @ToolParam(description = "Unique property Id of a house") String propertyId
    ) {
        logger.info("  **** Tool called => findHouseByPropertyId");
        logger.info("  **** propertyId => {}", propertyId);
        Optional<House> house = appService.getHouseByPropertyId(propertyId);
        return house.orElse(null);
    }

    @Tool(description = "Return all houses. Use this tool ONLY when the user wants a full list of houses and " +
            "has NOT provided a type, property ID, or zip code to filter by.", returnDirect = true)
    public List<String> findAllHouses() {
        logger.info("  **** Tool called => findAllHouses");
        List<String> propertyIds = appService.getAllHouses().stream().map(House::getPropertyId).toList();
        logger.info("  *** Property Ids: {}", propertyIds);
        return propertyIds;
    }
}
