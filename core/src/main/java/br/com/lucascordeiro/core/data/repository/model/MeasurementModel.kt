package br.com.lucascordeiro.core.data.repository.model

import br.com.lucascordeiro.querie.GetMeasurementsQuery
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MeasurementModel(
    @PrimaryKey var id: Long = 0,
    var measurement: String = "",
    var status: String = "",
    var createdAt: String = "",
    var measuredAt: String = ""
) : RealmObject() {
    fun convertFromDao() : GetMeasurementsQuery.Measurement {
        return GetMeasurementsQuery.Measurement(
            "Measurements",
            id.toInt(),
            measurement,
            createdAt,
            measuredAt,
            status
        )
    }
    companion object {
        fun convertToDao(measurement: GetMeasurementsQuery.Measurement) : MeasurementModel {
            return MeasurementModel(
                id = measurement.id()!!.toLong(),
                measurement = measurement.measurement(),
                status = measurement.status(),
                createdAt = measurement.createdAt().toString(),
                measuredAt = measurement.measuredAt().toString()
            )
        }
    }
}