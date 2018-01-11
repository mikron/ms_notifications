package com.sflpro.notifier.persistence.repositories.notification.push;

import com.sflpro.notifier.services.notification.model.push.PushNotificationSubscription;
import com.sflpro.notifier.services.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 8/13/15
 * Time: 10:19 AM
 */
@Repository
public interface PushNotificationSubscriptionRepository extends JpaRepository<PushNotificationSubscription, Long> {

    /**
     * Finds push notification subscription by user
     *
     * @param user
     * @return pushNotificationSubscription
     */
    PushNotificationSubscription findByUser(@Nonnull final User user);
}
