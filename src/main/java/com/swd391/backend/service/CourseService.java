package com.swd391.backend.service;

import com.swd391.backend.dao.CategoryRepository;
import com.swd391.backend.dao.ChapterRepository;
import com.swd391.backend.dao.CourseRepository;
import com.swd391.backend.entity.Category;
import com.swd391.backend.entity.Chapter;
import com.swd391.backend.entity.Course;
import com.swd391.backend.service.Interface.ICourseService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public int addCourse(String chapter, List<Integer> category, String courseName, String courseDescription, int coursePrice) {
        // Create course
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(courseDescription);
        course.setPrice(coursePrice);
        // Add course to database
        Course newCourse = courseRepository.save(course);
//        //Add chapter to database
//        Chapter newChapter = new Chapter();
//        newChapter.setChapterContent(chapter);
//        newChapter.setCourse(newCourse);
//        chapterRepository.save(newChapter);
        //Handle chapter
        List<String> title = new ArrayList<>();
        List<String> url = new ArrayList<>();
        // Khởi tạo ChromeOptions và cấu hình chế độ headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        // Khởi tạo ChromeDriver với ChromeOptions
        WebDriver driver = new ChromeDriver(options);
        //open the website
        try {
            driver.get(chapter); //https://www.youtube.com/playlist?list=PLCd8j6ZYo0lYj4aXZby1k8rOYG_73Fb3L
            Thread.sleep(3000);
            // Javascript executor
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight)");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //print the title
        System.out.println("Title: " + driver.getTitle());
        List<WebElement> elements = driver.findElements(By.tagName("ytd-playlist-video-renderer"));
        //Trong trường hợp này, chúng ta sẽ tìm các thẻ ytd-thumbnail có class là style-scope ytd-playlist-video-renderer
        for (WebElement element : elements) {
            List<WebElement> content = element.findElements(By.id("content"));
            for (WebElement c : content) {
                List<WebElement> container = element.findElements(By.id("container"));
                for (WebElement b : container) {
                    List<WebElement> meta = b.findElements(By.id("meta"));
                    for (WebElement m : meta) {
                        //tìm tất cả các thẻ h3
                        List<WebElement> h3 = m.findElements(By.tagName("h3"));
//                    System.out.println(h3.get(0).getText());
                        title.add(h3.get(0).getText());
                        //ở trong thẻ h3, tìm thẻ a và lấy href
                        List<WebElement> a = h3.get(0).findElements(By.tagName("a"));
//                    System.out.println(a.get(0).getAttribute("href"));
                        url.add(a.get(0).getAttribute("href"));
                    }
                }
            }
        }
        //Add title and url to Chapter and save to database
        for (int i = 0; i < title.size(); i++) {
            Chapter newChapter = new Chapter();
            newChapter.setChapterName(title.get(i));
            newChapter.setChapterContent(url.get(i));
            newChapter.setCourse(newCourse);
            chapterRepository.save(newChapter);
        }
        // Add categories to database
        for (int categoryId : category) {
            Category cat = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Không tìm thấy Category với id: " + categoryId));
            cat.getCourses().add(newCourse);
            categoryRepository.save(cat);
        }
        return newCourse.getCourseId();
    }
}
