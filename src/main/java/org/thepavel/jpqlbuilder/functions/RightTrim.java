/*
 * Copyright (c) 2020 Pavel Grigorev.
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

package org.thepavel.jpqlbuilder.functions;

public class RightTrim extends LeftTrim {
  public RightTrim(String parameter) {
    super(parameter);
  }

  public RightTrim(String parameter, char trimChar) {
    super(parameter, trimChar);
  }

  public RightTrim(JpqlFunction<String> nested) {
    super(nested);
  }

  public RightTrim(JpqlFunction<String> nested, char trimChar) {
    super(nested, trimChar);
  }

  @Override
  String getTrimTypeKeyword() {
    return "trailing ";
  }
}
