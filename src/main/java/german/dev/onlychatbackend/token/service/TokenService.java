package german.dev.onlychatbackend.token.service;

import german.dev.onlychatbackend.user.entity.User;

public interface TokenService {

    String generateActivateAccountToken(User user);
    User activateAccountToken(String validateToken);
    String generateResetPasswordToken(User user);
    User resetPasswordToken(String validateToken);
    String resendActivateAccountToken(User user);

}
