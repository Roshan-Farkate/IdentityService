package com.xseedai.identityservice.model;

import java.util.HashMap;
import java.util.Map;

public class EmailModel {
    private String resetLink;

    public String getResetLink() {
        return resetLink;
    }

    public void setResetLink(String resetLink) {
        this.resetLink = resetLink;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> model = new HashMap<>();
        model.put("resetLink", resetLink);
        return model;
    }
}
