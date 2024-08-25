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
package fr.ekalia.injector.annotation;

/**
 * The priority of a class.
 * The priority is used to determine the order of the class injection.
 * The order is from the lowest priority to the highest priority.
 * For example, if you have a class with the priority "LOWEST" and another class with the priority "HIGHEST",
 * the class with the priority "LOWEST" will be injected before the class with the priority "HIGHEST", so the
 * one with the priority "HIGHEST" will replace the one with the priority "LOWEST".
 *
 * @author Azn9
 */
public enum InjectPriority {

    /**
     * The lowest priority.
     */
    LOWEST,
    /**
     * A little bit lower priority.
     */
    LOW,
    /**
     * The default priority.
     */
    NORMAL,
    /**
     * A little bit higher priority.
     */
    HIGH,
    /**
     * The highest priority.
     */
    HIGHEST

}
