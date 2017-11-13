package com.example.temp.imagesearch.utils;

import android.support.annotation.Nullable;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by temp on 11/11/17.
 */

public class RxUtils {

    private RxUtils() {
        throw new AssertionError("No instances for utility class");
    }

    /**
     * Unsubscribes itself and all inner subscriptions.
     * <p>After call of this method, new {@code Subscription}s added to {@link CompositeSubscription}
     * will be unsubscribed immediately.
     */
    public static void unSubscribe(@Nullable CompositeSubscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * Stops the receipt of notifications on the {@link Subscriber} that was registered when this Subscription
     * was received.
     * <p>
     * This allows unregistering an {@link Subscriber} before it has finished receiving all events (i.e. before
     * onCompleted is called).
     */
    public static void unSubscribe(@Nullable Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * Unsubscribes any subscriptions that are currently part of this {@code CompositeSubscription}
     * and remove them from the {@code CompositeSubscription} so that the
     * {@code CompositeSubscription} is empty and
     * able to manage new subscriptions.
     */

    public static void clear(@Nullable CompositeSubscription subscription) {
        if (subscription != null) {
            subscription.clear();
        }
    }

    /**
     * @param compositeSubscription -> The list of all subscription added in the composite subscription
     * @param subscription          -> A particular subscription which you want to remove
     *                              <p>
     *                              It removes and unSubscribes a particular subscription from the composite subscription.
     */
    public static void removeFromCompositeSubscription(CompositeSubscription compositeSubscription, Subscription subscription) {
        if (compositeSubscription != null && subscription != null) {
            compositeSubscription.remove(subscription);
        }
    }

    /**
     * Returns observable which subscribes on io thread and observes on
     * main thread.
     * <p>
     * Use it in .compose() function, it just returns a transformed observable
     * which subscribes and observes on given thread.
     */
    public static <T> Observable.Transformer<T, T> applyIOScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Returns observable which subscribes on computation thread and observes on
     * main thread.
     * <p>
     * Use it in .compose() function, it just returns a transformed observable
     * which subscribes and observes on given thread.
     */
    public static <T> Observable.Transformer<T, T> applyComputationScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param compositeSubscription
     * @param subscription          - subscription to be added
     *                              adds the subscription to the composite subscription
     */
    public static void addToCompositeSubscription(CompositeSubscription compositeSubscription, Subscription subscription) {
        if (compositeSubscription != null && subscription != null) {
            compositeSubscription.add(subscription);
        }
    }
}
