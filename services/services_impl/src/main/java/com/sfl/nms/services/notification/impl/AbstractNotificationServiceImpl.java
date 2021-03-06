package com.sfl.nms.services.notification.impl;

import com.sfl.nms.persistence.repositories.notification.AbstractNotificationRepository;
import com.sfl.nms.services.notification.AbstractNotificationService;
import com.sfl.nms.services.notification.dto.NotificationDto;
import com.sfl.nms.services.notification.exception.NotificationNotFoundForIdException;
import com.sfl.nms.services.notification.model.Notification;
import com.sfl.nms.services.notification.model.NotificationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/21/15
 * Time: 8:00 PM
 */
public abstract class AbstractNotificationServiceImpl<T extends Notification> implements AbstractNotificationService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNotificationServiceImpl.class);

    /* Constructors */
    public AbstractNotificationServiceImpl() {
        LOGGER.debug("Initializing abstract notification service");
    }

    @Nonnull
    @Override
    public T getNotificationById(@Nonnull final Long notificationId) {
        assertNotificationIdNotNull(notificationId);
        LOGGER.debug("Getting notification for id - {}, class - {}", notificationId, getInstanceClass());
        final T notification = getRepository().findOne(notificationId);
        assertNotificationNotNullForId(notification, notificationId);
        LOGGER.debug("Successfully retrieved notification for id - {}, notification class - {}", notificationId, getInstanceClass());
        return notification;
    }

    @Transactional
    @Nonnull
    @Override
    public T updateNotificationState(@Nonnull final Long notificationId, @Nonnull final NotificationState notificationState) {
        assertNotificationIdNotNull(notificationId);
        Assert.notNull(notificationState, "Notification state should not be null");
        LOGGER.debug("Updating notification state for notification with id - {}, state - {}", notificationId, notificationState);
        T notification = getRepository().findOne(notificationId);
        assertNotificationNotNullForId(notification, notificationId);
        // Update notification state
        notification.setState(notificationState);
        notification.setUpdated(new Date());
        // Persist notification
        notification = getRepository().save(notification);
        LOGGER.debug("Successfully updated state for notification with id - {}, notification state - {}", notificationId, notificationState);
        return notification;
    }

    @Transactional
    @Nonnull
    @Override
    public T updateProviderExternalUuid(@Nonnull final Long notificationId, @Nonnull final String providerExternalUuid) {
        assertNotificationIdNotNull(notificationId);
        Assert.notNull(providerExternalUuid, "Provider external uuid should not be null");
        LOGGER.debug("Updating notification provider external uuid, notification id - {}, uuid - {}", notificationId, providerExternalUuid);
        T notification = getRepository().findOne(notificationId);
        assertNotificationNotNullForId(notification, notificationId);
        // Update notification external uuid
        notification.setProviderExternalUuId(providerExternalUuid);
        notification.setUpdated(new Date());
        // Persist notification
        notification = getRepository().save(notification);
        LOGGER.debug("Successfully updated notification external uuid for notification with id - {}, notification - {}", notification.getId(), notification);
        return notification;
    }

    /* Abstract methods */
    protected abstract AbstractNotificationRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    /* Utility methods */
    protected void assertNotificationNotNullForId(final T notification, final Long id) {
        if (notification == null) {
            LOGGER.error("Was not able to find notification for id - {}, notification class - {}", id, getInstanceClass());
            throw new NotificationNotFoundForIdException(id, getInstanceClass());
        }
    }

    protected void assertNotificationDto(final NotificationDto<T> notificationDto) {
        Assert.notNull(notificationDto, "Notification DTO should not be null");
        Assert.notNull(notificationDto.getContent(), "Content in notification DTO should not be null");
    }

    protected void assertNotificationIdNotNull(final Long notificationId) {
        Assert.notNull(notificationId, "Notification ID should not be null");
    }
}
