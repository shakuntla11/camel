/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.urlrewrite;
import org.junit.Before;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.BeforeClass;

/**
 *
 */
public abstract class BaseUrlRewriteTest extends CamelTestSupport {

    private static volatile int port;

    private static volatile int port2;

    private final AtomicInteger counter = new AtomicInteger(1);

    @BeforeClass
    public static void initPort() throws Exception {
        // start from somewhere in the 23xxx range
        port = AvailablePortFinder.getNextAvailable(23000);
        // find another ports for proxy route test
        port2 = AvailablePortFinder.getNextAvailable(24000);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addComponent("properties", new PropertiesComponent("ref:prop"));
        return context;
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = super.createRegistry();

        Properties prop = new Properties();
        prop.setProperty("port", "" + getPort());
        prop.setProperty("port2", "" + getPort2());
        jndi.bind("prop", prop);
        return jndi;
    }

    protected int getNextPort() {
        return AvailablePortFinder.getNextAvailable(port + counter.getAndIncrement());
    }

    protected int getNextPort(int startWithPort) {
        return AvailablePortFinder.getNextAvailable(startWithPort);
    }

    protected static int getPort() {
        return port;
    }

    protected static int getPort2() {
        return port2;
    }


}