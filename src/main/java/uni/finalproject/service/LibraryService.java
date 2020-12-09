package uni.finalproject.service;

import org.springframework.web.multipart.MultipartFile;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.Library;

public interface LibraryService {

    Library updateLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile, Library library);
    Library createLibrary(LibraryFormRequest libraryFormRequest, MultipartFile multipartFile);
    Boolean deleteLibrary(Library library);
    Boolean removeImage(Library library);
}
