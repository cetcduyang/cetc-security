package com.cetc.security.core.properties;

public class BrowserProperties {

    private String loginPage = "/login_b.html";

    private String signUpUrl = "/login_signUp.html";

    private String logoutSuccessUrl;

    private LoginType loginType = LoginType.JSON;

    private int remeberMeSeconds = 600;

    private SessionProperties session = new SessionProperties();

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRemeberMeSeconds() {
        return remeberMeSeconds;
    }

    public void setRemeberMeSeconds(int remeberMeSeconds) {
        this.remeberMeSeconds = remeberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }
}
