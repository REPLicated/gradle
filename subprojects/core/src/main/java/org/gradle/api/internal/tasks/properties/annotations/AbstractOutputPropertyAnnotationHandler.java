/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.api.internal.tasks.properties.annotations;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.internal.tasks.properties.BeanPropertyContext;
import org.gradle.api.internal.tasks.properties.ContentTracking;
import org.gradle.api.internal.tasks.properties.OutputFilePropertyType;
import org.gradle.api.internal.tasks.properties.PropertyValue;
import org.gradle.api.internal.tasks.properties.PropertyVisitor;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.Untracked;
import org.gradle.internal.reflect.AnnotationCategory;
import org.gradle.internal.reflect.PropertyMetadata;

import static org.gradle.api.internal.tasks.properties.ModifierAnnotationCategory.OPTIONAL;
import static org.gradle.api.internal.tasks.properties.ModifierAnnotationCategory.UNTRACKED;

public abstract class AbstractOutputPropertyAnnotationHandler implements PropertyAnnotationHandler {
    @Override
    public ImmutableSet<? extends AnnotationCategory> getAllowedModifiers() {
        return ImmutableSet.of(OPTIONAL, UNTRACKED);
    }

    protected abstract OutputFilePropertyType getFilePropertyType();

    @Override
    public boolean isPropertyRelevant() {
        return true;
    }

    @Override
    public boolean shouldVisit(PropertyVisitor visitor) {
        return true;
    }

    @Override
    public void visitPropertyValue(String propertyName, PropertyValue value, PropertyMetadata propertyMetadata, PropertyVisitor visitor, BeanPropertyContext context) {
        ContentTracking contentTracking = propertyMetadata.isAnnotationPresent(Untracked.class) ? ContentTracking.UNTRACKED : ContentTracking.TRACKED;
        visitor.visitOutputFileProperty(propertyName, propertyMetadata.isAnnotationPresent(Optional.class), contentTracking, value, getFilePropertyType());
    }
}
