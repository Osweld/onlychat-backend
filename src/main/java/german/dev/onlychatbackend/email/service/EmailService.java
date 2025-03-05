package german.dev.onlychatbackend.email.service;

import german.dev.onlychatbackend.user.entity.User;

public interface EmailService {

    void sendActivateAccountEmail(User user, String token);
    void sendResetPasswordEmail(User user, String token);

}
