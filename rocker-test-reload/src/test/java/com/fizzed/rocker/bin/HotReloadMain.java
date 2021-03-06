/*
 * Copyright 2015 Fizzed Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fizzed.rocker.bin;

import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.runtime.RockerRuntime;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class HotReloadMain {

    public static void main(final String[] args) {
        // activate reloading @ runtime
        RockerRuntime.getInstance().setReloading(true);
        
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        /**
                        // static, compile-time checked template interface
                        String out = views.index.template("Home", "Joe")
                            .render()
                            .toString();
                        */
                        
                        /**
                        // dynamic, runtime-time checked template interface, builder-style
                        String out = Rocker.template("views/index.rocker.html")
                            .bind("title", "Home")
                            .bind("name", "Joe")
                            .render()
                            .toString();
                        */
                        
                        // dynamic, runtime-time checked template interface, with arguments
                        String out = Rocker.template("views/index.rocker.html", "Home", "Joe")
                            .render()
                            .toString();
                        
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html; charset=utf-8");
                        exchange.getResponseSender().send(out);
                    }
                }).build();
        server.start();
    }

}