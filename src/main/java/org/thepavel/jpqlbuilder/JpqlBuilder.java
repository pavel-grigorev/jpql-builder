/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.factory.CollectionInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultCollectionInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultMapInstanceFactory;
import org.thepavel.jpqlbuilder.factory.InstanceFactory;
import org.thepavel.jpqlbuilder.factory.MapInstanceFactory;

public class JpqlBuilder {
  private JpqlBuilder() {
  }

  public static SelectBuilder selectBuilder() {
    return new SelectBuilder(JpqlBuilderContext.defaultContext());
  }

  public static DeleteBuilder deleteBuilder() {
    return new DeleteBuilder(JpqlBuilderContext.defaultContext());
  }

  public static UpdateBuilder updateBuilder() {
    return new UpdateBuilder(JpqlBuilderContext.defaultContext());
  }

  public static Builder with(InstanceFactory instanceFactory) {
    return new Builder(instanceFactory);
  }

  public static Builder with(CollectionInstanceFactory collectionInstanceFactory) {
    return new Builder(collectionInstanceFactory);
  }

  public static Builder with(MapInstanceFactory mapInstanceFactory) {
    return new Builder(mapInstanceFactory);
  }

  public static class Builder {
    private InstanceFactory instanceFactory;
    private CollectionInstanceFactory collectionInstanceFactory;
    private MapInstanceFactory mapInstanceFactory;

    private Builder(InstanceFactory instanceFactory) {
      this.instanceFactory = instanceFactory;
    }

    private Builder(CollectionInstanceFactory collectionInstanceFactory) {
      this.collectionInstanceFactory = collectionInstanceFactory;
    }

    private Builder(MapInstanceFactory mapInstanceFactory) {
      this.mapInstanceFactory = mapInstanceFactory;
    }

    public Builder with(InstanceFactory instanceFactory) {
      this.instanceFactory = instanceFactory;
      return this;
    }

    public Builder with(CollectionInstanceFactory collectionInstanceFactory) {
      this.collectionInstanceFactory = collectionInstanceFactory;
      return this;
    }

    public Builder with(MapInstanceFactory mapInstanceFactory) {
      this.mapInstanceFactory = mapInstanceFactory;
      return this;
    }

    public SelectBuilder selectBuilder() {
      return new SelectBuilder(createContext());
    }

    public DeleteBuilder deleteBuilder() {
      return new DeleteBuilder(createContext());
    }

    public UpdateBuilder updateBuilder() {
      return new UpdateBuilder(createContext());
    }

    private JpqlBuilderContext createContext() {
      if (instanceFactory == null) {
        instanceFactory = new DefaultInstanceFactory();
      }
      if (collectionInstanceFactory == null) {
        collectionInstanceFactory = new DefaultCollectionInstanceFactory();
      }
      if (mapInstanceFactory == null) {
        mapInstanceFactory = new DefaultMapInstanceFactory();
      }
      return new JpqlBuilderContext(instanceFactory, collectionInstanceFactory, mapInstanceFactory);
    }
  }
}
