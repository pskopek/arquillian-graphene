/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.selenium.guard.request;

import org.jboss.test.selenium.framework.AjaxSelenium;
import org.jboss.test.selenium.request.RequestType;

/**
 * The factory for shortening use of {@link RequestTypeGuard}s in code.
 * 
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public final class RequestTypeGuardFactory {

    private RequestTypeGuardFactory() {
    }

    private static AjaxSelenium guard(AjaxSelenium selenium, RequestType requestExpected, boolean interlayed) {
        AjaxSelenium copy;
        try {
            copy = selenium.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
        copy.getInterceptionProxy().unregisterInterceptorType(RequestTypeGuard.class);
        copy.getInterceptionProxy().registerInterceptor(new RequestTypeGuard(requestExpected, interlayed));
        return copy;
    }

    /**
     * Shortcut for registering a XMLHttpRequest on given selenium object.
     * 
     * @param selenium
     *            where should be registered XMLHttpRequest guard
     * @return the selenium guarded to use XMLHttpRequest
     */
    public static AjaxSelenium guardXhr(AjaxSelenium selenium) {
        return guard(selenium, RequestType.XHR, false);
    }

    /**
     * Shortcut for registering a regular HTTP request on given selenium object.
     * 
     * @param selenium
     *            where should be registered regular HTTP request guard
     * @return the selenium guarded to use regular HTTP requests
     */
    public static AjaxSelenium guardHttp(AjaxSelenium selenium) {
        return guard(selenium, RequestType.HTTP, false);
    }

    /**
     * Shortcut for registering a guard for no request on given selenium object.
     * 
     * @param selenium
     *            where should be registered no request guard
     * @return the selenium guarded to use no request during interaction
     */
    public static AjaxSelenium guardNoRequest(AjaxSelenium selenium) {
        return guard(selenium, RequestType.NONE, false);
    }

    /**
     * Shortcut for registering guard waiting for interception of XHR type request
     * 
     * @param selenium
     *            where should be the guard registered
     * @return the selenium waiting for interception of XHR type request
     */
    public static AjaxSelenium waitXhr(AjaxSelenium selenium) {
        return guard(selenium, RequestType.XHR, true);
    }

    /**
     * Shortcut for registering guard waiting for interception of HTTP type request
     * 
     * @param selenium
     *            selenium where should be the guard registered
     * @return the selenium waitinf for interception of HTTP type request
     */
    public static AjaxSelenium waitHttp(AjaxSelenium selenium) {
        return guard(selenium, RequestType.HTTP, true);
    }
}
