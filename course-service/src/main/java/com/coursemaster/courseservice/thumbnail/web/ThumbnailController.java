package com.coursemaster.courseservice.thumbnail.web;

import com.coursemaster.courseservice.thumbnail.api.ThumbnailDTO;
import com.coursemaster.courseservice.thumbnail.domain.ThumbnailFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses/thumbnails")
public class ThumbnailController {

    private final ThumbnailFacade thumbnailFacade;

    @Operation(summary = "Downloads thumbnail file", description = "Downloads thumbnail file by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thumbnail file"),
            @ApiResponse(responseCode = "400", description = "Incorrect data")})
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/getById")
    public ResponseEntity<ThumbnailDTO> getById(Integer id) {
        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(thumbnailFacade.findById(id));
    }
}
