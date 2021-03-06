/*
 *
 *  * Copyright 2020 New Relic Corporation. All rights reserved.
 *  * SPDX-License-Identifier: Apache-2.0
 *
 */

package org.apache.catalina.connector;

import java.util.logging.Level;
import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.tomcat7.AsyncListenerFactory;
import org.apache.catalina.core.AsyncContextImpl_Weaved;

@Weave(originalName = "org.apache.catalina.connector.Request")
public abstract class Request_Weaved implements HttpServletRequest {

    @Override
    public AsyncContext startAsync(ServletRequest request, ServletResponse response) {

        AsyncContext asyncContext = Weaver.callOriginal();

        asyncContext.addListener(AsyncListenerFactory.getAsyncListener());
        NewRelic.getAgent().getLogger().log(Level.FINER, "Added async listener");

        return asyncContext;
    }

    public AsyncContextImpl_Weaved getAsyncContextInternal() {
        return Weaver.callOriginal();
    }

    public abstract org.apache.catalina.connector.Response getResponse();

}
