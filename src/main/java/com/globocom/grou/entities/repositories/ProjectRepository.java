/*
 * Copyright (c) 2017-2017 Globo.com
 * All rights reserved.
 *
 * This source is subject to the Apache License, Version 2.0.
 * Please see the LICENSE file for more information.
 *
 * Authors: See AUTHORS file
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.globocom.grou.entities.repositories;

import com.globocom.grou.entities.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.stream.Stream;

public interface ProjectRepository extends PagingAndSortingRepository<Project, String> {
    Project findByName(String name);

    @Override
    @RestResource(exported = false)
    Project save(Project project);

    @Override
    @RestResource(exported = false)
    void delete(Project project);
}