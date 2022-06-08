package io.github.tuguzt.pcbuilder.data.datasource.remote.impl

import io.github.tuguzt.pcbuilder.data.Error
import io.github.tuguzt.pcbuilder.data.Result
import io.github.tuguzt.pcbuilder.data.datasource.BuildDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendBuildAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.toResult
import io.github.tuguzt.pcbuilder.domain.model.NanoId
import io.github.tuguzt.pcbuilder.domain.model.build.BuildData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteBuildDataSource(private val backendBuildAPI: BackendBuildAPI) : BuildDataSource {
    override suspend fun getAll(): Result<List<BuildData>, Error> =
        withContext(Dispatchers.IO) { backendBuildAPI.getAll() }.toResult()

    override suspend fun findById(id: NanoId): Result<BuildData?, Error> =
        withContext(Dispatchers.IO) { backendBuildAPI.findById(id) }.toResult()

    override suspend fun save(item: BuildData): Result<Unit, Error> =
        withContext(Dispatchers.IO) { backendBuildAPI.save(item) }.toResult()

    override suspend fun delete(item: BuildData): Result<Unit, Error> =
        withContext(Dispatchers.IO) { backendBuildAPI.delete(item) }.toResult()

    override suspend fun clear(): Result<Unit, Error> = TODO("Not yet implemented")
}
