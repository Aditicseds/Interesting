package com.email.emailGenerator;

import lombok.Data;

@Data
public class EmailRequest {
    private String content;
    private String tone;
    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
