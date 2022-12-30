package utils;

import input.CredentialsInput;

public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;
    public Credentials() {

    }

    public Credentials(final Credentials credentials) {
        this.name = credentials.name;
        this.accountType = credentials.accountType;
        this.password = credentials.password;
        this.country = credentials.country;
        this.balance = credentials.balance;

    }

    public Credentials(final CredentialsInput credentials) {
        this.name = credentials.getName();
        this.accountType = credentials.getAccountType();
        this.password = credentials.getPassword();
        this.country = credentials.getCountry();
        this.balance = credentials.getBalance();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getBalance() {
        return balance;
    }

    /**
     *
     * @return converted value to int of balance field which is String
     */
    public int convertBalanceInt() {
        return Integer.parseInt(balance);
    }

    public void setBalance(final int balance) {
        this.balance = Integer.toString(balance);
    }
}
