package Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Websites {

    private Integer id;
    private String url;
    private String siteName;
    private Boolean active;
    private String statusText;
    private LocalDateTime createdAt;

}
