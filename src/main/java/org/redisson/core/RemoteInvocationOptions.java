/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.core;

import java.util.concurrent.TimeUnit;

/**
 * RRemoteService invocation options.
 *
 * Used to tune how RRemoteService will behave
 * in regard to the remote invocations acknowledgement
 * and execution timeout.
 * <p/>
 * Examples:
 * <pre>
 *     // 1 second ack timeout and 30 seconds execution timeout
 *     RemoteInvocationOptions options =
 *          RemoteInvocationOptions.defaults();
 *
 *     // no ack but 30 seconds execution timeout
 *     RemoteInvocationOptions options =
 *          RemoteInvocationOptions.defaults()
 *              .noAck();
 *
 *     // 1 second ack timeout then forget the result
 *     RemoteInvocationOptions options =
 *          RemoteInvocationOptions.defaults()
 *              .noResult();
 *
 *     // 1 minute ack timeout then forget about the result
 *     RemoteInvocationOptions options =
 *          RemoteInvocationOptions.defaults()
 *              .expectAckWithin(1, TimeUnit.MINUTES)
 *              .noResult();
 *
 *     // no ack and forget about the result (fire and forget)
 *     RemoteInvocationOptions options =
 *          RemoteInvocationOptions.defaults()
 *              .noAck()
 *              .noResult();
 * </pre>
 *
 * @see RRemoteService#get(Class, RemoteInvocationOptions)
 */
public class RemoteInvocationOptions {

    private Long ackTimeoutInMillis;
    private Long executionTimeoutInMillis;

    private RemoteInvocationOptions(Long ackTimeoutInMillis, Long executionTimeoutInMillis) {
        this.ackTimeoutInMillis = ackTimeoutInMillis;
        this.executionTimeoutInMillis = executionTimeoutInMillis;
    }

    public RemoteInvocationOptions(RemoteInvocationOptions copy) {
        this.ackTimeoutInMillis = copy.ackTimeoutInMillis;
        this.executionTimeoutInMillis = copy.executionTimeoutInMillis;
    }

    /**
     * Creates a new instance of RemoteInvocationOptions with opinionated defaults.
     * <p/>
     * This is equivalent to:
     * <pre>
     *     RemoteInvocationOptions.defaults()
     *      .expectAckWithin(1, TimeUnit.SECONDS)
     *      .expectResultWithin(30, TimeUnit.SECONDS)
     * </pre>
     */
    public static RemoteInvocationOptions defaults() {
        return new RemoteInvocationOptions(TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(30));
    }

    public Long getAckTimeoutInMillis() {
        return ackTimeoutInMillis;
    }

    public Long getExecutionTimeoutInMillis() {
        return executionTimeoutInMillis;
    }

    public boolean isAckExpected() {
        return ackTimeoutInMillis != null;
    }

    public boolean isResultExpected() {
        return executionTimeoutInMillis != null;
    }

    public RemoteInvocationOptions expectAckWithin(long ackTimeoutInMillis) {
        this.ackTimeoutInMillis = ackTimeoutInMillis;
        return this;
    }

    public RemoteInvocationOptions expectAckWithin(long ackTimeout, TimeUnit timeUnit) {
        this.ackTimeoutInMillis = timeUnit.toMillis(ackTimeout);
        return this;
    }

    public RemoteInvocationOptions noAck() {
        ackTimeoutInMillis = null;
        return this;
    }

    public RemoteInvocationOptions expectResultWithin(long executionTimeoutInMillis) {
        this.executionTimeoutInMillis = executionTimeoutInMillis;
        return this;
    }

    public RemoteInvocationOptions expectResultWithin(long executionTimeout, TimeUnit timeUnit) {
        this.executionTimeoutInMillis = timeUnit.toMillis(executionTimeout);
        return this;
    }

    public RemoteInvocationOptions noResult() {
        executionTimeoutInMillis = null;
        return this;
    }
}
