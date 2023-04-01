package tr.com.nebildev.springredis.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tr.com.nebildev.springredis.dto.CityRequest;
import tr.com.nebildev.springredis.dto.CityResponse;
import tr.com.nebildev.springredis.entity.City;
import tr.com.nebildev.springredis.service.RedisCityService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {

    private final RedisCityService redisCityService;

    @GetMapping(value = "/{code}")
    public ResponseEntity<CityResponse> getCity(@PathVariable String code){
        return ResponseEntity.ok(redisCityService.getCity(code));
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities(){
        return ResponseEntity.ok(redisCityService.getCities());
    }

    @PostMapping
    public ResponseEntity<CityResponse> saveCity(@RequestBody CityRequest cityRequest){
        final CityResponse cityResponse = redisCityService.saveCity(cityRequest);
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(uri).body(cityResponse);
    }

    @DeleteMapping
    public void deleteCity(String cityId){
        redisCityService.deleteCityById(cityId);
    }

}
