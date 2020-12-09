package uni.finalproject.service;

import uni.finalproject.http.requests.backoffice.AgencyFormRequest;
import uni.finalproject.models.image.Agency;
import uni.finalproject.models.image.Library;

public interface AgencyService {

    Agency updateAgency(AgencyFormRequest agencyFormRequest, Agency agency);
    Agency createAgency(AgencyFormRequest agencyFormRequest);
    Boolean deleteLibrary(Agency agency);
}
