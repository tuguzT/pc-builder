package io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.mock

import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.OctopartAPI
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.json
import io.github.tuguzt.pcbuilder.presentation.repository.net.octopart.model.SearchResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MockOctopartAPI : OctopartAPI {
    override fun searchQuery(
        query: String,
        apiKey: String,
        start: Int,
        limit: Int,
    ) = object : Call<SearchResponse> {
        override fun clone() = this

        override fun execute(): Response<SearchResponse> {
            val searchResponse = json.decodeFromString<SearchResponse>(response)
            return Response.success(searchResponse)
        }

        override fun enqueue(callback: Callback<SearchResponse>) {
            val call = this
            CoroutineScope(Dispatchers.IO).launch {
                val searchResponse = execute()
                callback.onResponse(call, searchResponse)
            }
        }

        override fun isExecuted() = false

        override fun cancel() = Unit

        override fun isCanceled() = false

        override fun request() = Request.Builder().build()

        override fun timeout() = Timeout.NONE
    }
}

private const val response = "{\n" +
        "  \"hits\": 103212,\n" +
        "  \"results\": [\n" +
        "    {\n" +
        "      \"item\": {\n" +
        "        \"uid\": \"8ca745d754035360\",\n" +
        "        \"mpn\": \"6ES7 214 1BD23 0XB0\",\n" +
        "        \"brand\": {\n" +
        "          \"uid\": \"924\",\n" +
        "          \"name\": \"Siemens\",\n" +
        "          \"homepage_url\": \"\"\n" +
        "        },\n" +
        "        \"manufacturer\": {\n" +
        "          \"uid\": \"924\",\n" +
        "          \"name\": \"Siemens\",\n" +
        "          \"homepage_url\": \"\"\n" +
        "        },\n" +
        "        \"short_description\": \"S7-224 CPU; 6ES7 214-1BD23 OXBO 24i/o\",\n" +
        "        \"octopart_url\": \"https://octopart.com/6es7+214+1bd23+0xb0-siemens-8060354\",\n" +
        "        \"offers\": [\n" +
        "          {\n" +
        "            \"id\": \"726429926\",\n" +
        "            \"sku\": \"6ES72141BD230XB0\",\n" +
        "            \"eligible_region\": \"\",\n" +
        "            \"factory_lead_days\": null,\n" +
        "            \"factory_order_multiple\": null,\n" +
        "            \"in_stock_quantity\": 6,\n" +
        "            \"on_order_quantity\": null,\n" +
        "            \"order_multiple\": 1,\n" +
        "            \"multipack_quantity\": null,\n" +
        "            \"packaging\": null,\n" +
        "            \"moq\": 1,\n" +
        "            \"product_url\": \"https://octopart.com/click/track?country=US&ct=offers&ppid=8060354&sid=29958&sig=0325675&vpid=726429926&ai4=140534\",\n" +
        "            \"last_updated\": \"2021-10-15T05:00:43Z\",\n" +
        "            \"prices\": {\n" +
        "              \"USD\": [\n" +
        "                [\n" +
        "                  1,\n" +
        "                  \"1805.00000\"\n" +
        "                ]\n" +
        "              ]\n" +
        "            },\n" +
        "            \"seller\": {\n" +
        "              \"uid\": \"13678\",\n" +
        "              \"id\": \"13678\",\n" +
        "              \"name\": \"Standard Electric Supply Co.\",\n" +
        "              \"display_flag\": \"US\",\n" +
        "              \"homepage_url\": \"https://www.standardelectricsupply.com/\"\n" +
        "            },\n" +
        "            \"is_authorized\": true\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": \"237252910\",\n" +
        "            \"sku\": \"6ES7214-1BD23-0XB0\",\n" +
        "            \"eligible_region\": \"\",\n" +
        "            \"factory_lead_days\": null,\n" +
        "            \"factory_order_multiple\": null,\n" +
        "            \"in_stock_quantity\": 26,\n" +
        "            \"on_order_quantity\": null,\n" +
        "            \"order_multiple\": null,\n" +
        "            \"multipack_quantity\": null,\n" +
        "            \"packaging\": null,\n" +
        "            \"moq\": null,\n" +
        "            \"product_url\": \"https://octopart.com/click/track?country=US&ct=offers&ppid=8060354&sid=2460&sig=0b54ffe&vpid=237252910&ai4=140534\",\n" +
        "            \"last_updated\": \"2021-10-14T19:54:35Z\",\n" +
        "            \"prices\": {},\n" +
        "            \"seller\": {\n" +
        "              \"uid\": \"2460\",\n" +
        "              \"id\": \"2460\",\n" +
        "              \"name\": \"Electronic Supply\",\n" +
        "              \"display_flag\": \"US\",\n" +
        "              \"homepage_url\": \"http://www.electronicsupply.com/\"\n" +
        "            },\n" +
        "            \"is_authorized\": true\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": \"114467764\",\n" +
        "            \"sku\": \"15P8347\",\n" +
        "            \"eligible_region\": \"US\",\n" +
        "            \"factory_lead_days\": null,\n" +
        "            \"factory_order_multiple\": null,\n" +
        "            \"in_stock_quantity\": 0,\n" +
        "            \"on_order_quantity\": null,\n" +
        "            \"order_multiple\": 1,\n" +
        "            \"multipack_quantity\": \"1\",\n" +
        "            \"packaging\": null,\n" +
        "            \"moq\": 1,\n" +
        "            \"product_url\": \"https://octopart.com/click/track?c=1&country=US&ct=offers&ppid=8060354&sid=2402&sig=07bb446&vpid=114467764&ai4=140534\",\n" +
        "            \"last_updated\": \"2021-10-15T19:10:27Z\",\n" +
        "            \"prices\": {},\n" +
        "            \"seller\": {\n" +
        "              \"uid\": \"2402\",\n" +
        "              \"id\": \"2402\",\n" +
        "              \"name\": \"Newark\",\n" +
        "              \"display_flag\": \"US\",\n" +
        "              \"homepage_url\": \"http://www.newark.com\"\n" +
        "            },\n" +
        "            \"is_authorized\": true\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": \"132618230\",\n" +
        "            \"sku\": \"8381720\",\n" +
        "            \"eligible_region\": \"SG\",\n" +
        "            \"factory_lead_days\": null,\n" +
        "            \"factory_order_multiple\": null,\n" +
        "            \"in_stock_quantity\": 0,\n" +
        "            \"on_order_quantity\": null,\n" +
        "            \"order_multiple\": 1,\n" +
        "            \"multipack_quantity\": \"1\",\n" +
        "            \"packaging\": null,\n" +
        "            \"moq\": 1,\n" +
        "            \"product_url\": \"https://octopart.com/click/track?c=1&country=US&ct=offers&ppid=8060354&sid=11744&sig=0843145&vpid=132618230&ai4=140534\",\n" +
        "            \"last_updated\": \"2021-10-14T19:05:57Z\",\n" +
        "            \"prices\": {\n" +
        "              \"SGD\": [\n" +
        "                [\n" +
        "                  1,\n" +
        "                  \"747.31000\"\n" +
        "                ]\n" +
        "              ]\n" +
        "            },\n" +
        "            \"seller\": {\n" +
        "              \"uid\": \"3702\",\n" +
        "              \"id\": \"3702\",\n" +
        "              \"name\": \"element14 APAC\",\n" +
        "              \"display_flag\": \"SG\",\n" +
        "              \"homepage_url\": \"http://www.element14.com/\"\n" +
        "            },\n" +
        "            \"is_authorized\": true\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": \"511047842\",\n" +
        "            \"sku\": \"12571071\",\n" +
        "            \"eligible_region\": \"\",\n" +
        "            \"factory_lead_days\": null,\n" +
        "            \"factory_order_multiple\": null,\n" +
        "            \"in_stock_quantity\": 0,\n" +
        "            \"on_order_quantity\": null,\n" +
        "            \"order_multiple\": null,\n" +
        "            \"multipack_quantity\": null,\n" +
        "            \"packaging\": null,\n" +
        "            \"moq\": 1,\n" +
        "            \"product_url\": \"https://octopart.com/click/track?c=1&country=US&ct=offers&ppid=8060354&sid=14389&sig=09567b8&vpid=511047842&ai4=140534\",\n" +
        "            \"last_updated\": \"2021-10-15T10:12:55Z\",\n" +
        "            \"prices\": {\n" +
        "              \"EUR\": [\n" +
        "                [\n" +
        "                  1,\n" +
        "                  \"857.00000\"\n" +
        "                ],\n" +
        "                [\n" +
        "                  5,\n" +
        "                  \"836.00000\"\n" +
        "                ]\n" +
        "              ]\n" +
        "            },\n" +
        "            \"seller\": {\n" +
        "              \"uid\": \"4617\",\n" +
        "              \"id\": \"4617\",\n" +
        "              \"name\": \"Distrelec\",\n" +
        "              \"display_flag\": \"EU\",\n" +
        "              \"homepage_url\": \"http://www.distrelec.com\"\n" +
        "            },\n" +
        "            \"is_authorized\": true\n" +
        "          }\n" +
        "        ],\n" +
        "        \"imagesets\": [\n" +
        "          {\n" +
        "            \"attribution\": {\n" +
        "              \"sources\": [\n" +
        "                {\n" +
        "                  \"name\": \"Allied Electronics & Automation\"\n" +
        "                }\n" +
        "              ]\n" +
        "            },\n" +
        "            \"large_image\": {\n" +
        "              \"url\": \"https://sigma.octopart.com/124932767/image/Siemens-6ES7-214-1BD23-0XB0.jpg\",\n" +
        "              \"mimetype\": \"image/jpg\"\n" +
        "            },\n" +
        "            \"credit_string\": \"Allied Electronics & Automation\",\n" +
        "            \"credit_url\": \"http://alliedelec.com\"\n" +
        "          },\n" +
        "          {\n" +
        "            \"attribution\": {\n" +
        "              \"sources\": [\n" +
        "                {\n" +
        "                  \"name\": \"element14 APAC\"\n" +
        "                }\n" +
        "              ]\n" +
        "            },\n" +
        "            \"large_image\": {\n" +
        "              \"url\": \"https://sigma.octopart.com/21438272/image/Siemens-6ES7214-1BD23-0XB0.jpg\",\n" +
        "              \"mimetype\": \"image/jpg\"\n" +
        "            },\n" +
        "            \"credit_string\": \"element14 APAC\",\n" +
        "            \"credit_url\": \"https://octopart.com/click/track?c=1&country=&ct=offers&ppid=8060354&sid=11744&sig=0843145&vpid=132618230&ai4=140534\"\n" +
        "          },\n" +
        "          {\n" +
        "            \"attribution\": {\n" +
        "              \"sources\": [\n" +
        "                {\n" +
        "                  \"name\": \"Distrelec\"\n" +
        "                }\n" +
        "              ]\n" +
        "            },\n" +
        "            \"large_image\": {\n" +
        "              \"url\": \"https://sigma.octopart.com/34342822/image/Siemens-6ES7214-1BD23-0XB0.jpg\",\n" +
        "              \"mimetype\": \"image/jpg\"\n" +
        "            },\n" +
        "            \"credit_string\": \"Distrelec\",\n" +
        "            \"credit_url\": \"https://octopart.com/click/track?c=1&country=&ct=offers&ppid=8060354&sid=14389&sig=09567b8&vpid=511047842&ai4=140534\"\n" +
        "          },\n" +
        "          {\n" +
        "            \"attribution\": {\n" +
        "              \"sources\": [\n" +
        "                {\n" +
        "                  \"name\": \"TME\"\n" +
        "                }\n" +
        "              ]\n" +
        "            },\n" +
        "            \"large_image\": {\n" +
        "              \"url\": \"https://sigma.octopart.com/34375619/image/Siemens-6ES7214-1BD23-0XB0.jpg\",\n" +
        "              \"mimetype\": \"image/jpg\"\n" +
        "            },\n" +
        "            \"credit_string\": \"TME\",\n" +
        "            \"credit_url\": \"http://www.tme.eu/en/\"\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    }\n" +
        "  ],\n" +
        "  \"request\": {\n" +
        "    \"q\": \"cpu\",\n" +
        "    \"start\": 0,\n" +
        "    \"limit\": 1,\n" +
        "    \"sort\": \"\",\n" +
        "    \"sort_dir\": \"desc\",\n" +
        "    \"filters\": {},\n" +
        "    \"country\": \"US\",\n" +
        "    \"facet\": null,\n" +
        "    \"stats\": null\n" +
        "  },\n" +
        "  \"msec\": 0,\n" +
        "  \"facet_results\": {},\n" +
        "  \"stats_results\": {},\n" +
        "  \"spec_metadata\": {}\n" +
        "}\n"
