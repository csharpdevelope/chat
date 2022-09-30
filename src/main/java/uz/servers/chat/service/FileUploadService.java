package uz.servers.chat.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.servers.chat.model.Attachment;
import uz.servers.chat.repo.AttachmentRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AttachmentRepository imagesRepository;

    @Value("${file.upload.path}")
    private String rootPath;

    public Attachment uploadFilename(String content, String ext) {
        Attachment images = new Attachment();
        byte[] imageByte = Base64.getDecoder().decode(content);
        images.setExtension(ext);
        String filename = getUniqueFileName(ext);
        InputStream inputStream = new ByteArrayInputStream(imageByte);
        String uploadPath;
        try {
            uploadPath = uploadFilenameByYearByMonthByDay();
            Path path = Paths.get(uploadPath);
            if (!Files.exists(path))
                Files.createDirectories(path);
            Files.copy(inputStream, path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("File Don't write");
            return null;
        }
        images.setFilename(uploadPath + filename);
        imagesRepository.save(images);
        return images;
    }

    private String uploadFilenameByYearByMonthByDay() throws IOException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return  rootPath + "/" + year + "/" + month + "/" + day + "/";
    }

    private String getUniqueFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }
}
