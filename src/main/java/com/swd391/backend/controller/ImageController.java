package com.swd391.backend.controller;

import com.swd391.backend.entity.Image;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "List course images", description = "Account management APIs")
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/add")
    public ResponseEntity<?> createImage(@RequestBody Map<String, Object> requestData) {
        try {
            int courseId = (int) requestData.get("courseId");
            String imageData = (String) requestData.get("imageData");
            imageService.addImages(courseId, imageData);
            return ResponseEntity.ok("Thêm ảnh thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/get")
    public ResponseEntity<Object> getImage(@RequestBody CreateOrder courseID){
        return ResponseEntity.ok(imageService.getImage(courseID.getCourseID()));
    }
}
