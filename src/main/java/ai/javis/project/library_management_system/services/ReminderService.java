package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.Utils.Utils;
import ai.javis.project.library_management_system.models.User;
import ai.javis.project.library_management_system.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReminderService {
    private static final Logger logger = LoggerFactory.getLogger(ReminderService.class);
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String adminMail;

    public void sendReminderEmail(String to, String subject, String mailBody) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(adminMail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(mailBody);
            mailSender.send(message);
        } catch (Exception ex){
            logger.error("sendReminderEmail: "  + ex.getMessage());
        }
    }

    @Scheduled(cron = "0 29 22 * * ?") // sec min hr dayofMonth month dayOfWeek
    public void sendReminders() {
        try{
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            List<User> usersWithDueBooks = userRepository.findUsersWithBooksDueOn(tomorrow);

            for (User user : usersWithDueBooks) {
                String subject = "Reminder: Book Due Tomorrow";
                String text = "Dear " + user.getName() + ",\n\nYour borrowed book is due tomorrow. Please return it on time.\n\nThank you!";
                sendReminderEmail(user.getEmail(), subject, text);
            }
        } catch (Exception ex){
            logger.error("sendReminders: " + ex.getMessage());
        }
    }
}
