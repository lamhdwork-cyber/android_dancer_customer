package android.support.persistent.cache.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

interface DataStoreFactory {
    fun create(context: Context): DataStore<Preferences>
}

private const val STORE_NAME = "lawyer:booking:cache"

private val Context.internalDataStore: DataStore<Preferences> by preferencesDataStore(
    name = STORE_NAME,
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, STORE_NAME))
    }
)

class DefaultDataStoreFactory : DataStoreFactory {
    override fun create(context: Context): DataStore<Preferences> {
        return context.internalDataStore
    }
}