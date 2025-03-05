package german.dev.onlychatbackend.email.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import german.dev.onlychatbackend.email.exception.EmailSendException;
import german.dev.onlychatbackend.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    // @Value("${app.frontend-url}")
    private String frontendUrl = "http://localhost:4200";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendActivateAccountEmail(User user, String token) {

        try {
            Context context = new Context();
            context.setVariable("name", user.getUsername());
            context.setVariable("activationUrl", frontendUrl + "/activate-account?token=" + token);

            String htmlContent = templateEngine.process("email/activate-account", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Activate your OnlyChat account");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new EmailSendException("Failed to send activation email", e);
        }
    }

    @Override
    public void sendResetPasswordEmail(User user, String token) {
        
        try {
            Context context = new Context();
            context.setVariable("name", user.getUsername());
            context.setVariable("resetUrl", frontendUrl + "/reset-password?token=" + token);

            String htmlContent = templateEngine.process("email/reset-password", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Reset your OnlyChat password");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new EmailSendException("Failed to send reset password email", e);
        }
    }

}
