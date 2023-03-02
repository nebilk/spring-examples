package tr.com.nebilk.springsecurityjwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/resources")
public class ResourceController {
    @GetMapping
    public ResponseEntity<List<String>> getSampleResources(){
        return ResponseEntity.ok(Arrays.asList("Sample1", "Sample2"));
    }
}
