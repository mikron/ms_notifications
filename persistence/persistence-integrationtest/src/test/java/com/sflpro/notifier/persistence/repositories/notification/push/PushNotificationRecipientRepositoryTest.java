package com.sflpro.notifier.persistence.repositories.notification.push;

import com.sflpro.notifier.persistence.repositories.AbstractRepositoryTest;
import com.sflpro.notifier.services.device.model.mobile.DeviceOperatingSystemType;
import com.sflpro.notifier.services.notification.dto.push.PushNotificationRecipientSearchParameters;
import com.sflpro.notifier.services.notification.model.push.PushNotificationProviderType;
import com.sflpro.notifier.services.notification.model.push.PushNotificationRecipient;
import com.sflpro.notifier.services.notification.model.push.PushNotificationRecipientStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/18/15
 * Time: 3:44 PM
 */
public class PushNotificationRecipientRepositoryTest extends AbstractRepositoryTest {

    /* Dependencies */
    @Autowired
    private PushNotificationRecipientRepository pushNotificationRecipientRepository;

    /* Constructors */
    public PushNotificationRecipientRepositoryTest() {
    }

    /* Test methods */
    @Test
    public void testGetPushNotificationRecipientsCount() {
        // Prepare data
        final PushNotificationRecipientSearchParameters parameters = createPushNotificationRecipientSearchParameters();
        // Get recipients count
        final Long recipientsCount = pushNotificationRecipientRepository.getPushNotificationRecipientsCount(parameters);
        assertNotNull(recipientsCount);
        assertEquals(0, recipientsCount.intValue());
    }

    @Test
    public void testFindPushNotificationRecipients() {
        // Prepare data
        final PushNotificationRecipientSearchParameters parameters = createPushNotificationRecipientSearchParameters();
        final Long startFrom = 0l;
        final Integer maxResults = Integer.MAX_VALUE;
        // Load push notifications recipients
        final List<PushNotificationRecipient> recipients = pushNotificationRecipientRepository.findPushNotificationRecipients(parameters, startFrom, maxResults);
        assertNotNull(recipients);
        assertEquals(0, recipients.size());
    }

    /* Utility methods */
    private PushNotificationRecipientSearchParameters createPushNotificationRecipientSearchParameters() {
        final PushNotificationRecipientSearchParameters parameters = new PushNotificationRecipientSearchParameters();
        parameters.setStatus(PushNotificationRecipientStatus.ENABLED);
        parameters.setDeviceOperatingSystemType(DeviceOperatingSystemType.IOS);
        parameters.setDestinationRouteToken("GGFTF%*^D(RD*RDFXR58drS&D*");
        parameters.setProviderType(PushNotificationProviderType.SNS);
        parameters.setSubscriptionId(1L);
        parameters.setApplicationType("mainapp");
        return parameters;
    }
}
