package com.educonnect.security.model;

public enum SecuritySocialProvider {
	FACEBOOK("facebook"), KAKAO("kakao"), NAVER("naver");

	private String providerId;

	private SecuritySocialProvider(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderId() {
		return this.providerId;
	}

}
