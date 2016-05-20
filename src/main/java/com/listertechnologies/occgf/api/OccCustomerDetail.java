package com.listertechnologies.occgf.api;

public class OccCustomerDetail {
    private String profileId;
    private OccProfile profile;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public OccProfile getProfile() {
        return profile;
    }

    public void setProfile(OccProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return profile.getShippingAddress().getAddress1();
    }
}
