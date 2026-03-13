package com.example.demo.dto;

public class UserLookupResult {
    private UserDTO user;
    private boolean fallbackUsed;
    private String downstreamStatus;
    private String reason;

    public UserLookupResult() {
    }

    public UserLookupResult(UserDTO user, boolean fallbackUsed, String downstreamStatus, String reason) {
        this.user = user;
        this.fallbackUsed = fallbackUsed;
        this.downstreamStatus = downstreamStatus;
        this.reason = reason;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public boolean isFallbackUsed() {
        return fallbackUsed;
    }

    public void setFallbackUsed(boolean fallbackUsed) {
        this.fallbackUsed = fallbackUsed;
    }

    public String getDownstreamStatus() {
        return downstreamStatus;
    }

    public void setDownstreamStatus(String downstreamStatus) {
        this.downstreamStatus = downstreamStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
