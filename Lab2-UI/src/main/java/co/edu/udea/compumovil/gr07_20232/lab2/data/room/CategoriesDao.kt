/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.edu.udea.compumovil.gr07_20232.lab2.data.room

import androidx.room.Dao
import androidx.room.Query
import co.edu.udea.compumovil.gr07_20232.lab2.data.Category
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [Category] related operations.
 */
@Dao
abstract class CategoriesDao : BaseDao<Category> {
    @Query(
        """
        SELECT categories.* FROM categories
        INNER JOIN (
            SELECT category_id, COUNT(podcast_uri) AS podcast_count FROM podcast_category_entries
            GROUP BY category_id
        ) ON category_id = categories.id
        ORDER BY podcast_count DESC
        LIMIT :limit
        """
    )
    abstract fun categoriesSortedByPodcastCount(
        limit: Int
    ): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE name = :name")
    abstract suspend fun getCategoryWithName(name: String): Category?
}
