package com.sflpro.notifier.services.notification.email;

import com.sflpro.notifier.services.notification.AbstractNotificationService;
import com.sflpro.notifier.services.notification.AbstractNotificationServiceIntegrationTest;
import com.sflpro.notifier.services.notification.dto.email.EmailNotificationDto;
import com.sflpro.notifier.services.notification.model.email.EmailNotification;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/21/15
 * Time: 8:40 PM
 */
public class EmailNotificationServiceIntegrationTest extends AbstractNotificationServiceIntegrationTest<EmailNotification> {

    /* Dependencies */
    @Autowired
    private EmailNotificationService emailNotificationService;

    /* Constructors */
    public EmailNotificationServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testCreateEmailNotification() {
        // Prepare data
        final EmailNotificationDto notificationDto = getServicesTestHelper().createEmailNotificationDto();
        // Create notification
        EmailNotification emailNotification = emailNotificationService.createEmailNotification(notificationDto);
        getServicesTestHelper().assertEmailNotification(emailNotification, notificationDto);
        // Flush, clear, reload and assert
        flushAndClear();
        emailNotification = emailNotificationService.getNotificationById(emailNotification.getId());
        getServicesTestHelper().assertEmailNotification(emailNotification, notificationDto);
    }

    /* Utility methods */
    @Override
    protected AbstractNotificationService<EmailNotification> getService() {
        return emailNotificationService;
    }

    @Override
    protected EmailNotification getInstance() {
        return getServicesTestHelper().createEmailNotification();
    }

    @Override
    protected Class<EmailNotification> getInstanceClass() {
        return EmailNotification.class;
    }

}
