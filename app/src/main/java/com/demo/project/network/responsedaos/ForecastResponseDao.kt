package com.demo.project.network.responsedaos

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "stopInfo")
data class ForecastResponseDao(
    @field:Element(name = "message") @param:Element(name = "message") val message: String?,
    @field:ElementList(name = "direction",inline = true) @param:ElementList(name = "direction",inline = true) val forecastResponseDirectionDaos: List<ForecastResponseDirectionDao>?

)

@Root(strict = false, name = "direction")
data class ForecastResponseDirectionDao (
    @field:ElementList(name = "tram",inline = true) @param:ElementList(name = "tram",inline = true) val trams: List<ForecastResponseTramDao>?,
    @field:Attribute(name = "name") @param:Attribute(name = "name") val name: String?
)

@Root(strict = false, name = "tram")
data class ForecastResponseTramDao (
    @field:Attribute(name = "destination") @param:Attribute(name = "destination") val destination: String?,
    @field:Attribute(name = "dueMins") @param:Attribute(name = "dueMins") val dueMins: String?
)


