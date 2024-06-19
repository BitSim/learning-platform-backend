package org.learning.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BitSim
 * @version v1.0.0
 **/
@RestController
@RequestMapping("/course")
public class CourseController {
    @GetMapping
    public String getCourse() {
        return "course";
    }
}
