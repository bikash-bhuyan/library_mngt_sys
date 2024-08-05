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
import java.util.Set;

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

    @Scheduled(cron = "0 30 19 * * ?") // sec min hr dayofMonth month dayOfWeek
    public void sendBookReturnReminders() {
        try{
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            Set<User> usersWithDueBooks = userRepository.findUsersWithBooksDueOn(tomorrow);

            for (User user : usersWithDueBooks) {
                String subject = "Reminder: Book Due Tomorrow";
                String text = "Dear " + user.getName() + ",\n\nYour borrowed book is due tomorrow. Please return it on time.\n\nThank you!";
                sendReminderEmail(user.getEmail(), subject, text);
            }
        } catch (Exception ex){
            logger.error("sendBookReturnReminders: " + ex.getMessage());
        }
    }

    @Scheduled(cron = "30 52 10 * * ?")
    public void sendBookOverDueReminders(){
        try{
            LocalDate today = LocalDate.now();
            Set<User> usersWithOverDueBooks = userRepository.findUsersWithOverDueBooksTill(today);

            for (User user : usersWithOverDueBooks){
                String subject = "Reminder: Book Overdue";
                String text = "Dear " + user.getName() + ",\n\nYour borrowed book is overdue. Please return it on time to avoid extra fines.\n\nThank you!";
                sendReminderEmail(user.getEmail(),subject,text);
            }
        } catch(Exception ex){
            logger.error("sendBookOverDueReminders: " + ex.getMessage());
        }
    }
}
