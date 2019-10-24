package Server.controller;

public class DBConstant {
    public static final String User_Table = "users";
    public static final String User_Login = "Login";
    public static final String User_Password = "Password";
    public static final String User_Role = "Role";

    public static final String Clients_Table = "clients";
    public static final String Clients_ID = "ClientID";
    public static final String Clients_Surname = "ClientSurname";
    public static final String Clients_Name = "ClientName";
    public static final String Clients_Lastname = "ClientLastname";
    public static final String Clients_Birthday = "ClientBirthday";
    public static final String Clients_City = "ClientCity";
    public static final String Clients_Street = "ClientStreet";
    public static final String Clients_House = "ClientHouse";
    public static final String Clients_Flat = "ClientFlat";
    public static final String Clients_Salary = "ClientSalary";
    public static final String Clients_Currency = "CurrencyID";

    public static final String Credits_Table = "credits";
    public static final String Credits_ID = "CreditID";
    public static final String Credits_Name = "CreditName";
    public static final String Credits_MaxSumm = "CreditMaxSumm";
    public static final String Credits_Currency = "CurrencyID";
    public static final String Credits_NominalRate = "CreditNominalRate";

    public static final String Currency_Table = "currency";
    public static final String Currency_ID = "CurrencyID";
    public static final String Currency_Name = "CurrencyName";
    public static final String Currency_RoundingStep = "RoundingStep";
    public static final String Currency_Rate = "CurrencyRate";

    public static final String ClientsCredits_Table = "clientscredits";
    public static final String ClientsCredits_ClientID = "ClientID";
    public static final String ClientsCredits_CreditID = "CreditID";
    public static final String ClientsCredits_ClientSurname = "ClientSurname";
    public static final String ClientsCredits_CreditName = "CreditName";
    public static final String ClientsCredits_CreditNominalRate = "CreditNominalRate";
    public static final String ClientsCredits_CreditGiven = "CreditGiveDate";
    public static final String ClientsCredits_CreditReturned = "CreditReturnDate";
    public static final String ClientsCredits_CreditFrequency = "CreditFrequency";
    public static final String ClientsCredits_CreditSumm = "CreditSumm";
}
