package kg.airbnb.airbnb.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class AdminPageAllHousingResponses {

    private int adminPageAllHousingResponseListSize;
    private List<AdminPageHousingResponse> adminPageHousingResponseList;

}
