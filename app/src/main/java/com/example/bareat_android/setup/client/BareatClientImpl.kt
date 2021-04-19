package com.example.bareat_android.setup.client

import com.example.domain.client.BareatClient
import com.example.domain.client.BareatService
import com.example.domain.error.NetworkController

class BareatClientImpl(
private val bareatService: BareatService,
private val networkController: NetworkController
) : BareatClient {



}