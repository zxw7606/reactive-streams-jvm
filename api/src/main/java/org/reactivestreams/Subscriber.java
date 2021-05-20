/***************************************************
 * Licensed under MIT No Attribution (SPDX: MIT-0) *
 ***************************************************/

package org.reactivestreams;

/**
 * Will receive call to {@link #onSubscribe(Subscription)} once after passing an instance of {@link Subscriber} to {@link Publisher#subscribe(Subscriber)}.
 * <p>
 * No further notifications will be received until {@link Subscription#request(long)} is called.
 * <p>
 * After signaling demand:
 * <ul>
 * <li>One or more invocations of {@link #onNext(Object)} up to the maximum number defined by {@link Subscription#request(long)}</li>
 * <li>Single invocation of {@link #onError(Throwable)} or {@link Subscriber#onComplete()} which signals a terminal state after which no further events will be sent.
 * </ul>
 * <p>
 * Demand can be signaled via {@link Subscription#request(long)} whenever the {@link Subscriber} instance is capable of handling more.
 *
 * @param <T> the type of element signaled
 */
public interface Subscriber<T> {

    /**
     * 约定：当调用 {@link Publisher#subscribe(Subscriber)}后要调用此方法.
     *
     * 大致流程就是 Subscriber#onSubscribe 完成业务逻辑之后可以再 Subscription#request(long) , Subscription#request(long) 会调用 Subscriber#onNext，
     * 在 onNext 里面可以再生成 一些 Subscriber 订阅 onNext(T t) 的元素以此来完成链式调用.
     * 通过Subscription#request(long) 可以控制消费的数量 或者通过Subscription#cancel()来停止
     * <p>
     * 为什么方法都是void因为该框架是为了解决异步时候调用的问题。因此类似于js, 但是又想避免类似回调地域的问题. 总体来说该API是为了响应异步而生的
     * <p>
     * Invoked after calling {@link Publisher#subscribe(Subscriber)}.
     * <p>
     * No data will start flowing until {@link Subscription#request(long)} is invoked.
     * <p>
     * It is the responsibility of this {@link Subscriber} instance to call {@link Subscription#request(long)} whenever more data is wanted.
     * <p>
     * The {@link Publisher} will send notifications only in response to {@link Subscription#request(long)}.
     * 
     * @param s the {@link Subscription} that allows requesting data via {@link Subscription#request(long)}
     */
    public void onSubscribe(Subscription s);

    /**
     * 约定： 当
     * Data notification sent by the {@link Publisher} in response to requests to {@link Subscription#request(long)}.
     * 
     * @param t the element signaled
     */
    public void onNext(T t);

    /**
     * Failed terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     *
     * @param t the throwable signaled
     */
    public void onError(Throwable t);

    /**
     * Successful terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     */
    public void onComplete();
}
