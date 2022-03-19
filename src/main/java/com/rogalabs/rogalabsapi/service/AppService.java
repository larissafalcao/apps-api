package com.rogalabs.rogalabsapi.service;

import com.rogalabs.rogalabsapi.domain.AppEntity;
import com.rogalabs.rogalabsapi.dto.AppDTO;
import com.rogalabs.rogalabsapi.exception.AppNotFoundException;
import com.rogalabs.rogalabsapi.repository.AppRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AppService implements IAppService{
    private ModelMapper modelMapper;
    private AppRepository appRepository;

    public AppService(ModelMapper modelMapper, AppRepository appRepository) {
        this.modelMapper = modelMapper;
        this.appRepository = appRepository;
    }

    private AppDTO convertToDto(AppEntity project) {
        return modelMapper.map(project, AppDTO.class);
    }

    private AppEntity convertToEntity(AppDTO projectDTO) {
        return modelMapper.map(projectDTO, AppEntity.class);
    }

    @Override
    public AppDTO update(Long id, AppDTO app) {
        AppEntity foundApp = findApp(id);
        BeanUtils.copyProperties(app, foundApp, "id");
        return convertToDto(appRepository.save(foundApp));
    }

    @Override
    public AppDTO getById(Long id) {
        return convertToDto(findApp(id));
    }


    public AppDTO createApp(AppDTO appDTO) {
        return convertToDto(appRepository.save(convertToEntity(appDTO)));
    }

    private AppEntity findApp(Long id){
        return appRepository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    public List<AppDTO> getAllApps() {
        List<AppEntity> apps = appRepository.findAll();
        return apps.stream().map(this::convertToDto).collect(toList());
    }

    @Override
    public List<AppDTO> getAppsByType(String type) {
        List<AppEntity> apps = appRepository.findByType(type);
        return apps.stream().map(this::convertToDto).collect(toList());
    }

    @Override
    public List<AppDTO> getAppsByName(String name) {
        List<AppEntity> apps = appRepository.findByNameContaining(name);
        return apps.stream().map(this::convertToDto).collect(toList());
    }

    @Override
    public AppDTO getCheaperAppByType(String type) {
        List<AppEntity> apps = appRepository.findByType(type);
        AppEntity foundApp = new AppEntity();
        foundApp.setPrice(Double.MAX_VALUE);

        for (AppEntity app:
             apps) {
            if(app.getPrice() < foundApp.getPrice()){
                foundApp = findApp(app.getId());
            }
        }
        return convertToDto(foundApp);
    }
}
