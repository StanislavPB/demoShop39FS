package org.demoshop39fs.service.fileService.localVersion;

import lombok.extern.slf4j.Slf4j;
import org.demoshop39fs.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

//@Service
@Slf4j
public class FileServiceLocal {

    private final Path fileStorageLocation = Paths.get("src/main/resources/static/upload").toAbsolutePath().normalize();


    public boolean uploadFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename(); // получаем имя файла оригинальное

            String extension = "";

            if (originalFilename != null) {

                // получаем расширение нашего файла
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            } else {
                throw new IllegalArgumentException("Null original file name");
            }

            String newFilename = UUID.randomUUID().toString();
            newFilename = newFilename + "." + extension;

            Path targetLocation = fileStorageLocation.resolve(newFilename);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            log.error("File upload error: {}", e.getMessage(), e);
            throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, "ошибка сохранения файла: " + file);
        }

        return true;
    }

}
