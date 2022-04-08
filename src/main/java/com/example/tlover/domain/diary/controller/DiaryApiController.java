package com.example.tlover.domain.diary.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/diary")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryApiController {
}
