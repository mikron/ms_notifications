package com.sflpro.notifier.services.notification.push;

import com.sflpro.notifier.services.notification.model.NotificationState;
import com.sflpro.notifier.services.notification.model.push.PushNotification;
import com.sflpro.notifier.services.notification.model.push.PushNotificationRecipient;
import com.sflpro.notifier.services.test.AbstractServiceIntegrationTest;
import com.sflpro.notifier.services.user.model.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/17/15
 * Time: 3:00 PM
 */
@Ignore
public class PushNotificationProcessorIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private PushNotificationProcessor pushNotificationProcessingService;

    @Autowired
    private PushNotificationService pushNotificationService;

    /* Constructors */
    public PushNotificationProcessorIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testProcessPushNotification() {
        // Prepare data
        final User user = getServicesTestHelper().createUser();
        final String iOSDeviceToken = getServicesTestHelper().generateIOSToken();
        // Create recipient
        final PushNotificationRecipient recipient = getServicesTestHelper().createPushNotificationRecipientForIOSDeviceAndRegisterWithAmazonSns(user, iOSDeviceToken);
        flushAndClear();
        // Create push notification
        PushNotification pushNotification = getServicesTestHelper().createPushNotification(recipient, getServicesTestHelper().createPushNotificationDto(), getServicesTestHelper().createPushNotificationPropertyDTOs(10));
        Assert.assertEquals(NotificationState.CREATED, pushNotification.getState());
        // Process push notification
        pushNotificationProcessingService.processNotification(pushNotification.getId());
        flushAndClear();
        // Reload push notification
        pushNotification = pushNotificationService.getNotificationById(pushNotification.getId());
        Assert.assertEquals(NotificationState.SENT, pushNotification.getState());
        assertNotNull(pushNotification.getProviderExternalUuId());
    }
}
