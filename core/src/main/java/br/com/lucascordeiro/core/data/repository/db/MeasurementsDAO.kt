package br.com.lucascordeiro.core.data.repository.db

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class MeasurementsDAO {
    companion object{
        suspend fun doGetMeasurementsQueryAsync() = withContext(IO){

        }
    }
}