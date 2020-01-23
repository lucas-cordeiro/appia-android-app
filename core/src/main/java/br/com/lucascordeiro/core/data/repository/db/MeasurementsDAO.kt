package br.com.lucascordeiro.core.data.repository.db

import br.com.lucascordeiro.core.data.repository.model.MeasurementModel
import io.realm.Realm
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MeasurementsDAO {
    companion object{
        suspend fun doGetMeasurementsAsync() = withContext(Default){
            async {
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                val measurements = realm.where(MeasurementModel::class.java).findAll()
                realm.commitTransaction()
                measurements
            }
        }

        suspend fun doInsertMeasurementsAsync(measurementModel: MeasurementModel) = withContext(Default){
            async {
                val realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                realm.insertOrUpdate(measurementModel)
                realm.commitTransaction()
            }
        }
    }
}