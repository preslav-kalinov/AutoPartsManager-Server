package com.pkalinov.autopartsmgmtserver.configs;

import com.pkalinov.autopartsmgmtserver.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    @Bean
    public FileService instantiateFileService(@Value("${autopartsmgmt.fileservice.filepath}") String filePath) {
        return new FileService(filePath);
    }
}
