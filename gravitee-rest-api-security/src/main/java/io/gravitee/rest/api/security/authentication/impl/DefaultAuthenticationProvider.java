/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.rest.api.security.authentication.impl;

import java.util.Map;

import io.gravitee.rest.api.security.authentication.AuthenticationProvider;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
class DefaultAuthenticationProvider implements AuthenticationProvider {

    private final String type;
    private final int order;
    private Map<String, Object> configuration;

    DefaultAuthenticationProvider(String type, int order) {
        this.type = type;
        this.order = order;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public int order() {
        return this.order;
    }

    public Map<String, Object> configuration() {
        return configuration;
    }

    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultAuthenticationProvider that = (DefaultAuthenticationProvider) o;

        if (order != that.order) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + order;
        return result;
    }
}
