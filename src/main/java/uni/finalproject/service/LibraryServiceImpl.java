package uni.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.Library;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.image.LibraryRepository;
import uni.finalproject.util.DateUtils;
import uni.finalproject.util.FileUploadUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Boolean removeImage(Library library) {

        library.setFile(null);

        libraryRepository.save(library);
        return true;
    }

    /**
     * @param libraryFormRequest
     * @param multipartFile
     * @return
     */
    public Library createLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile) {

        libraryFormRequest.setLibraryDeleted(false);

        Library library = new Library();
        library.setCreatedAt(new Date());

        return _updateRecord(libraryFormRequest, multipartFile, library);
    }

    /**
     * @param libraryFormRequest
     * @param multipartFile
     * @param library
     * @return
     */
    public Library updateLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile, Library library) {

        libraryFormRequest.setLibraryDeleted(false);

        return _updateRecord(libraryFormRequest, multipartFile, library);
    }

    /**
     * @param library
     * @return
     */
    public Boolean deleteLibrary(Library library) {

        library.setDeleted(true);
        libraryRepository.save(library);

        return true;
    }

    /**
     * @param libraryFormRequest
     * @param multipartFile
     * @param library
     * @return
     */
    private Library _updateRecord(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile, Library library) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        library.setTitle(libraryFormRequest.getLibraryTitle())
                .setResume(libraryFormRequest.getLibraryResume())
                .setText(libraryFormRequest.getLibraryText())
                .setEmbed(libraryFormRequest.getLibraryEmbed())
                .setPhotographer(libraryFormRequest.getLibraryPhotographer())
                .setAgency(libraryFormRequest.getLibraryAgency())
                .setType(libraryFormRequest.getLibraryType())
                .setDeleted(libraryFormRequest.getLibraryDeleted())
                .setUpdatedAt(DateUtils.today());
        try {
            library.setPublicationDate(DateUtils.formattedDb(libraryFormRequest.getLibraryPublicationDate()));
        } catch (Exception e) {
        }


        library = libraryRepository.saveAndFlush(library);

        try {
            this._uploadImage(library, multipartFile);
        } catch (IOException e) {
        }

        return library;
    }

    /**
     * @param library
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private Boolean _uploadImage(Library library, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if (!fileName.isEmpty()) {
            library.setFile(fileName);
            libraryRepository.save(library);

            String uploadDir = "uploads/media/" + library.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return true;
        }

        return false;
    }
}
