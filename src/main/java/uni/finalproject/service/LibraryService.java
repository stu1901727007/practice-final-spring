package uni.finalproject.service;

import org.springframework.web.multipart.MultipartFile;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.Library;

public interface LibraryService {

    /**
     *
     * @param libraryFormRequest
     * @param multipartFile
     * @param library
     * @return
     */
    Library updateLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile, Library library);

    /**
     *
     * @param libraryFormRequest
     * @param multipartFile
     * @return
     */
    Library createLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile);

    /**
     *
     * @param library
     * @return
     */
    Boolean deleteLibrary(Library library);

    /**
     *
     * @param library
     * @return
     */
    Boolean removeImage(Library library);
}
