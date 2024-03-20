package com.swd391.backend.service;

import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.dao.ImageRepository;
import com.swd391.backend.entity.Course;
import com.swd391.backend.entity.Image;
import com.swd391.backend.service.Interface.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public void addImages(int courseId, String image) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Không tìm thấy Course với id: " + courseId));
        Image img = new Image();
        img.setImageData(image);
        img.setCourse(course);
        imageRepository.save(img);
    }
    public Image getImage(int courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Không tìm thấy Course với id: " + courseId));
        return imageRepository.findImageByCourse(course).orElse(new Image());
    }

}
