package com.example.tlover.domain.scrap.controller;

import com.example.tlover.domain.scrap.constant.ScrapConstants.EScrapResponseMessage;
import com.example.tlover.domain.scrap.dto.ScrapChangeRequest;
import com.example.tlover.domain.scrap.dto.ScrapChangeResponse;
import com.example.tlover.domain.scrap.dto.ScrapCountResponse;
import com.example.tlover.domain.scrap.service.ScrapService;
import com.example.tlover.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/scraps")
@RequiredArgsConstructor
@Api(tags = "Scrap API")
public class ScrapController {

    private final ScrapService scrapService;

    @ApiOperation(value = "다이어리의 스크랩 수 조회", notes = "다이어리의 스크랩 수를 조회합니다.")
    @GetMapping("{diaryId}")
    public ResponseEntity<ResponseDto<ScrapCountResponse>> getScrapCount(@PathVariable Long diaryId) {
        return ResponseEntity.ok(ResponseDto.create(EScrapResponseMessage.eScrapCountSuccess.getMessage()
                , this.scrapService.getScrapCount(diaryId)));
    }

    @ApiOperation(value = "다이어리 스크랩 생성/삭제", notes = "다이어리의 스크랩을 생성/삭제 합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<ScrapChangeResponse>> changeScrap(@Valid @RequestBody ScrapChangeRequest scrapChangeRequest) {
        ScrapChangeResponse scrapChangeResponse = this.scrapService.changeScrap(scrapChangeRequest);
        if(scrapChangeResponse.isCreated()){
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{scrapId}")
                    .buildAndExpand(scrapChangeResponse.getScrapId())
                    .toUri();
            return ResponseEntity.created(location).body(ResponseDto.create(EScrapResponseMessage.eCreateScrapMessage.getMessage(), scrapChangeResponse));
        }
        else return ResponseEntity.noContent().build();
    }

}
