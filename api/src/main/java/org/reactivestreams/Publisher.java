/***************************************************
 * Licensed under MIT No Attribution (SPDX: MIT-0) *
 ***************************************************/

package org.reactivestreams;

/**
 * A {@link Publisher} is a provider of a potentially unbounded number of sequenced elements, publishing them according to
 * the demand received from its {@link Subscriber}(s).
 * <p>
 * A {@link Publisher} can serve multiple {@link Subscriber}s subscribed {@link Publisher#subscribe(Subscriber)} dynamically
 * at various points in time.
 *
 * @param <T> the type of element signaled
 */
public interface Publisher<T> {

    /**
     * Request {@link Publisher} to start streaming data.
     * <p>
     * This is a "factory method" and can be called multiple times, each time starting a new {@link Subscription}.
     * <p>
     * Each {@link Subscription} will work for only a single {@link Subscriber}.
     * <p>
     * A {@link Subscriber} should only subscribe once to a single {@link Publisher}.
     * <p>
     * If the {@link Publisher} rejects the subscription attempt or otherwise fails it will
     * signal the error via {@link Subscriber#onError(Throwable)}.
     *
     * @param s the {@link Subscriber} that will consume signals from this {@link Publisher}
     */
    public void subscribe(Subscriber<? super T> s);

    // 当调用 subscribe后 , 我们要 对 Subscriber 和  Subscription 绑定一个关系
    // 具体逻辑是 new Subscription(Subscriber);
    // 之后在Subscription 调用 Subscriber onSubscribe(Subscription) 方法形成一个绑定
    // 由 Subscriber 来控制是否执行，
    // 由 Subscription 来执行具体的业务逻辑，
}
