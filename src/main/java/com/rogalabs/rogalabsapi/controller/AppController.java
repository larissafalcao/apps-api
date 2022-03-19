package com.rogalabs.rogalabsapi.controller;

import com.rogalabs.rogalabsapi.dto.AppDTO;
import com.rogalabs.rogalabsapi.service.IAppService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/app")
public class AppController {
    private final IAppService appService;

    public AppController(IAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    @ApiOperation("get all apps")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
    })
    public ResponseEntity<List<AppDTO>> getApps() {
        return ResponseEntity.ok(appService.getAllApps());
    }

    @PostMapping
    @ApiOperation("create a new app")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 400, message = "validation error"),
    })
    public ResponseEntity<AppDTO> createApp(@Valid @RequestBody AppDTO app){
        return new ResponseEntity<>((appService.createApp(app)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("update an app")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
            @ApiResponse(code = 400, message = "validation error")
    })
    public ResponseEntity<AppDTO> updateApp(@PathVariable Long id, @Valid @RequestBody AppDTO app){
        return ResponseEntity.ok(appService.update(id, app));
    }

    @GetMapping("/{id}")
    @ApiOperation("get an  app")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
    })
    public ResponseEntity<AppDTO> getAppById(@PathVariable Long id){
        return ResponseEntity.ok(appService.getById(id));
    }

    @GetMapping("/findByType")
    @ApiOperation("get apps by type")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
    })
    public ResponseEntity<List<AppDTO>>  getAppsByType(@RequestBody String type){
        return ResponseEntity.ok(appService.getAppsByType(type));
    }

    @GetMapping("/findByName")
    @ApiOperation("get apps by name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
    })
    public ResponseEntity<List<AppDTO>>  getAppsByName(@RequestBody String name){
        return ResponseEntity.ok(appService.getAppsByName(name));
    }

    @GetMapping("/getCheaperAppByType")
    @ApiOperation("get apps by name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "app created successfully"),
            @ApiResponse(code = 404, message = "app not found"),
    })
    public ResponseEntity<AppDTO> getCheaperApp(@RequestBody String type){
        return ResponseEntity.ok(appService.getCheaperAppByType(type));
    }

}
