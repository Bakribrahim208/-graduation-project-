package client;

public interface ClientStatusListener
{
    void loginStatus(String status); 
    void logout(String status);

}
