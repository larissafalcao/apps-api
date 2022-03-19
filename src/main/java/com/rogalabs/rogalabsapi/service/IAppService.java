package com.rogalabs.rogalabsapi.service;

import com.rogalabs.rogalabsapi.dto.AppDTO;


import java.util.List;

public interface IAppService {
    AppDTO update(Long id, AppDTO app);
    AppDTO getById(Long id);
    List<AppDTO> getAllApps();
    List<AppDTO> getAppsByType(String type);
    List<AppDTO> getAppsByName(String name);
    AppDTO getCheaperAppByType(String type);
    AppDTO createApp(AppDTO app);
}
