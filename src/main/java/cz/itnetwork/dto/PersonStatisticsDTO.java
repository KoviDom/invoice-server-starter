package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonStatisticsDTO {

    private Long personId;
    private String personName;
    private long revenue;

}
