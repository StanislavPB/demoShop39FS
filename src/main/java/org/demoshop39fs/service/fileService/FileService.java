package org.demoshop39fs.service.fileService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.demoshop39fs.dto.StandardResponseDto;
import org.demoshop39fs.entity.FileInfo;
import org.demoshop39fs.repository.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;

    private final FileInfoRepository repository;

    @Transactional
    @SneakyThrows
    public StandardResponseDto uploadFile(MultipartFile file) {


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

        // создаем запрос на отправку файла


        InputStream inputStream = file.getInputStream();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType()); // сообщаем хранилищу какого типа у нас файл


        PutObjectRequest request = new PutObjectRequest(
                "demo-shop-files",
                "image/" + newFilename,
                inputStream,
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(request);

        // получаем ссылку на файл

        String link = amazonS3.getUrl("demo-shop-files", "image/" + newFilename).toString();

        FileInfo fileInfo = new FileInfo().builder()
                .link(link)
                .build();

        repository.save(fileInfo);

        return new StandardResponseDto(link);

    }

}
