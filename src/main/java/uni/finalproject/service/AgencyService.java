package uni.finalproject.service;

import uni.finalproject.http.requests.backoffice.AgencyFormRequest;
import uni.finalproject.models.image.Agency;

public interface AgencyService {

    /**
     *
     * @param agencyFormRequest
     * @param agency
     * @return
     */
    Agency updateAgency(AgencyFormRequest agencyFormRequest, Agency agency);

    /**
     *
     * @param agencyFormRequest
     * @return
     */
    Agency createAgency(AgencyFormRequest agencyFormRequest);

    /**
     *
     * @param agency
     * @return
     */
    Boolean deleteAgency(Agency agency);
}
