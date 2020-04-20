package com.owners.gravitas.dto.tour;

import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zuner.coshopping.model.BaseModel;

/**
 * The Class TourFeedbackResponse.
 *
 * @author rajputbh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourFeedbackResponse extends BaseModel {

    private static final long serialVersionUID = 2733359778664966143L;

    @NotBlank(message = "id is required")
    private String id;

    private Map<String, String> signedS3Urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getSignedS3Urls() {
        return signedS3Urls;
    }

    public void setSignedS3Urls(Map<String, String> signedS3Urls) {
        this.signedS3Urls = signedS3Urls;
    }
}
