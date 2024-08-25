/*
 * Copyright 2024 Ekalia <contact@ekalia.fr>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ekalia.injector.exception;

import fr.ekalia.injector.Injector;

/**
 * Exception thrown when a class cannot be provided by the {@link Injector}
 *
 * @author Azn9
 */
public class UnprovidableClassException extends RuntimeException {

    /**
     * The reason of the exception
     */
    private final String reason;
    /**
     * Whether the exception should be logged
     */
    private final boolean shouldLog;

    /**
     * Create a new unprovidable class exception
     *
     * @param reason The reason of the exception
     */
    public UnprovidableClassException(String reason) {
        this.reason = reason;
        this.shouldLog = true;
    }

    /**
     * Create a new unprovidable class exception
     *
     * @param reason    The reason of the exception
     * @param shouldLog true if the exception should be logged, false otherwise
     */
    public UnprovidableClassException(String reason, boolean shouldLog) {
        this.reason = reason;
        this.shouldLog = shouldLog;
    }

    /**
     * Get the reason of the exception
     *
     * @return The reason of the exception
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Check if the exception should be logged
     *
     * @return true if the exception should be logged, false otherwise
     */
    public boolean isShouldLog() {
        return this.shouldLog;
    }
}
