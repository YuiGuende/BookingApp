package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.address.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GeoUtils {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    @Autowired
    public GeoUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private static final double EARTH_RADIUS = 6371; // Bán kính Trái Đất (km)

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Khoảng cách (km)
    }

    public static double[] splitPosition(String position) {
        String[] parts = position.split(","); // Không có khoảng trắng sau dấu phẩy
        double latitude = Double.parseDouble(parts[0].trim());
        double longitude = Double.parseDouble(parts[1].trim());
        return new double[]{latitude, longitude};
    }
    private static final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search";

    // public double[] getCoordinates(String location) {
    //     @SuppressWarnings("deprecation")
    //     UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(NOMINATIM_BASE_URL)
    //             .queryParam("q", location)
    //             .queryParam("format", "json")
    //             .queryParam("limit", 1);
    //     JsonNode response = restTemplate.getForObject(builder.toUriString(), JsonNode.class);
    //     if (response != null && response.isArray() && response.size() > 0) {
    //         JsonNode firstResult = response.get(0);
    //         double lat = firstResult.get("lat").asDouble();
    //         double lon = firstResult.get("lon").asDouble();
    //         return new double[]{lat, lon};
    //     }
    //     return null;
    // }
    // public double[] getCoordinates(String location) {
    //     try {
    //         String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_BASE_URL)
    //                 .queryParam("q", location)
    //                 .queryParam("format", "json")
    //                 .queryParam("limit", 1)
    //                 .build()
    //                 .toUriString();
    //         logger.info("Requesting coordinates for location: {}", location);
    //         logger.info("URL: {}", url);
    //         ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    //         logger.info("Response status: {}", response.getStatusCode());
    //         logger.info("Response body: {}", response.getBody());
    //         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
    //             ObjectMapper mapper = new ObjectMapper();
    //             JsonNode jsonNode = mapper.readTree(response.getBody());
    //             if (jsonNode.isArray() && jsonNode.size() > 0) {
    //                 JsonNode firstResult = jsonNode.get(0);
    //                 double lat = firstResult.get("lat").asDouble();
    //                 double lon = firstResult.get("lon").asDouble();
    //                 logger.info("Coordinates found: lat={}, lon={}", lat, lon);
    //                 return new double[]{lat, lon};
    //             } else {
    //                 logger.warn("No results found for location: {}", location);
    //             }
    //         } else {
    //             logger.warn("Unsuccessful response for location: {}", location);
    //         }
    //     } catch (RestClientException e) {
    //         logger.error("RestClientException while fetching coordinates for location: {}", location, e);
    //     } catch (JsonProcessingException e) {
    //         logger.error("JsonProcessingException while parsing response for location: {}", location, e);
    //     } catch (Exception e) {
    //         logger.error("Unexpected error while fetching coordinates for location: {}", location, e);
    //     }
    //     return null;
    // }
    // public Map<String, String> getAddressComponents(String location) {
    //     Map<String, String> result = new HashMap<>();
    //     try {
    //         String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_BASE_URL)
    //                 .queryParam("q", location)
    //                 .queryParam("format", "json")
    //                 .queryParam("limit", 1)
    //                 .build()
    //                 .toUriString();
    //         logger.info("Requesting coordinates for location: {}", location);
    //         logger.info("URL: {}", url);
    //         ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    //         logger.info("Response status: {}", response.getStatusCode());
    //         logger.info("Response body: {}", response.getBody());
    //         if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
    //             ObjectMapper mapper = new ObjectMapper();
    //             JsonNode jsonNode = mapper.readTree(response.getBody());
    //             if (jsonNode.isArray() && jsonNode.size() > 0) {
    //                 JsonNode address = jsonNode.get(0).get("address");
    //                 result.put("road", address.has("road") ? address.get("road").asText() : "");
    //                 result.put("city", address.has("city") ? address.get("city").asText() : "");
    //                 result.put("state", address.has("state") ? address.get("state").asText() : "");
    //                 result.put("country", address.has("country") ? address.get("country").asText() : "");
    //             } else {
    //                 logger.warn("No results found for location: {}", location);
    //             }
    //         } else {
    //             logger.warn("Unsuccessful response for location: {}", location);
    //         }
    //     } catch (RestClientException e) {
    //         logger.error("RestClientException while fetching address components for location: {}", location, e);
    //     } catch (JsonProcessingException e) {
    //         logger.error("JsonProcessingException while parsing response for location: {}", location, e);
    //     } catch (Exception e) {
    //         logger.error("Unexpected error while fetching address components for location: {}", location, e);
    //     }
    //     return result;
    // }
    // public Address loadAddress(Address address,String location){
    //     Map<String, String > addressComponents = getAddressComponents(location);
    //     address.setCountry(addressComponents.getOrDefault("country", null));
    //     address.setState(addressComponents.getOrDefault("state", null));
    //     address.setCity(addressComponents.getOrDefault("city", null));
    //     address.setStreet(addressComponents.getOrDefault("street", null));
    //     return address;
    // }
    public Map<String, String> getAddressComponents(String location) {
        Map<String, String> result = new HashMap<>();
        try {
            String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_BASE_URL)
                    .queryParam("q", location)
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .queryParam("addressdetails", 1) 
                    .build()
                    .toUriString();

            logger.info("Requesting address components for location: {}", location);
            logger.info("URL: {}", url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            logger.info("Response status: {}", response.getStatusCode());
            logger.info("Response body: {}", response.getBody());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getBody());

                if (jsonNode.isArray() && jsonNode.size() > 0) {
                    JsonNode firstResult = jsonNode.get(0);
                    JsonNode address = firstResult.get("address");

                    // Extract lat and lon from the top-level object
                    result.put("lat", firstResult.has("lat") ? firstResult.get("lat").asText() : "");
                    result.put("lon", firstResult.has("lon") ? firstResult.get("lon").asText() : "");

                    // Extract address components if available
                    if (address != null) {
                        result.put("street", getStreet(address));
                        result.put("city", getCity(address));
                        result.put("state", getState(address));
                        result.put("country", address.has("country") ? address.get("country").asText() : "");
                    } else {
                        // If address is null, try to extract some information from the display_name
                        System.out.println(" NULLL");
                        String displayName = firstResult.has("display_name") ? firstResult.get("display_name").asText() : "";
                        String[] parts = displayName.split(", ");
                        if (parts.length > 0) {
                            result.put("city", parts[0]);
                        }
                        if (parts.length > 1) {
                            result.put("state", parts[1]);
                        }
                        if (parts.length > 2) {
                            result.put("country", parts[parts.length - 1]);
                        }
                    }
                } else {
                    logger.warn("No results found for location: {}", location);
                }
            } else {
                logger.warn("Unsuccessful response for location: {}", location);
            }
        } catch (RestClientException e) {
            logger.error("RestClientException while fetching address components for location: {}", location, e);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException while parsing response for location: {}", location, e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching address components for location: {}", location, e);
        }

        return result;
    }

    public static String preprocessCityName(String city) {
        if (city == null) {
            return null;
        }
        return city.replaceAll("^(Thành phố|Thị xã|Huyện|Tỉnh)\\s+", "").trim();
    }

    private String getStreet(JsonNode address) {
        String street = "";
        if (address.has("street")) {
            street = address.get("street").asText();
        }
        // Remove common prefixes
        street = street.replaceAll("^(Thành phố|Thị xã|Huyện|Tỉnh)\\s+", "");

        return street.trim();
    }

    private String getCity(JsonNode address) {
        String city = "";
        if (address.has("city")) {
            city = address.get("city").asText();
        }

        // Remove common prefixes
        city = city.replaceAll("^(Thành phố|Thị xã|Huyện|Tỉnh)\\s+", "");

        return city.trim();
    }

    private String getState(JsonNode address) {
        String state = "";
        if (address.has("state")) {
            state = address.get("state").asText();
            System.out.println("tỉnh ++++++++"+address.get("state").asText());
        } 
        // Remove common prefixes
        state = state.replaceAll("^(Thành phố|Thị xã|Huyện|Tỉnh)\\s+", "");

        return state.trim();
    }

    public Address loadAddress( String location) {
        Map<String, String> addressComponents = getAddressComponents(location);
        Address address = new Address();
        address.setCountry(preprocessCountryName(addressComponents.getOrDefault("country", null)));
        address.setState(preprocessStateName(addressComponents.getOrDefault("state", null)));
        address.setCity(preprocessCityName(addressComponents.getOrDefault("city", null)));
        address.setStreet(addressComponents.getOrDefault("road", null));

        // Set positioning if available
        String lat = addressComponents.get("lat");
        String lon = addressComponents.get("lon");
        if (lat != null && lon != null) {
            address.setPositioning(lat + "," + lon);
        }

        return address;
    }

    public static String preprocessStateName(String state) {
        if (state == null) {
            return null;
        }
        return state.replaceAll("^(Tỉnh|Bang)\\s+", "").trim();
    }

    public static String preprocessCountryName(String country) {
        if (country == null) {
            return null;
        }
        return country.replaceAll("^(Nước|Quốc gia)\\s+", "").trim();
    }

    public double[] getCoordinates(String location) {
        Map<String, String> addressComponents = getAddressComponents(location);
        String lat = addressComponents.get("lat");
        String lon = addressComponents.get("lon");

        if (lat != null && lon != null) {
            try {
                return new double[]{Double.parseDouble(lat), Double.parseDouble(lon)};
            } catch (NumberFormatException e) {
                logger.error("Error parsing coordinates for location: {}", location, e);
            }
        }

        return null;
    }
}
