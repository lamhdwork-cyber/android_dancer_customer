package android.support.persistent.cache.datastore

import android.content.Context
import android.support.persistent.Parser
import android.support.persistent.cache.GsonParser
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface PreferenceProperty<T> {
    suspend fun get(): T
    suspend fun set(value: T)
    fun asFlow(): Flow<T>
}

abstract class DataStoreCaching(
    val context: Context,
    val parser: Parser,
    factory: DataStoreFactory = DefaultDataStoreFactory()
) {
    protected val mShared: DataStore<Preferences> = factory.create(context)
    val shared: DataStore<Preferences> get() = mShared

    fun boolean(key: String, def: Boolean = false) = object : PreferenceProperty<Boolean> {
        override suspend fun get(): Boolean = booleanFlow(key, def).first()
        override suspend fun set(value: Boolean) = putBoolean(key, value)
        override fun asFlow(): Flow<Boolean> = booleanFlow(key, def)
    }

    fun string(key: String, def: String = "") = object : PreferenceProperty<String> {
        override suspend fun get(): String = stringFlow(key, def).first()
        override suspend fun set(value: String) = putString(key, value)
        override fun asFlow(): Flow<String> = stringFlow(key, def)
    }

    fun int(key: String, def: Int = 0) = object : PreferenceProperty<Int> {
        override suspend fun get(): Int = intFlow(key, def).first()
        override suspend fun set(value: Int) = putInt(key, value)
        override fun asFlow(): Flow<Int> = intFlow(key, def)
    }

    fun long(key: String, def: Long = 0) = object : PreferenceProperty<Long> {
        override suspend fun get(): Long = longFlow(key, def).first()
        override suspend fun set(value: Long) = putLong(key, value)
        override fun asFlow(): Flow<Long> = longFlow(key, def)
    }

    inline fun <reified T> reference(key: String) = object : PreferenceProperty<T?> {
        override suspend fun get(): T? = objectFlow<T>(key).first()
        override suspend fun set(value: T?) = putObject(key, value)
        override fun asFlow(): Flow<T?> = objectFlow(key)
    }

    fun stringFlow(key: String, def: String = ""): Flow<String> =
        mShared.data.map { it[stringPreferencesKey(key)] ?: def }

    fun booleanFlow(key: String, def: Boolean = false): Flow<Boolean> =
        mShared.data.map { it[booleanPreferencesKey(key)] ?: def }

    fun intFlow(key: String, def: Int = 0): Flow<Int> =
        mShared.data.map { it[intPreferencesKey(key)] ?: def }

    fun longFlow(key: String, def: Long = 0): Flow<Long> =
        mShared.data.map { it[longPreferencesKey(key)] ?: def }

    inline fun <reified T> objectFlow(key: String): Flow<T?> = shared.data.map { pref ->
        val json = pref[stringPreferencesKey(key)]
        if (json.isNullOrEmpty()) null else parser.fromJson(json, T::class.java)
    }

    suspend fun putString(key: String, value: String) {
        mShared.edit { it[stringPreferencesKey(key)] = value }
    }

    suspend fun putBoolean(key: String, value: Boolean) {
        mShared.edit { it[booleanPreferencesKey(key)] = value }
    }

    suspend fun putInt(key: String, value: Int) {
        mShared.edit { it[intPreferencesKey(key)] = value }
    }

    suspend fun putLong(key: String, value: Long) {
        mShared.edit { it[longPreferencesKey(key)] = value }
    }

    suspend fun <T> putObject(key: String, value: T?) {
        mShared.edit { it[stringPreferencesKey(key)] = parser.toJson(value) }
    }

    suspend fun clear() {
        mShared.edit { it.clear() }
    }
}

class GsonDataStoreCaching(context: Context) :
    DataStoreCaching(context, GsonParser(), DefaultDataStoreFactory())
