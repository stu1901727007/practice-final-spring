package uni.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uni.finalproject.http.requests.backoffice.AgencyFormRequest;
import uni.finalproject.http.requests.backoffice.LibraryFormRequest;
import uni.finalproject.models.image.Agency;
import uni.finalproject.models.image.Library;
import uni.finalproject.models.user.User;
import uni.finalproject.repository.image.AgencyRepository;
import uni.finalproject.util.DateUtils;

import java.util.Date;

@Component
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private UserService userService;

    @Autowired
    private AgencyRepository agencyRepository;

    /**
     *
     * @param agencyFormRequest
     * @return
     */
    public Agency createAgency(AgencyFormRequest agencyFormRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Agency agency = new Agency();
        agency.setOwner(user);
        agency.setCreatedAt(new Date());

        agencyFormRequest.setAgencyDeleted(false);

        return _updateRecord(agencyFormRequest, agency);
    }

    /**
     *
     * @param agencyFormRequest
     * @param agency
     * @return
     */
    public Agency updateAgency(AgencyFormRequest agencyFormRequest, Agency agency) {

        agencyFormRequest.setAgencyDeleted(false);

        return _updateRecord(agencyFormRequest, agency);
    }

    /**
     *
     * @param agencyFormRequest
     * @param agency
     * @return
     */
    private Agency _updateRecord(AgencyFormRequest agencyFormRequest, Agency agency) {

        agency.setCode(agencyFormRequest.getAgencyCode().toUpperCase())
                .setName(agencyFormRequest.getAgencyName())
                .setDetails(agencyFormRequest.getAgencyDetails())
                .setDeleted(agencyFormRequest.getAgencyDeleted())
                .setUpdatedAt(DateUtils.today());

        agencyRepository.save(agency);

        return agency;
    }

    /**
     *
     * @param agency
     * @return
     */
    public Boolean deleteLibrary(Agency agency) {

        agency.setDeleted(true);
        agencyRepository.save(agency);

        return true;
    }
}
