package com.example.portal.modules.rocket


import com.example.portal.api.RocketApi
import com.example.portal.modules.base.BaseRepository
import com.example.portal.modules.base.Result
import com.example.portal.modules.base.Status
import org.json.JSONArray

class RocketRepository(private val rocketApi: RocketApi) : BaseRepository() {

    suspend fun getRocketItemResult(): Result<List<RocketItem>> {
        val safeRequest = safeRequest {
            rocketApi.getRockets()
        }
        if (safeRequest.status == Status.SUCCESS) {
            safeRequest.data?.let { data ->
                return Result.success(parseRocketItem(data.string()))
            } ?: return Result.error(safeRequest.message ?: "", data = null)
        } else {
            return Result.error(safeRequest.message ?: "", data = null)
        }
    }

    private fun parseRocketItem(body: String): List<RocketItem> {
        val rocketList = mutableListOf<RocketItem>()
        val jsonArray = JSONArray(body)

        val arrayLength = jsonArray.length()
        for (i in 0 until arrayLength) {
            val resultObject = jsonArray.optJSONObject(i)
            val linksObject = resultObject.optJSONObject("links")
            val name = resultObject?.optString("mission_name") ?: "-"
            val launchYear = resultObject?.optInt("launch_year") ?: 0
            val thumbnail = linksObject?.optString("mission_patch") ?: "-"

            rocketList.add(
                RocketItem(
                    missionName = name,
                    missionPatch = thumbnail,
                    launchYear = launchYear
                )
            )
        }
        return rocketList.toList()
    }
}