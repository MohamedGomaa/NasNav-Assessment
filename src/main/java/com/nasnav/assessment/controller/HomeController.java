package com.nasnav.assessment.controller;

import static com.nasnav.assessment.strings.Paths.HOME_API;

import com.nasnav.assessment.service.IPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HOME_API)
@RequiredArgsConstructor
public class HomeController {

  private final IPictureService pictureService;

  @GetMapping
  public ResponseEntity<?> getAllApprovedPictures(){
    return new ResponseEntity<>(pictureService.getAllApprovedImages(), HttpStatus.OK);
  }
}
